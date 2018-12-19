(ns recipe.utils
  (:gen-class)
  (:require [clj-http.client :as http]))

(defn- paginate [accum page-obj access-token]
  (println (:next page-obj))
  (let [accum     (concat accum (:items page-obj))
        next-page (:next page-obj)]
    (if (nil? next-page)
      accum
      (let [resp (http/get next-page {:oauth-token access-token
                                      :as          :json})]
        (paginate accum (:body resp) access-token)))))

(defn fetch-all [api-fn params access-token]
  (let [page-obj   (api-fn params access-token)
        accum-objs (paginate [] page-obj access-token)]
        accum-objs))
