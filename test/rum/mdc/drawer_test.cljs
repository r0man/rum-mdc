(ns rum.mdc.drawer-test
  (:require [devcards.core :refer-macros [defcard]]
            [rum.core :as rum]
            [rum.mdc.drawer :refer [drawer]]
            [sablono.core :refer [html]]))

(defonce open?
  (atom false))

(defn my-drawer-content []
  [:div.my-drawer__content.mdc-list
   [:a.mdc-list-item
    {:href "#!/rum.mdc.button_test"}
    "Button Cards"]
   [:a.mdc-list-item
    {:href "#!/rum.mdc.checkbox_test"}
    "Checkbox Cards"]
   [:a.mdc-list-item
    {:href "#!/rum.mdc.drawer_test"}
    "Drawer Cards"]
   [:a.mdc-list-item
    {:href "#!/rum.mdc.radio_test"}
    "Radio Cards"]
   [:a.mdc-list-item
    {:href "#!/rum.mdc.textfield_test"}
    "Textfield Cards"]])

(rum/defc my-drawer < rum/reactive []
  [:div
   (drawer
    {:header "Header"
     :class "my-drawer"
     :content (my-drawer-content)
     :on-change #(reset! open? %1)
     :open? (rum/react open?)})
   [:main
    [:button.mdc-button.mdc-button--raised.mdc-button--primary
     {:on-click #(swap! open? not)}
     (if @open? "Close" "Open") " Drawer"]]])

(defcard temporary-drawer
  (html (my-drawer)))
