(defproject rum-mdc "0.1.0-SNAPSHOT"
  :description "Material Design components for Rum"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.7.1"
  :dependencies [[cljsjs/material-components-web "0.1.1-0"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [rum "0.10.7"]
                 [sablono "0.7.6"]]
  :plugins [[lein-cljsbuild "1.1.5" :exclusions [org.clojure/clojure]]
            [lein-figwheel "0.5.8"]
            [lein-npm "0.6.2"]]
  :npm {:dependencies [[material-components-web "0.1.1"]]}
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :cljsbuild {:builds [{:id "devcards"
                        :source-paths ["src" "test"]
                        :figwheel {:devcards true
                                   :open-urls ["http://localhost:3449/index.html"]}
                        :compiler {:main "rum.mdc.test"
                                   :asset-path "js/compiled/devcards_out"
                                   :output-to  "resources/public/js/compiled/rum-mdc-devcards.js"
                                   :output-dir "resources/public/js/compiled/devcards_out"
                                   :source-map-timestamp true}}]}
  :figwheel {:css-dirs ["resources/public/css"] }
  :profiles {:dev {:dependencies [[binaryage/devtools "0.8.3"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [devcards "0.2.2"]
                                  [figwheel-sidecar "0.5.8"]
                                  [cljsjs/react "15.4.0-0"]
                                  [cljsjs/react-dom "15.4.0-0"]
                                  [org.clojure/core.async "0.2.395"]]
                   :source-paths ["src" "dev"]
                   :repl-options {:init (set! *print-length* 50)
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
