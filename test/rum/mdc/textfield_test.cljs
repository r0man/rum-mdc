(ns rum.mdc.textfield-test
  (:require [devcards.core :refer-macros [defcard]]
            [sablono.core :refer [html]]
            [rum.mdc.textfield :refer [textfield]]))

(defcard single-line
  (html (textfield {:label "Hint text"})))

(defcard disabled
  (html (textfield {:label "Hint text" :disabled? true})))

(defcard pre-filled
  (html (textfield {:label "Hint text" :value "Pre-filled value"})))

(defcard help-text
  (html
   [:div
    (textfield
     {:label "Hint text"
      :help "help-text"})
    [:p.mdc-textfield-helptext
     {:aria-hidden true
      :id "help-text"}
     "This will be displayed on your public profile"]]))

(defcard persistent-help-text
  (html
   [:div
    (textfield
     {:help "persistent-help-text"
      :label "Email address"
      :type "email"})
    [:p.mdc-textfield-helptext.mdc-textfield-helptext--persistent
     {:aria-hidden true
      :id "persistent-help-text"}
     "We will " [:em "never"] " share your email address with third parties"]]))

(defcard required
  (html (textfield
         {:label "Hint text"
          :required? true
          :min-length 8})))

(defcard validation
  (html (textfield
         {:label "Hint text"
          :required? true})))
