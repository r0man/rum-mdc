(ns rum.mdc.checkbox-test
  (:require [devcards.core :refer-macros [defcard]]
            [rum.core :as rum]
            [rum.mdc.checkbox :refer [checkbox]]
            [sablono.core :refer [html]]))

(def state (atom false))

(rum/defc my-checkbox < rum/reactive []
  (checkbox {:checked (rum/react state)
             :on-change #(swap! state not)}))

(defcard test-checkbox
  (html (my-checkbox)))
