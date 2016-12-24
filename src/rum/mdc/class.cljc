(ns rum.mdc.class
  (:refer-clojure :exclude [get remove])
  (:require [rum.core :as rum]))

(defn get [state type]
  (-> state ::classes deref type))

(defn add [state type class]
  (swap! (::classes state) update type conj class))

(defn remove [state type class]
  (swap! (::classes state) update type disj class))

(defn mixin [initial]
  (rum/local initial ::classes))
