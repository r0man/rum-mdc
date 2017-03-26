(ns rum.mdc.grid-list
  (:require #?(:cljs [cljsjs.material-components :as mdc])
            [rum.core :as rum]))

(def foundation
  {:did-mount
   #?(:clj identity
      :cljs (fn [state]
              (->> (rum/ref-node state "root")
                   (js/mdc.gridList.MDCGridList.attachTo)
                   (assoc state ::grid-list))))
   :will-unmount
   (fn [state]
     (.destroy (::grid-list state))
     (dissoc state ::grid-list))})

(rum/defcs grid-list < foundation
  [state children {:keys [class dir] :as opts}]
  [:div.mdc-grid-list
   {:class class
    :dir dir
    :ref "root"}
   children])
