(ns rum.mdc.radio
  (:require [rum.core :as rum]))

(def foundation
  {:will-mount
   (fn [state]
     #?(:clj state
        :cljs
        (->> (js/mdc.radio.MDCRadioFoundation.
              #js {:getNativeInput #(rum/ref-node state "input")})
             (assoc state ::foundation))))
   :did-mount
   (fn [state]
     (.init (::foundation state))
     state)
   :will-unmount
   (fn [state]
     (.destroy (::foundation state))
     state)})

(rum/defcs radio < foundation
  "Render a Material Design radio button."
  [state {:keys [class disabled? id name on-change]}]
  [:div.mdc-radio
   {:class
    (cond-> #{}
      class (conj class)
      disabled? (conj "mdc-radio--disabled"))
    :ref "root"}
   [:input.mdc-radio__native-control
    {:disabled disabled?
     :id id
     :name name
     :on-change on-change
     :ref "input"
     :type "radio"}]
   [:div.mdc-radio__background
    [:div.mdc-radio__outer-circle]
    [:div.mdc-radio__inner-circle]]])
