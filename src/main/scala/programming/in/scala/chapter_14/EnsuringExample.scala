package programming.in.scala.chapter_14

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
class EnsuringExample {

  //In this example, the predicate is "w <= _.width". The underscore is a placeholder for the one argument
  //passed to the predicate, the Element result of the widen method. If the width passed as w to widen is
  //less than or equal to the width of the result Element, the predicate will result in true, and ensuring
  //will result in the Element on which it was invoked. Because this is the last expression of the widen
  //method, widen itself will then result in the Element

/*  private def widen(w: Int): Element =
    if (w <= width)
      this
    else {
      val left = elem(' ', (w - width) / 2, height)
      var right = elem(' ', w - width - left.width, height)
      left beside this beside right
    } ensuring (w <= _.width)*/

  //Assertions can be enabled and disabled using the JVM's -ea and -da command-line flags.
}
