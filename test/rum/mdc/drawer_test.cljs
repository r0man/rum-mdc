(ns rum.mdc.drawer-test
  (:require [devcards.core :refer-macros [defcard]]
            [rum.core :as rum]
            [rum.mdc.drawer :refer [drawer]]
            [sablono.core :refer [html]]))

(def open?
  (atom false))

(defn my-drawer-content []
  [:div.mdc-list
   [:a.mdc-list-item
    {:href "#!/rum.mdc.button_test"}
    [:i.material-icons "inbox"]
    "Button Tests"]
   [:a.mdc-list-item
    {:href "#!/rum.mdc.checkbox_test"}
    [:i..material-icons "star"]
    "Checkbox Tests"]
   [:a.mdc-list-item
    {:href "#!/rum.mdc.drawer_test"}
    [:i.material-icons "send"]
    "Drawer Tests"]
   [:a.mdc-list-item
    {:href "#!/rum.mdc.radio_test"}
    [:i.material-icons "drafts"]
    "Radio Tests"]
   [:a.mdc-list-item
    {:href "#!/rum.mdc.textfield_test"}
    [:i.material-icons "drafts"]
    "Textfield Tests"]])

(rum/defc my-drawer < rum/reactive []
  [:div
   (drawer
    {:header "Header"
     :content (my-drawer-content)
     :on-change #(reset! open? %1)
     :open? (rum/react open?)})
   [:main
    [:button
     {:on-click #(swap! open? not)}
     (if @open? "Close" "Open") " Drawer"]]])

(defcard temporary-drawer
  (html (my-drawer)))
