(ns rum.mdc.textfield-test
  (:require [devcards.core :refer-macros [defcard]]
            [sablono.core :refer [html]]
            [rum.mdc.textfield :refer [textfield]]))

(defcard default
  (textfield
   {:id "name"
    :label "Name"}))
