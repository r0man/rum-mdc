(ns rum.mdc.checkbox
  (:require #?(:cljs [cljsjs.mdc :as mdc])
            [rum.core :as rum]
            [rum.mdc.classes :as class]
            [rum.mdc.events :as events]))

(defn native-control [state]
  (rum/ref-node state "input"))

(def foundation
  {:will-mount
   (fn [state]
     #?(:clj state
        :cljs
        (->> (js/mdc.checkbox.MDCCheckboxFoundation.
              #js {:addClass #(class/add state :root %1)
                   :deregisterAnimationEndHandler #(events/deregister state :root :animationend %1)
                   :deregisterChangeHandler #(events/register state :input :change %1)
                   :forceLayout (fn [] (prn "Force checkbox layout"))
                   :getNativeControl #(native-control state)
                   :isAttachedToDOM #(some? (native-control state))
                   :registerAnimationEndHandler #(events/register state :root :animationend %1)
                   :registerChangeHandler #(events/register state :input :change %1)
                   :removeClass #(class/remove state :root %1)})
             (assoc state ::checkbox))))
   :did-mount
   (fn [state]
     (.init (::checkbox state))
     state)
   :will-unmount
   (fn [state]
     (.destroy (::checkbox state))
     state)})

(rum/defcs checkbox < (class/mixin :root) foundation
  [state {:keys [class] :as opts}]
  [:div.mdc-checkbox
   {:class
    (cond-> (class/get state :root)
      class (conj class))
    :ref "root"}
   [:input.mdc-checkbox__native-control
    {:checked (:checked opts)
     :on-change (:on-change opts)
     :ref "input"
     :type "checkbox"}]
   [:div.mdc-checkbox__background
    [:svg.mdc-checkbox__checkmark
     {:version "1.1"
      ;; :xmlns "http://www.w3.org/2000/svg"
      :viewBox "0 0 24 24"}
     [:path.mdc-checkbox__checkmark__path
      {:d "M1.73,12.91 8.1,19.28 22.79,4.59"
       :fill "none"
       :stroke "white"}]]
    [:div.mdc-checkbox__mixedmark]]])
