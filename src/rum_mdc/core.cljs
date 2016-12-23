(ns rum-mdc.core
  (:require [devcards.core :refer-macros [defcard deftest]]
            [sablono.core :refer [html]]
            [rum.core :as rum]
            [clojure.string :as str]
            [cljsjs.mdc :as mdc]))

(enable-console-print!)

(defcard first-card
  (html
   [:div
    [:h1 "This is your first devcard!"]]))

(defcard flat-button
  (html [:button.mdc-button "Flat button"]))

(defcard colored-button
  (html [:button.mdc-button.mdc-button--accent "Colored button"]))

(def MDCCheckboxFoundation js/mdc.checkbox.MDCCheckboxFoundation)

(defn state
  [& [initial-state]]
  {:init
   (fn [state props]
     (assoc state ::state (atom (assoc initial-state :classes #{}))))})

(defn checkbox-root [state]
  (rum/ref-node state "root"))

(defn checkbox-input [state]
  (rum/ref-node state "input"))

(def checkbox-mixin
  {:will-mount
   (fn [state]
     (let [mdc-state (::mdc-state state)]
       (swap! mdc-state merge (first (:rum/args state)))
       (->> (MDCCheckboxFoundation.
             #js {:addClass
                  (fn [class]
                    (prn "ADD CLASS" class)
                    (swap! mdc-state update :classes conj class))
                  :removeClass
                  (fn [class]
                    (prn "REMOVE CLASS" class)
                    (swap! mdc-state update :classes disj class))
                  :registerAnimationEndHandler
                  (fn [handler]
                    (some-> (checkbox-root state)
                            (.addEventListener MDCCheckboxFoundation.strings.ANIM_END_EVENT_NAME handler)))
                  :deregisterAnimationEndHandler
                  (fn [handler]
                    (some-> (checkbox-root state)
                            (.removeEventListener MDCCheckboxFoundation.strings.ANIM_END_EVENT_NAME handler))
                    (prn "deregisterAnimationEndHandler"))
                  :registerChangeHandler
                  (fn [handler]
                    (prn "registerChangeHandler")
                    (.log js/console (checkbox-input state))
                    (some-> (checkbox-input state)
                            (.addEventListener "change" handler)))
                  :deregisterChangeHandler
                  (fn [handler]
                    (prn "deregisterChangeHandler")
                    (.log js/console (checkbox-input state))
                    (some-> (checkbox-input state)
                            (.removeEventListener "change" handler)))
                  :getNativeControl
                  (fn [handler]
                    (prn "getNativeControl" (checkbox-input state))
                    (checkbox-input state))
                  :forceLayout
                  (fn [handler]
                    (some-> (checkbox-input state) (.-offsetWidth)))
                  :isAttachedToDOM
                  (fn [handler]
                    (prn "IS ATTACHED")
                    (some? (checkbox-input state)))})
            (assoc state ::adapter))))
   :did-update
   (fn [state]
     (prn "DID UPDATE")
     (set! (.-indeterminate (checkbox-input state))
           (-> state ::mdc-state deref :indeterminate))
     state)
   ;; :should-update
   ;; (fn [old-state new-state]
   ;;   (prn "SHOULD-UPDATE"
   ;;        (::mdc-state old-state)
   ;;        (::mdc-state new-state))
   ;;   (not= @(::mdc-state old-state)
   ;;         @(::mdc-state new-state)))
   :did-mount
   (fn [state]
     (prn (::mdc-state state))
     (.init (::adapter state))
     state)
   :did-remount
   (fn [old-state state]
     (prn "DID REMOUNT" state)
     state)
   :will-unmount
   (fn [state]
     (.destroy (::adapter state))
     state)})

(rum/defcs checkbox <
  (rum/local {:classes #{}
              :checked false
              :indeterminate false}
             ::mdc-state)
  checkbox-mixin
  [state opts]
  (prn "RENDER")
  (let [mdc-state (some-> state ::mdc-state)]
    [:div.mdc-checkbox
     {:class (str/join " " (:classes @mdc-state))
      :ref "root"}
     (pr-str @mdc-state)
     [:input.mdc-checkbox__native-control
      {:checked (:checked @mdc-state)
       :type "checkbox"
       :ref "input"
       :on-change (fn [event]
                    (prn "CHECKED" (.-checked (.-target event)))
                    (swap! mdc-state assoc
                           :checked (.-checked (.-target event))
                           :indeterminate false))}]
     [:div.mdc-checkbox__background
      [:svg.mdc-checkbox__checkmark
       {:version "1.1"
        :xmlns "http://www.w3.org/2000/svg"
        :viewBox "0 0 24 24"}
       [:path.mdc-checkbox__checkmark__path
        {:d "M1.73,12.91 8.1,19.28 22.79,4.59"
         :fill "none"
         :stroke "white"}]]
      [:div.mdc-checkbox__mixedmark]]]))

(defcard checkbox-test
  (html (checkbox
         {:checked true
          ;; :on-change (prn "checkbox changed")
          })))

(defn main []
  ;; conditionally start the app based on whether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (.render js/ReactDOM (html [:div "This is working"]) node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html
