package programming.in.scala.chapter_26.variable.args.pattern

/**
  * Created by denis.shuvalov on 08/05/2018.
  */
object Domain {
  // The injection method (optional)
  def apply(parts: String*): String = parts.reverse.mkString(".")

  // The extraction method (mandatory)
  // The result type of an unapplySeq must conform to Option[Seq[T]], where the element type T is arbitrary.
  def unapplySeq(whole: String): Option[Seq[String]] =
    Some(whole.split("\\.").reverse)

  //The Domain object defines an unapplySeq method that first splits the string into parts separated by periods.
  //This is done using Java's split method on strings, which takes a regular expression as its argument. The result of
  //split is an array of substrings. The result of unapplySeq is then that array with all elements reversed and wrapped in a Some.

  //Seq is an important class in Scala's collection hierarchy. It's a common superclass of several classes describing
  //different kinds of sequences: Lists, Arrays, WrappedString, and several others
}
