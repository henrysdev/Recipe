(ns recipe.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.util.codec :as codec]
            [clojure.walk :as walk]
            [clj-http.util :refer :all]
            [clj-http.client :as http]
            [recipe.user :as user]))

;; MOVE TO UTIL MODULE
(defn rand-str [len]
  (apply str (take len (repeatedly #(char (+ (rand 26) 65))))))

(def csrf-token (rand-str 16))

(def oauth2-params
  {:client-id (System/getenv "MY_CLIENT_ID")
   :client-secret (System/getenv "MY_CLIENT_SECRET")
   :authorize-uri  "https://accounts.spotify.com/authorize"
   :redirect-uri "http://127.0.0.1:1234/callback" ;(System/getenv "MY_REDIRECT_URI")
   :access-token-uri "https://accounts.spotify.com/api/token"
   :scope "user-top-read user-follow-read user-read-recently-played playlist-read-collaborative playlist-read-private user-library-read user-read-playback-state user-read-currently-playing"})

(defn authorize-uri [client-params csrf-token]
  (str
   (:authorize-uri client-params)
   "?response_type=code"
   "&client_id="
   (url-encode (:client-id client-params))
   "&redirect_uri="
   (url-encode (:redirect-uri client-params))
   "&scope="
   (url-encode (:scope client-params))
   "&state="
   (url-encode csrf-token)))

(defn get-authentication-response [csrf-token response-params]
  (if (= csrf-token (:state response-params))
    (try
      (-> (http/post (:access-token-uri oauth2-params)
                     {:form-params {:code         (:code response-params)
                                    :grant_type   "authorization_code"
                                    :client_id    (:client-id oauth2-params)
                                    :redirect_uri (:redirect-uri oauth2-params)}
                      :basic-auth [(:client-id oauth2-params) (:client-secret oauth2-params)]
                      :as          :json})
          :body)
      (catch Exception _ nil))
    (println "csrf didnt match")))

(defn- refresh-tokens
  "Request a new token pair"
  [refresh-token]
  (let [{{access-token :access_token refresh-token :refresh_token} :body}
        (http/post (:access-token-uri oauth2-params)
                  {:form-params {:grant_type       "refresh_token"
                                 :refresh_token    refresh-token}
                    :basic-auth [(:client-id oauth2-params) (:client-secret oauth2-params)]
                    :as          :json})]
    [access-token refresh-token]))

(defn get-fresh-tokens
  "Returns current token pair if they have not expired, or a refreshed token pair otherwise"
  [access-token refresh-token]
  (refresh-tokens refresh-token))

(defn callback-handler [req]
  (let [response-params (walk/keywordize-keys (codec/form-decode (:query-string req)))
        tokens (get-authentication-response csrf-token response-params)]
    (user/process {:access (:access_token tokens) :refresh (:refresh_token tokens)})))

(defroutes app-routes
  (GET "/" [] (resp/file-response "index.html" {:root "public"}))
  (GET "/login" [] (resp/redirect (authorize-uri oauth2-params csrf-token)))
  (GET "/callback" [] callback-handler)
  (route/not-found "You Must Be New Here"))

(defn -main
  "This is our app's entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "1234"))]
    (server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))