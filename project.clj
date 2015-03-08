(defproject cauchy-jobs-kestrel "0.1.0-SNAPSHOT"
  :description "Cauchy jobs to monitor Kestrel queuing"
  :url "https://github.com/pguillebert/cauchy-jobs-kestrel"
  :scm {:name "git"
        :url "https://github.com/pguillebert/cauchy-jobs-kestrel"}
  :pom-addition [:developers
                 [:developer
                  [:name "Philippe Guillebert"]
                  [:url "https://github.com/pguillebert"]
                  [:timezone "+1"]]]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :signing {:gpg-key "93FEB8D7"}
  :deploy-repositories [["clojars" {:creds :gpg}]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-api "1.7.10"]

                 [cauchy "0.1.0-SNAPSHOT"]
                 [clj-http "1.0.1"]])
