(ns Drivers.Mult100
        (:use [clojure.tools.logging :only (info)]))
(use 'Main.Superoptimise)

; Superoptimises multiplication by a constant 7

(let [class-name "Mult100Test"
      method-name "Mult100"
      method-signature "(I)I"
      eq-tests-filter [
                       (fn one-to-hundred? [i]  (= 100 (invoke-method i method-name 1)))
                       (fn two-to-two-hundred? [i] (= 200 (invoke-method i method-name 2)))
                       (fn zero-untouched? [i]  (= 0 (invoke-method i method-name 0)))
                       (fn minus-one-to-minus-hundred? [i]  (= -100 (invoke-method i method-name -1)))
                       (fn large-positive? [i]  (= 88900 (invoke-method i method-name 889)))
                       (fn large-negative? [i]  (= -987300 (invoke-method i method-name -9873)))
                       ]]

	(defn -main []
	  (time 
	    (doall
	      (superoptimise-pmap 4 class-name method-name method-signature eq-tests-filter))))

    (defn run-slice
      "Superoptimises a small slice of the overall search space"
      [num-nodes cur-node]
      (do
        (info "starting node " cur-node "/" num-nodes)
	      (time
	          (dorun
	            (superoptimise-slice 6 class-name method-name method-signature eq-tests-filter num-nodes cur-node)))
        (info "finishing node " cur-node "/" num-nodes))))