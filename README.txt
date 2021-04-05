After reading the Massalin paper, I wondered about superoptimization over higher level languages.

The idea of superoptimization only really makes sense for incredibly small programs though, and I noticed a lot of the cool examples from Massalin came from bit level trickiness.

Eventually, I found a project that investigated superoptimization over the JVM bytecode instructions.

Here is a link to their paper and implementation: 

 https://doi.org/10.1002/spe.2240
 https://github.com/twhume/superoptimiser

I got it up and running and played around with their built-in methods, then implemented a couple of my own (Mult7, Numsig (inverse signum)) and observed the optimized versions.

To implement new methods to be optimized, the following info is needed:

  class-name - name of the class that will be generated for the method
  method-name - name of the method to be optimized
  method-signature - method signature, see https://www.cs.miami.edu/home/burt/reference/java/language_vm_specification.pdf
  eq-tests-filter - tests that the optimized method must pass to ensure equivalent behavior
    examples: (fn one-to-seven? [i]  (= 7 (invoke-method i method-name 1)))
              (fn zero-untouched? [i]  (= 0 (invoke-method i method-name 0)))

            

Here's some samples of runs of the superoptimizer:

Identity

Ians-MacBook-Pro-4:SuperOptimiser Ian$ lein run -m Drivers.Identity
Apr 04, 2021 9:07:20 AM jdk.internal.reflect.NativeMethodAccessorImpl invoke0
INFO: PASS IdentityTest.identity {:seq-num 0, :vars 1, :length 2, :code ((:iload_0) (:ireturn)), :jumps {}}
"Elapsed time: 128.928389 msecs"

Mult7

Ians-MacBook-Pro-4:SuperOptimiser Ian$ lein run -m Drivers.Mult7
Apr 04, 2021 10:50:41 AM jdk.internal.reflect.NativeMethodAccessorImpl invoke0
INFO: PASS Mult7Test.Mult7 {:seq-num 3242, :vars 1, :length 4, :code ((:iload_0) (:bipush 7) (:imul) (:ireturn)), :jumps {}}
"Elapsed time: 6377.110742 msecs"

Numsig takes a very long time because its optimized version is 7 instructions long
As of the time of writing, the optimizer for numsig has been running for 6 hours and has generated 115M programs
I eventually terminated the optimizer at ~700M solutions generated
