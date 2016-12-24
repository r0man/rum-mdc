(ns rum.mdc.drawer-test
  (:require [devcards.core :refer-macros [defcard]]
            [sablono.core :refer [html]]
            [rum.mdc.drawer :refer [drawer]]))

(defcard permanent-drawer
  (html
   [:div
    [:nav {:class "mdc-permanent-drawer mdc-typography"}
     [:div {:class "mdc-permanent-drawer__toolbar-spacer"}]
     [:div {:class "mdc-permanent-drawer__content"}
      [:nav {:id "icon-with-text-demo", :class "mdc-list"}
       [:a {:class "mdc-list-item mdc-permanent-drawer--selected", :href "#"}
        [:i {:class "material-icons mdc-list-item__start-detail", :aria-hidden "true"}
         "inbox"] "Inbox"]
       [:a {:class "mdc-list-item", :href "#"}
        [:i {:class "material-icons mdc-list-item__start-detail", :aria-hidden "true"}
         "star"] "Star"]
       ]]
     ]
    [:div "Toolbar and page content go inside here."]
    ]))
