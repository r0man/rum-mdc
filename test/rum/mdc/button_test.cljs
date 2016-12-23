(ns rum.mdc.button-test
  (:require [devcards.core :refer-macros [defcard]]
            [sablono.core :refer [html]]))

(defcard default
  (html [:button.mdc-button "Text"]))

(defcard default-with-accent
  (html [:button.mdc-button.mdc-button--accent "Text"]))

(defcard default-with-accent
  (html [:button.mdc-button.mdc-button--accent "Text"]))
