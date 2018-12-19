(defproject recipe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :dependencies [[org.clojure/clojure "1.8.0"]
                 ;[org.clojure/java.jdbc "0.7.8"]
                 ;[org.postgresql/postgresql "9.2-1003-jdbc4"]
                 ;[postgresql "9.3-1102.jdbc41"]
                 ;[java-jdbc/dsl "0.1.0"]
                 [clj-spotify "0.1.8"]
                 [http-kit "2.3.0"]
                 [clj-http "3.9.1"]
                 [slingshot "0.12.2"]
                 [compojure "1.6.1"]
                 [org.postgresql/postgresql "42.2.2"]
                 [com.layerware/hugsql "0.4.9"]]
  
  :main ^:skip-aot recipe.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
