(ns recipe.authorization
  (:gen-class)
  (:require [clj-http.util :as http-util]
            [clj-http.client :as http]
            [recipe.utils :as utils]))

(def csrf-token (utils/rand-str 16))

(def oauth2-params
  {:client-id (System/getenv "MY_CLIENT_ID")
   :client-secret (System/getenv "MY_CLIENT_SECRET")
   :authorize-uri  "https://accounts.spotify.com/authorize"
   :redirect-uri (System/getenv "MY_REDIRECT_URI")
   :access-token-uri "https://accounts.spotify.com/api/token"
   :scope (slurp "debug/scopes")})

(defn authorize-uri []
  (str
   (:authorize-uri oauth2-params)
   "?response_type=code"
   "&client_id="
   (http-util/url-encode (:client-id oauth2-params))
   "&redirect_uri="
   (http-util/url-encode (:redirect-uri oauth2-params))
   "&scope="
   (http-util/url-encode (:scope oauth2-params))
   "&state="
   (http-util/url-encode csrf-token)))

(defn get-authentication-response [response-params]
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

(defn refresh-tokens
  "Request a new token pair"
  [refresh-token]
  (let [{{access-token :access_token refresh-token :refresh_token} :body}
        (http/post (:access-token-uri oauth2-params)
                   {:form-params {:grant_type       "refresh_token"
                                  :refresh_token    refresh-token}
                    :basic-auth [(:client-id oauth2-params) (:client-secret oauth2-params)]
                    :as          :json})]
    [access-token refresh-token]))
