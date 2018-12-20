(ns recipe.utils
  (:gen-class)
  (:require [clj-http.client :as http]))

(defn- turn-page [accum page-obj access-token]
  (let [accum     (concat accum (:items page-obj))
        next-page (:next page-obj)]
    (if (nil? next-page)
      accum
      (let [resp (http/get next-page {:oauth-token access-token
                                      :as          :json})]
        (turn-page accum (:body resp) access-token)))))

(defn paginate [api-fn params access-token]
  (let [page-obj   (api-fn params access-token)
        aggregated (paginate [] page-obj access-token)]
        aggregated))
