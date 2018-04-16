package programming.in.scala.chapter_21

/**
  * Created by denis.shuvalov on 15/04/2018.
  */
class SimulatingNewSyntax {
  //The other major use of implicit conversions is to simulate adding new syntax.
  //Recall that you can make a Map using syntax like this:
  Map(1 -> "one", 2 -> "two", 3 -> "three")
  //Predef.ArrowAssoc(1) -> "one"
  /*
    package scala
    object Predef {
      class ArrowAssoc[A](x: A) {
        def -> [B](y: B): Tuple2[A, B] = Tuple2(x, y)
      }
      implicit def any2ArrowAssoc[A](x: A): ArrowAssoc[A] =
        new ArrowAssoc(x)
      ...

      This "rich wrappers" pattern is common in libraries that provide syntax-like extensions to the language, so you
      should be ready to recognize the pattern when you see it. Whenever you see someone calling methods that appear
      not to exist in the receiver class, they are probably using implicits. Similarly, if you see a class named
      RichSomething (e.g., RichInt or RichBoolean), that class is likely adding syntax-like methods to type Something.
   */
}
