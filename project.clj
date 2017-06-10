(defproject rum-mdc "0.6.1-SNAPSHOT"
  :description "Material Design components for Rum"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.7.1"
  :dependencies [[cljsjs/material-components "0.12.1-0"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.562" :scope "provided"]
                 [rum "0.10.8"]]
  :plugins [[lein-cljsbuild "1.1.6" :exclusions [org.clojure/clojure]]
            [lein-figwheel "0.5.10"]]
  :clean-targets ^{:protect false} ["resources/public/js" "target"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src" "test"]
                        :figwheel {:open-urls ["http://localhost:3449/index.html"]}
                        :compiler {:devcards true
                                   :main "rum.mdc.test"
                                   :asset-path "js/dev"
                                   :output-to  "resources/public/js/rum-mdc.js"
                                   :output-dir "resources/public/js/dev"
                                   :source-map-timestamp true}}
                       {:id "advanced"
                        :source-paths ["src" "test"]
                        :compiler {:main "rum.mdc.test"
                                   :devcards true
                                   :asset-path "js/advanced"
                                   :output-to "resources/public/js/rum-mdc.js"
                                   :output-dir "resources/public/js/advanced"
                                   :optimizations :advanced
                                   :pretty-print true
                                   :pseudo-names true
                                   :source-map "resources/public/js/rum-mdc.js.map"
                                   :source-map-timestamp true}}]}
  :deploy-repositories [["releases" :clojars]]
  :figwheel {:css-dirs ["resources/public/css"] }
  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.2"]
                                  [devcards "0.2.3"]
                                  [figwheel-sidecar "0.5.10"]
                                  [cljsjs/react "15.5.4-1"]
                                  [cljsjs/react-dom "15.5.4-1"]
                                  [org.clojure/core.async "0.3.443"]]
                   :source-paths ["src" "dev"]
                   :repl-options {:init (set! *print-length* 50)
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
