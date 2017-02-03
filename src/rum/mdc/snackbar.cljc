(ns rum.mdc.snackbar
  (:require #?(:cljs [cljsjs.material-components :as mdc])
            [rum.mdc.button :refer [button]]
            [rum.core :as rum]
            [rum.mdc.classes :as class]
            [rum.mdc.events :as events]))

(defn- snackbar-data
  "Return the snackbar data from `state`."
  [state]
  (-> state :rum/args first))

(defn- snackbar-show
  "Show the snackbar data from `state`."
  [state]
  #?(:cljs (.show (::snackbar state) (clj->js (snackbar-data state)))))

(defn- snackbar-show?
  "Return the snackbar data from `state`."
  [old-state new-state]
  (and (empty? (snackbar-data old-state))
       (not (empty? (snackbar-data new-state)))))

(def foundation
  {:will-mount
   (fn [state]
     #?(:clj state
        :cljs
        (->> (js/mdc.snackbar.MDCSnackbarFoundation.
              #js {:addClass
                   #(class/add state :root %1)

                   :removeClass
                   #(class/remove state :root %1)

                   :setAriaHidden
                   #(reset! (:aria-hidden state) true)

                   :unsetAriaHidden
                   #(reset! (:aria-hidden state) false)

                   :setActionAriaHidden
                   #(reset! (:action-aria-hidden state) true)

                   :unsetActionAriaHidden
                   #(reset! (:action-aria-hidden state) false)

                   :registerTransitionEndHandler
                   #(events/register state :root :transitionend %)

                   :deregisterTransitionEndHandler
                   #(events/deregister state :root :transitionend %)})
             (assoc state ::snackbar))))
   :did-mount
   (fn [state]
     (.init (::snackbar state))
     (when-not (empty? (snackbar-data state))
       (snackbar-show state))
     state)
   :did-remount
   (fn [old-state new-state]
     (when (snackbar-show? old-state new-state)
       (snackbar-show new-state))
     new-state)
   :will-unmount
   (fn [state]
     (.destroy (::snackbar state))
     state)})

(rum/defcs snackbar < (class/mixin :root)
  (rum/local true :action-aria-hidden)
  (rum/local true :aria-hidden)
  foundation
  [{:keys [aria-hidden
           action-aria-hidden] :as state}
   {:keys [action-handler
           action-on-bottom
           action-text
           message
           multiline
           timeout] :as opts}]
  [:div.mdc-snackbar
   {:aria-hidden @aria-hidden
    :class (class/get state :root)
    :ref "root"}
   [:div.mdc-snackbar__text message]
   [:div.mdc-snackbar__action-wrapper
    {:aria-hidden @action-aria-hidden}
    (button action-text
            {:class "mdc-snackbar__action-button"
             :on-click action-handler})]])
