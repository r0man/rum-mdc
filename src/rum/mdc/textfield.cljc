(ns rum.mdc.textfield
  (:require #?(:cljs [cljsjs.mdc :as mdc])
            [clojure.string :as str]
            [rum.core :as rum]
            [sablono.core :refer [html]]))

(defn classes [state type]
  (-> state ::classes deref type))

(defn add-class [state type class]
  (swap! (::classes state) update type conj class))

(defn remove-class [state type class]
  (swap! (::classes state) update type disj class))

(defn native-input [state]
  #?(:cljs #(when-let [element (rum/ref-node state "input")]
              #js {:checkValidity (constantly true)
                   :disabled false
                   :value (.-value element)})))

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
                   :getNativeInput (native-input state)})
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
  [state {:keys [disabled? help id label min-length type value required?]}]
  [:label.mdc-textfield
   {:class
    (cond-> (classes state :root)
      value (conj "mdc-textfield--upgraded"))
    :ref "root"}
   [:input.mdc-textfield__input
    {:aria-controls help
     :disabled disabled?
     :id id
     :type (or type "text")
     :ref "input"
     :required required?
     :min-length min-length
     :value value}]
   [:div.mdc-textfield__label
    {:class
     (cond-> (classes state :label)
       value (conj "mdc-textfield__label--float-above"))
     :for id}
    label]])
