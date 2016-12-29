(ns rum.mdc.textfield
  (:require #?(:cljs [cljsjs.material-components :as mdc])
            [clojure.string :as str]
            [rum.core :as rum]
            [rum.mdc.classes :as class]
            [rum.mdc.events :as events]
            [sablono.core :refer [defhtml html]]))

(defn check-validity [state]
  (if-let [valid? (-> state :rum/args first :valid?)]
    #(valid? (.-value (rum/ref-node state "input")))
    (constantly true)))

(defn native-input [state]
  (when-let [element (rum/ref-node state "input")]
    #?(:cljs #js {:checkValidity (check-validity state)
                  :disabled false
                  :value (.-value element)})))

(def foundation
  {:will-mount
   (fn [state]
     #?(:clj state
        :cljs
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
                   :getNativeInput #(native-input state)})
             (assoc state ::textfield))))
   :did-mount
   (fn [state]
     (.init (::textfield state))
     state)
   :will-unmount
   (fn [state]
     (.destroy (::textfield state))
     state)})

(rum/defcs textfield < (class/mixin :help :label :root) foundation
  [state {:keys [disabled? class help id label min-length type value required? on-change valid?] :as opts}]
  [:label.mdc-textfield
   {:class
    (cond-> (class/get state :root)
      class (conj class)
      value (conj "mdc-textfield--upgraded"))
    :ref "root"}
   [:input.mdc-textfield__input
    {:aria-controls help
     :auto-complete (:auto-complete opts)
     :disabled disabled?
     :id id
     :type (or type "text")
     :ref "input"
     :required required?
     :min-length min-length
     :on-change on-change
     :value value}]
   [:div.mdc-textfield__label
    {:class
     (cond-> (class/get state :label)
       value (conj "mdc-textfield__label--float-above"))
     :for id}
    label]])
