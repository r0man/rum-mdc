(ns rum.mdc.events
  (:require [rum.core :as rum]))

(defn register
  "Register an event handler."
  [state ref event handler]
  (some-> (rum/ref-node state (name ref))
          (.addEventListener (name event) handler)))

(defn deregister
  "Unregister an event handler."
  [state ref event handler]
  (some-> (rum/ref-node state (name ref))
          (.removeEventListener (name event) handler)))
