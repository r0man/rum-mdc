(ns rum.mdc.radio
  (:require [rum.mdc.classes :as class]
            [rum.mdc.ripple :as ripple]
            [rum.core :as rum]))

(def foundation
  {:will-mount
   (fn [state]
     #?(:clj state
        :cljs
        (->> (js/mdc.radio.MDCRadioFoundation.
              #js {:getNativeInput #(rum/ref-node state "input")})
             (assoc state ::radio))))
   :did-mount
   (fn [state]
     (.init (::radio state))
     state)
   :will-unmount
   (fn [state]
     (.destroy (::radio state))
     state)})

(rum/defcs radio < (class/mixin :root) foundation (ripple/foundation ::radio {:unbounded? true})
  "Render a Material Design radio button."
  [state {:keys [class checked? disabled? id name on-change value]}]
  [:div.mdc-radio
   {:class
    (cond-> (class/get state :root)
      class (conj class)
      disabled? (conj "mdc-radio--disabled"))
    :ref "root"}
   [:input.mdc-radio__native-control
    {:checked checked?
     :disabled disabled?
     :id id
     :name name
     :on-change on-change
     :ref "input"
     :type "radio"
     :value value}]
   [:div.mdc-radio__background
    [:div.mdc-radio__outer-circle]
    [:div.mdc-radio__inner-circle]]])
