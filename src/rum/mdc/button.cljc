(ns rum.mdc.button
  (:require [rum.core :as rum]))

(defn- button-class
  "Return the CSS class for the button."
  [{:keys [class mdc]}]
  (let [{:keys [accent? compact? dense? disabled? primary? raised?]} mdc]
    (cond-> (if (string? class) #{class} class)
      accent? (conj "mdc-button--accent")
      compact? (conj "mdc-button--compact")
      dense? (conj "mdc-button--dense")
      disabled? (conj "mdc-button--disabled")
      primary? (conj "mdc-button--primary")
      raised? (conj "mdc-button--raised"))))

(rum/defc button < rum/static
  "Render a Material Design button."
  [content & [opts]]
  [:button.mdc-button
   ^:attrs (-> (dissoc opts :mdc)
               (assoc :class (button-class opts)))
   content])
