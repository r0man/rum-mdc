(ns rum.mdc.checkbox-test
  (:require [devcards.core :refer-macros [defcard]]
            [rum.mdc :as mdc]
            [sablono.core :refer [html]]))

(defcard checkbox
  (html (mdc/checkbox
         {:checked true
          ;; :on-change (prn "checkbox changed")
          })))
