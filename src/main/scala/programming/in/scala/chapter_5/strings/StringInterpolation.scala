package programming.in.scala.chapter_5.strings

/**
  * Created by denis.shuvalov on 05/03/2018.
  */
object StringInterpolation extends App {

  //The expression, s"Hello, $name!" is a processed string literal. Because the letter s immediately precedes
  //the open quote, Scala will use the s string interpolator to process the literal. The s interpolator will
  //evaluate each embedded expression, invoke toString on each result, and replace the embedded expressions in the
  //literal with those results.

  val name = "reader"
  println(s"Hello, $name!")

  //Scala provides two other string interpolators by default: raw and f.
  println(raw"No\\\\escape!") // prints: No\\\\escape!

  //The f string interpolator allows you to attach printf-style formatting instructions to embedded expressions.
  println(f"${math.Pi}%.5f") //3.14159

}
