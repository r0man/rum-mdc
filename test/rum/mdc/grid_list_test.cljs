(ns rum.mdc.grid-list-test
  (:require [devcards.core :refer-macros [defcard]]
            [rum.core :as rum]
            [rum.mdc.grid-list :refer [grid-list]]
            [sablono.core :refer [html]]))

(rum/defc my-grid-list-item < rum/static
  [title support-text]
  [:li.mdc-grid-tile
   [:div.mdc-grid-tile__primary
    [:img.mdc-grid-tile__primary-content
     {:src "http://material-components-web.appspot.com/images/1-1.jpg"}]]
   [:span.mdc-grid-tile__secondary
    [:span.mdc-grid-tile__title title]
    [:span.mdc-grid-tile__support-text support-text]]])

(rum/defc my-grid-list [& [opts]]
  (grid-list
   [:ul.mdc-grid-list__tiles
    (for [n (range 12)]
      (my-grid-list-item
       (str "Title " (inc n))
       (str "Support text " (inc n))))]
   (assoc opts :class "mdc-grid-list--twoline-caption")))

(defcard test-grid-list
  (html (my-grid-list)))

(defcard test-grid-list-rtl
  (html (my-grid-list {:dir "rtl"})))
