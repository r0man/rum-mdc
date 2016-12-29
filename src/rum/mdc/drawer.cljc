(ns rum.mdc.drawer
  (:require #?(:cljs [cljsjs.material-components :as mdc])
            [rum.mdc.classes :as class]
            [rum.core :as rum]
            [rum.mdc.events :as events]
            [rum.mdc.util :as util]))

(def strings
  #?(:cljs (js->clj js/mdc.drawer.MDCTemporaryDrawerFoundation.strings :keywordize-keys true)))

(defn- handle-drawer [state open?]
  (when-let [handler (some-> state :rum/args first :on-change)]
    (handler open?)))

(defn- open-class? [class]
  (= class "mdc-temporary-drawer--open"))

(defn- add-class [state class]
  (class/add state :root class)
  (when (open-class? class)
    (handle-drawer state true)))

(defn- remove-class [state class]
  (class/remove state :root class)
  (when (open-class? class)
    (handle-drawer state false)))

(defn- drawer-element
  "Returns the drawer HTML element."
  [state]
  (rum/ref-node state "drawer"))

(defn- drawer-element?
  "Returns true if `element` is the drawer element, otherwise false."
  [state element]
  (= element (drawer-element state)))

(defn- root-element
  "Returns the root HTML element."
  [state]
  (rum/ref-node state "root"))

(defn- has-necessary-dom? [state]
  (some? (drawer-element state)))

(defn- drawer-width [state]
  (prn "drawer width"))

(defn- set-translate-x [state value]
  (prn "set-translate-x"))

(defn- update-css-variable [state value]
  (when-let [element (root-element state)]
    (.setProperty (.-style element) (:OPACITY_VAR_NAME strings) value)))

(defn- focusable-elements [state]
  (.querySelectorAll (drawer-element state) (:FOCUSABLE_ELEMENTS strings)))

(defn- rtl? [state]
  (prn "is-rtl")
  false)

(defn- open?
  "Returns true if the drawer is open, otherwise false."
  [state]
  (true? (-> state :rum/args first :open?)))

(defn- should-open?
  "Returns true if the drawer should open, otherwise false."
  [old-state new-state]
  (and (not (open? old-state)) (open? new-state)))

(defn- should-close?
  "Returns true if the drawer should close, otherwise false."
  [old-state new-state]
  (and (open? old-state) (not (open? new-state))))

(defn wrap-click
  "Return a wrapped handler that handles"
  [state click-handler]
  (fn [event]
    (let [target (.-target event)]
      ;; TODO: Issue with native vs React events? Stop event
      ;; propagation only when clicked on any of the following refs.
      (when (or (= target (rum/ref-node state "content"))
                (= target (rum/ref-node state "header"))
                (= target (rum/ref-node state "header-content"))
                (= target (rum/ref-node state "spacer")))
        (click-handler event)))))

(defn- register-drawer-interaction-handler
  "Register a drawer interaction handler."
  [state]
  (fn [event handler]
    (events/register
     state :drawer event
     (if (= (name event) "click")
       (wrap-click state handler) handler))))

(defn- deregister-drawer-interaction-handler
  "Deregister a drawer interaction handler."
  [state]
  (fn [event handler]
    (events/deregister
     state :drawer event
     (if (= (name event) "click")
       (wrap-click state handler) handler))))

(def foundation
  {:will-mount
   (fn [state]
     (class/add state :root "mdc-temporary-drawer")
     #?(:clj state
        :cljs
        (let [foundation
              (js/mdc.drawer.MDCTemporaryDrawerFoundation.
               #js {:addClass #(add-class state %1)
                    :removeClass #(remove-class state %1)
                    :hasClass #(class/has? state :root %1)
                    :hasNecessaryDom #(has-necessary-dom? state)
                    :registerInteractionHandler #(events/register state :root %1 %2 {:passive true})
                    :deregisterInteractionHandler #(events/deregister state :root %1 %2 {:passive true})
                    :registerDrawerInteractionHandler (register-drawer-interaction-handler state)
                    :deregisterDrawerInteractionHandler (deregister-drawer-interaction-handler state)
                    :registerTransitionEndHandler #(events/register state :drawer :transitionend %1)
                    :deregisterTransitionEndHandler #(events/register state :drawer :transitionend %1)
                    :registerDocumentKeydownHandler #(.addEventListener js/document "keydown" %1)
                    :deregisterDocumentKeydownHandler #(.removeEventListener js/document "keydown" %1)
                    :getDrawerWidth #(drawer-width state)
                    :setTranslateX #(set-translate-x state %1)
                    :updateCssVariable #(update-css-variable state %1)
                    :getFocusableElements #(focusable-elements state)
                    :saveElementTabState #(util/save-element-tab-state %1)
                    :restoreElementTabState #(util/restore-element-tab-state %1)
                    :makeElementUntabbable #(util/make-element-untabbable %1)
                    :isDrawer #(drawer-element? state %1)
                    :isRtl #(rtl? state)})]
          (assoc state ::drawer foundation))))
   :did-remount
   (fn [old-state new-state]
     (cond
       (should-open? old-state new-state)
       (.open (::drawer new-state))
       (should-close? old-state new-state)
       (.close (::drawer new-state)))
     new-state)
   :did-mount
   (fn [state]
     (.init (::drawer state))
     (when (open? state)
       (.open (::drawer state)))
     state)
   :will-unmount
   (fn [state]
     (.destroy (::drawer state))
     state)})

(rum/defcs drawer < (class/mixin :root) foundation
  [state {:keys [class content header spacer]}]
  [:aside
   {:class
    (cond-> (class/get state :root)
      class (conj class))
    :ref "root"}
   [:nav.mdc-temporary-drawer__drawer
    {:ref "drawer"}
    (when spacer
      [:div.mdc-temporary-drawer__toolbar-spacer
       {:ref "spacer"} spacer])
    (when header
      [:header.mdc-temporary-drawer__header
       {:ref "header"}
       [:div.mdc-temporary-drawer__header-content
        {:ref "header-content"}
        header]])
    [:nav.mdc-temporary-drawer__content
     {:ref "content"} content]]])
