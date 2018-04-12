package programming.in.scala.chapter_20.lazyvals

/**
  * Created by denis.shuvalov on 10/04/2018.
  */
object LazyValInit extends App {
  //You can use pre-initialized fields to simulate precisely the initialization behavior of class constructor arguments.
  //Sometimes, however, you might prefer to let the system itself sort out how things should be initialized. This
  //can be achieved by making your val definitions lazy. If you prefix a val definition with a lazy modifier, the
  //initializing expression on the right-hand side will only be evaluated the first time the val is used.

  val demo = Demo
  println(demo)
  println(demo.x)
  // initializing x
  // programming.in.scala.chapter_20.lazyvals.Demo$@23223dd8
  // done

  println("--------------------------------")

  //As you can see, the moment you use Demo, its x field becomes initialized. The initialization of x forms part
  //of the initialization of Demo. The situation changes, however, if you define the x field to be lazy:
  val lazyDemo = LazyDemo
  println(lazyDemo)
  println(lazyDemo.x)
  // programming.in.scala.chapter_20.lazyvals.LazyDemo$@4ec6a292
  // initializing x
  // done

  //Now, initializing Demo does not involve initializing x. The initialization of x will be deferred until the first
  //time x is used. This is similar to the situation where x is defined as a parameterless method, using a def.
  //However, unlike a def, a lazy val is never evaluated more than once. In fact, after the first evaluation of a
  //lazy val the result of the evaluation is stored, to be reused when the same val is used subsequently.

  println("--------------------------------")

  //With these changes, there's nothing that remains to be done when LazyRationalTrait is initialized; all initialization
  //code is now part of the right-hand side of a lazy val. Thus, it is safe to initialize the abstract fields of
  //LazyRationalTrait after the class is defined
  val x = 2
  val rat = new LazyRationalTrait {
    val numerArg: Int = 1 * x
    val denomArg: Int = 2 * x
  }

  println(rat)

  //No pre-initialization is needed. It's instructive to trace the sequence of initializations
  //that lead to the string 1/2 to be printed in the code above:
  /*
     1. A fresh instance of LazyRationalTrait gets created and the initialization code of LazyRationalTrait is run.
        This initialization code is empty; none of the fields of LazyRationalTrait is initialized yet.
     2. Next, the primary constructor of the anonymous subclass defined by the new expression is executed.
        This involves the initialization of numerArg with 2 and denomArg with 4.
     3. Next, the toString method is invoked on the constructed object by the interpreter,
        so that the resulting value can be printed.
     4. Next, the numer field is accessed for the first time by the toString method in trait LazyRationalTrait,
        so its initializer is evaluated.
     5. The initializer of numer accesses the private field, g, so g is evaluated next. This evaluation accesses
        numerArg and denomArg, which were defined in Step 2.
     6. Next, the toString method accesses the value of denom, which causes denom's evaluation. The evaluation of denom
        accesses the values of denomArg and g. The initializer of the g field is not re-evaluated, because it was already
        evaluated in Step 5.
     7. Finally, the result string "1/2" is constructed and printed.
   */

  //!!!
  //This shows an important property of lazy vals: The textual order of their definitions does not matter because values
  //get initialized on demand. Thus, lazy vals can free you as a programmer from having to think hard how to arrange val
  //definitions to ensure that everything is defined when it is needed.
  //However, this advantage holds only as long as the initialization of lazy vals neither produces side effects nor depends on them.
  //In the presence of side effects, initialization order starts to matter. And then it can be quite difficult to trace
  //in what order initialization code is run, as the previous example has demonstrated. So lazy vals are an ideal
  //complement to functional objects, where the order of initializations does not matter, as long as everything gets
  //initialized eventually. They are less well suited for code that's predominantly imperative.
}

object Demo {
  val x = { println("initializing x"); "done" }
}

object LazyDemo {
  lazy val x = { println("initializing x"); "done" }
}

trait LazyRationalTrait {
  val numerArg: Int
  val denomArg: Int

  lazy val numer = numerArg / g
  lazy val denom = denomArg / g

  override def toString = numer + "/" + denom

  private lazy val g = {
    require(denomArg != 0)
    gcd(numerArg, denomArg)
  }

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

}