(ns rum.mdc.textfield
  (:require #?(:cljs [cljsjs.mdc :as mdc])
            [clojure.string :as str]
            [rum.core :as rum]
            [rum.mdc.class :as class]
            [rum.mdc.events :as events]
            [sablono.core :refer [html]]))

(defn native-input [state]
  #?(:cljs #(when-let [element (rum/ref-node state "input")]
              #js {:checkValidity (constantly true)
                   :disabled false
                   :value (.-value element)})))

(def foundation
  {:will-mount
   (fn [state]
     #?(:cljs
        (->> (js/mdc.textfield.MDCTextfieldFoundation.
              #js {:addClass #(class/add state :root %1)
                   :addClassToHelptext #(class/add state :help %1)
                   :addClassToLabel #(class/add state :label %1)
                   :deregisterInputBlurHandler #(events/deregister state :input :blur %1)
                   :deregisterInputFocusHandler #(events/deregister state :input :focus %1)
                   :registerInputBlurHandler #(events/register state :input :blur %1)
                   :registerInputFocusHandler #(events/register state :input :focus %1)
                   :removeClass #(class/remove state :root %1)
                   :removeClassFromHelp #(class/remove state :help %1)
                   :removeClassFromLabel #(class/remove state :label %1)
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
  (class/mixin {:help #{} :label #{} :root #{}})
  foundation
  [state {:keys [disabled? help id label min-length type value required?]}]
  [:label.mdc-textfield
   {:class
    (cond-> (class/get state :root)
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
     (cond-> (class/get state :label)
       value (conj "mdc-textfield__label--float-above"))
     :for id}
    label]])
