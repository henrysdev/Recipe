(ns recipe.core
  (:gen-class)
  (:require [clj-spotify.core :refer :all]
            [clj-spotify.util :refer :all]))

(defn grab-track [track-id authtok]
  (clojure.pprint/pprint (get-a-track {:id track-id} authtok)))

(defn -main [& args]
  "main method"
  (def authtok
    (let [client-id (slurp "debug/client_id")
          client-secret (slurp "debug/secret")]
      (get-access-token client-id client-secret)))
  (with-open [rdr (clojure.java.io/reader "debug/fav_songs")]
    (doseq [line (line-seq rdr)]
      (grab-track line authtok))))

;; OAUTH2 => http://leonid.shevtsov.me/post/oauth2-is-easy/