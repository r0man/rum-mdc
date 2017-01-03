(ns rum.mdc.button-test
  (:require [devcards.core :refer-macros [defcard]]
            [rum.mdc.button :refer [button]]
            [sablono.core :refer [html]]))

(defcard default
  (html (button "Text")))

(defcard raised
  (html (button "Text" {:mdc {:raised? true}})))

(defcard dense
  (html (button "Text" {:mdc {:dense? true}})))

(defcard dense-raised
  (html (button "Text" {:mdc {:dense? true :raised? true}})))

(defcard compact
  (html (button "Text" {:mdc {:compact? true}})))

(defcard compact-raised
  (html (button "Text" {:mdc {:compact? true :raised? true}})))

(defcard primary
  (html (button "Text" {:mdc {:primary? true}})))

(defcard primary-raised
  (html (button "Text" {:mdc {:primary? true :raised? true}})))

(defcard accent
  (html (button "Text" {:mdc {:accent? true}})))

(defcard accent-raised
  (html (button "Text" {:mdc {:accent? true :raised? true}})))

(defcard disabled
  (html (button "Text" {:disabled true})))

(defcard disabled-raised
  (html (button "Text" {:disabled true :mdc {:raised? true}})))
