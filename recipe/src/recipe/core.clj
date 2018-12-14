(ns recipe.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.util.codec :as codec]
            [clojure.walk :as walk]
            [recipe.user :as user]
            [recipe.authorization :as auth]))

(defn callback-handler [req]
  (let [response-params (walk/keywordize-keys (codec/form-decode (:query-string req)))
        tokens          (auth/get-authentication-response response-params)
        resp-body       (user/process {:access (:access_token tokens) :refresh (:refresh_token tokens)})
        response        {:status 200
                         :headers {"Content-Type" "text/html"}
                         :body resp-body}]
    response))

(defroutes app-routes
  (GET "/" [] (resp/file-response "index.html" {:root "public"}))
  (GET "/login" [] (resp/redirect (auth/authorize-uri)))
  (GET "/callback" [] callback-handler)
  (route/not-found "You Must Be New Here"))

(defn -main
  "This is our app's entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "1234"))]
    (server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))