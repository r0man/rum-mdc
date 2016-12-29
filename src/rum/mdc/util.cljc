(ns rum.mdc.util)

(def tab-data "data-mdc-tabindex")
(def tab-data-handled "data-mdc-tabindex-handled")

(def touch-supported?
  (some? #?(:clj nil :cljs (js* "'ontouchstart' in document"))))

(defn make-element-untabbable
  "Remove the tabindex attribute from `element`."
  [element]
  (.setAttribute element "tabindex" -1))

(defn save-element-tab-state
  "Save the tab state of `element`."
  [element]
  (when (.hasAttribute element "tabindex")
    (.setAttribute element tab-data (.getAttribute element "tabindex")))
  (.setAttribute element tab-data-handled true))

(defn restore-element-tab-state
  "Restore the tab state of `element`."
  [element]
  (when (.hasAttribute element tab-data-handled)
    (if (.hasAttribute element tab-data)
      (do (.setAttribute element "tabindex" (.getAttribute element tab-data))
          (.removeAttribute element tab-data))
      (.removeAttribute element "tabindex"))
    (.removeAttribute element tab-data-handled)))
