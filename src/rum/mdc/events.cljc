(ns rum.mdc.events
  (:require #?(:cljs [goog.events :as events])
            [rum.core :as rum]
            [rum.mdc.util :as util]))

(defn remap-event
  "Remap touch events to pointer events, if the browser doesn't support touch events."
  [event-name]
  (let [event-name (name event-name)]
    (if (not util/touch-supported?)
      (case event-name
        "touchstart" "pointerdown"
        "touchmove" "pointermove"
        "touchend" "pointerup"
        event-name)
      event-name)))

(defn apply-passive
  "Remap touch events to pointer events, if the browser doesn't support touch events."
  [opts]
  #?(:cljs (cond
             (map? opts)
             (clj->js opts)
             :else false)))

(defn register
  "Register an event handler."
  [state ref event handler & [opts]]
  {:pre  [(ifn? handler)]}
  #?(:cljs (events/listen
            (rum/ref-node state (name ref))
            (remap-event event)
            handler)))

(defn deregister
  "Unregister an event handler."
  [state ref event handler & [opts]]
  {:pre  [(ifn? handler)]}
  #?(:cljs (events/unlisten
            (rum/ref-node state (name ref))
            (remap-event event)
            handler)))
