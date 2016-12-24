(ns rum.mdc.drawer
  (:require [rum.core :as rum]))

(rum/defcs drawer <
  (rum/local {:help #{} :label #{} :root #{}} ::classes)
  ;; foundation
  [state {:keys [disabled? help id label min-length type value required?]}]
  [:div])
