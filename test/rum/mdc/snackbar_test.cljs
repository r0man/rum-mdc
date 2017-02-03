(ns rum.mdc.snackbar-test
  (:require [clojure.spec :as s]
            [clojure.test.check.generators :as g]
            [clojure.spec.impl.gen :as gen]
            [devcards.core :refer-macros [defcard]]
            [sablono.core :refer [html]]
            [rum.core :as rum]
            [rum.mdc.button :refer [button]]
            [rum.mdc.snackbar :as snackbar]))

(rum/defc snackbar < rum/reactive [state]
  [:span
   [:pre (pr-str (rum/react state))]
   (snackbar/snackbar (rum/react state))])

(defn show-snackbar [state]
  (reset! state {:action-handler #(js/alert "Undone")
                 :action-text "Undo"
                 :message "Message deleted"})
  (.setTimeout js/window #(reset! state {}) 3000))

(defcard snackbar-1
  (let [state (atom {})]
    (html [:div
           (button "Show" {:on-click #(show-snackbar state)})
           (snackbar state)])))
