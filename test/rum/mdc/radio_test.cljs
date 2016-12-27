(ns rum.mdc.radio-test
  (:require [devcards.core :refer [defcard]]
            [rum.core :as rum]
            [rum.mdc.radio :refer [radio]]
            [sablono.core :refer [html]]))

(defcard test-active
  (let [attrs {:name "test-active"}]
    (html
     [:div
      (radio (assoc attrs :id "test-active-1"))
      [:label {:for "test-active-1"} "Radio 1"]
      (radio (assoc attrs :id "test-active-2"))
      [:label {:for "test-active-2"} "Radio 2"]])))

(defcard test-disabled
  (let [attrs {:name "test-disabled" :disabled? true} ]
    (html
     [:div
      (radio (assoc attrs :id "test-disabled-1"))
      [:label {:for "test-disabled-1"} "Radio 1"]
      (radio (assoc attrs :id "test-disabled-2"))
      [:label {:for "test-disabled-2"} "Radio 1"]])))
