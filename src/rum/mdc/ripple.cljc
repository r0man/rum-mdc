(ns rum.mdc.ripple
  (:require #?(:cljs [cljsjs.material-components :as mdc])
            [rum.core :as rum]
            [rum.mdc.classes :as class]
            [rum.mdc.events :as events]))

(defn css-vars-supported?
  "Returns true if CSS variables are supported, otherwise false."
  []
  (true? #?(:cljs (some-> js/window .-CSS (.supports "(--color: red)")))))

(def unbounded-class
  #?(:cljs (aget js/mdc.ripple.MDCRippleFoundation.cssClasses "UNBOUNDED")))

(defn- bounding-rect [state]
  (when-let [element (rum/ref-node state :root)]
    (.getBoundingClientRect element)))

(defn- update-css-variable [state variable value]
  (when-let [element (rum/ref-node state :root)]
    (.setProperty (.-style element) variable value)))

(defn- window-page-offset []
  #?(:cljs #js {:x (.-pageXOffset js/window)
                :y (.-pageYOffset js/window)}))

(defn foundation
  "Return the Material Design ripple mixin."
  [component & [{:keys [unbounded?]}]]
  {:will-mount
   (fn [state]
     #?(:clj state
        :cljs
        (do (if unbounded?
              (class/add state :root unbounded-class)
              (class/remove state :root unbounded-class))
            (->> (js/mdc.ripple.MDCRippleFoundation.
                  #js {:browserSupportsCssVars #(css-vars-supported?)
                       :isUnbounded #(true? unbounded?)
                       :isSurfaceActive (constantly true)
                       :addClass #(class/add state :root %1)
                       :removeClass #(class/add state :root %1)
                       :registerInteractionHandler #(events/register state :root %1 %2)
                       :deregisterInteractionHandler #(events/deregister state :root %1 %2)
                       :registerResizeHandler #(.addEventListener js/window "resize" %1)
                       :deregisterResizeHandler #(.removeEventListener js/window "resize" %1)
                       :updateCssVariable #(update-css-variable state %1 %2)
                       :computeBoundingRect #(bounding-rect state)
                       :getWindowPageOffset #(window-page-offset)})
                 (assoc state ::ripple)))))
   :did-mount
   (fn [state]
     (.init (::ripple state))
     state)
   :will-unmount
   (fn [state]
     (.destroy (::ripple state))
     state)})
