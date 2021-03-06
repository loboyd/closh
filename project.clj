(defproject closh "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :clean-targets ["build" :target-path]

  :dependencies [[org.clojure/clojure "1.9.0-beta2"]
                 [org.clojure/clojurescript "1.9.946"]
                ;  [org.clojure/spec.alpha "0.1.134"]

                 ; Lumo dependencies
                 [com.cognitect/transit-cljs  "0.8.239"]
                 [malabarba/lazy-map          "1.3"]
                 [fipp                        "0.6.10"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-npm "0.6.1"]
            [lein-doo "0.1.6"]
            [org.bodil/lein-noderepl "0.1.11"]
            [lein-kibit "0.1.6-beta2"]
            [lein-bikeshed "0.5.0"]
            [venantius/yagni "0.1.4"]
            [jonase/eastwood "0.2.5"]
            [funcool/codeina "0.4.0" :exclusions [org.clojure/clojure]]]

  :profiles {:dev {:dependencies [[lein-doo "0.1.6"]]}}

  :npm {:dependencies [[source-map-support "0.4.0"]]
        :package {}};; To distribute a node binary, set :bin
                  ; :bin {"closh" "bin/main.js"}
                  ;; To distribute a node library, set :main
                  ; :main "bin/main.js"
                  ;; To push to a publicly available npm name set :private
                  ; :private false


  :aliases {"build" ["cljsbuild" "once" "main"]
            "test" ["doo" "node" "test-node" "once"]
            "test-auto" ["doo" "node" "test-node" "auto"]
            "lint" ["do" ["kibit"] ["eastwood"] ["bikeshed"] ["yagni"]]}

  :codeina {:reader :clojurescript}

  ;; This release-task does lein npm publish in addition to lein deploy
  :release-tasks [["vcs" "assert-committed"]
                  ["clean"]
                  ["build"]
                  ["change" "version"
                   "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ;; Uncomment the following line to distribute via npm
                  ; ["npm" "publish"]
                  ;; The following line deploys to a maven repo
                  ["deploy"]
                  ["change" "version"
                   "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]

  :cljsbuild {:builds [{:id "main"
                        :source-paths ["src" "/home/me/dl/git/lumo/src/cljs/snapshot"]
                        :compiler {:output-to "build/main.js"
                                   :output-dir "build/js"
                                   :optimizations :advanced
                                   :target :nodejs
                                   :source-map "build/main.js.map"}}
                       {:id "test-node"
                        :source-paths ["src" "test"]
                        :compiler {:main closh.core-runner
                                   :output-to     "build/test-node.js"
                                   :target :nodejs
                                   :output-dir    "build/test-js"
                                   :optimizations :none
                                   :pretty-print  true}}]})
