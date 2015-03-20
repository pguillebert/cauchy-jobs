(ns cauchy.jobs.kestrel
  (:require [clj-http.client :as http]
            [cauchy.jobs.utils :as utils]))

(defn fetch-stats
  [{:keys [host port period] :or {host "localhost" port 3334 period 3600}}]
  (let [url (str "http://" host ":" port "/stats?period=" period)]
    (:body (http/get url {:as :json}))))

(defn all-queues
  [stats]
  (->> stats
      (:gauges)
      (keys)
      (map (fn [^clojure.lang.Keyword kw]
             (let [n (.toString kw)]
               (re-find #"q\/([a-zA-Z.+-_]+)\/" n))))
      (map second)
      (into #{})
      (remove nil?)))

(defn mk-key
  [queue metric]
  (keyword (str "q/" queue "/" metric)))

(defn get-queue-info
  [stats queue period]
  (let [items (get-in stats [:gauges (mk-key queue "items")])
        age (get-in stats [:gauges (mk-key queue "age_msec")])
        put (get-in stats [:counters (mk-key queue "put_items")])
        get (get-in stats [:counters (mk-key queue "get_items_hit")])
        put-rate (double (/ put period))
        get-rate (double (/ get period)) ]
    {"items" items "age" age
     "put_rate" put-rate "get_rate" get-rate}))

(def default-thresholds
  {"age" {:warn 300000 :crit 900000 :comp >}
   "items" {:warn 10000 :crit 100000 :comp >}
   "put_rate" {:warn 0.5 :crit 0.01 :comp <}
   "get_rate" {:warn 0.5 :crit 0.01 :comp <}})

(defn kestrel-stats
  ([{:keys [thresholds host port period]
     :or {period 3600}
     :as conf}]
   (let [thresholds (merge-with merge default-thresholds thresholds)
         stats (fetch-stats conf)
         infos (->> (all-queues stats)
                    (map (fn [queue]
                           [queue
                            (get-queue-info stats queue period)]))
                    (into {}))]
     (for [[queue data] infos
           [sname value] data]
       {:service (str queue "." sname)
        :metric value
        :state (utils/threshold (get thresholds sname) value)})))
  ([] (kestrel-stats {})))
