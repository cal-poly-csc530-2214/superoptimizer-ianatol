(ns Drivers.Mult7
        (:use [clojure.tools.logging :only (info)]))
(use 'Main.Superoptimise)

; Superoptimises multiplication by a constant 7

(let [class-name "Mult7Test"
      method-name "Mult7"
      method-signature "(I)I"
      eq-tests-filter [
                       (fn one-to-seven? [i]  (= 7 (invoke-method i method-name 1)))
                       (fn two-to-fourteen? [i] (= 14 (invoke-method i method-name 2)))
                       (fn zero-untouched? [i]  (= 0 (invoke-method i method-name 0)))
                       (fn minus-one-to-minus-seven? [i]  (= -7 (invoke-method i method-name -1)))
                       (fn large-positive? [i]  (= 6223 (invoke-method i method-name 889)))
                       (fn large-negative? [i]  (= -69111 (invoke-method i method-name -9873)))
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