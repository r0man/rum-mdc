(ns rum.mdc.events
  (:require #?(:cljs [goog.events :as events])
            [rum.core :as rum]
            [rum.mdc.util :as util]))

(defn remap-touch
  "Remap touch events to pointer events, if the browser doesn't
  support touch events."
  [event-name]
  (let [event-name (name event-name)]
    (if (not util/touch-supported?)
      (case event-name
        "touchstart" "pointerdown"
        "touchmove" "pointermove"
        "touchend" "pointerup"
        event-name)
      event-name)))

(defn register
  "Register an event handler."
  [state ref event handler & [opts]]
  {:pre  [(ifn? handler)]}
  #?(:cljs (events/listen
            (rum/ref-node state (name ref))
            (remap-touch event)
            handler)))

(defn deregister
  "Deregister an event handler."
  [state ref event handler & [opts]]
  {:pre  [(ifn? handler)]}
  #?(:cljs (events/unlisten
            (rum/ref-node state (name ref))
            (remap-touch event)
            handler)))
