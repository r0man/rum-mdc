(ns rum.mdc.textfield
  (:require #?(:cljs [cljsjs.mdc :as mdc])
            [clojure.string :as str]
            [rum.core :as rum]
            [sablono.core :refer [html]]))

(defn classes [state type]
  (str/join " " (-> state ::classes deref type)))

(defn add-class [state type class]
  (swap! (::classes state) update type conj class))

(defn remove-class [state type class]
  (swap! (::classes state) update type disj class))

(defn register [state ref event handler]
  (some-> (rum/ref-node state (name ref))
          (.addEventListener (name event) handler)))

(defn deregister [state ref event handler]
  (some-> (rum/ref-node state (name ref))
          (.removeEventListener (name event) handler)))

(def foundation
  {:will-mount
   (fn [state]
     #?(:cljs
        (->> (js/mdc.textfield.MDCTextfieldFoundation.
              #js {:addClass #(add-class state :root %1)
                   :addClassToHelptext #(add-class state :help %1)
                   :addClassToLabel #(add-class state :label %1)
                   :deregisterInputBlurHandler #(deregister state :input :blur %1)
                   :deregisterInputFocusHandler #(deregister state :input :focus %1)
                   :registerInputBlurHandler #(register state :input :blur %1)
                   :registerInputFocusHandler #(register state :input :focus %1)
                   :removeClass #(remove-class state :root %1)
                   :removeClassFromHelp #(remove-class state :help %1)
                   :removeClassFromLabel #(remove-class state :label %1)
                   :getNativeInput
                   (fn []
                     (when-let [element (rum/ref-node state "input")]
                       #js {:checkValidity (constantly true)
                            :disabled false
                            :value (.-value element)}))})
             (assoc state ::foundation))))
   :did-mount
   (fn [state]
     (.init (::foundation state))
     state)
   :will-unmount
   (fn [state]
     (.destroy (::foundation state))
     state)})

(rum/defcs textfield <
  (rum/local {:help #{} :label #{} :root #{}} ::classes)
  foundation
  [state {:keys [id label]}]
  [:div.mdc-textfield
   {:class (classes state :root)
    :ref "root"}
   [:input.mdc-textfield__input
    {:id id
     :type "text"
     :ref "input"}]
   [:label.mdc-textfield__label
    {:class (classes state :label)
     :for id} label]
   [:p.mdc-textfield-helptext
    {:class (classes state :help)}
    "This will be displayed on your public profile"]])
