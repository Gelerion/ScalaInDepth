package programming.in.scala.chapter_26.email.extractor

/**
  * Created by denis.shuvalov on 08/05/2018.
  *
  * It's also possible that an extractor pattern does not bind any variables. In that case the corresponding unapply
  * method returns a boolean—true for success and false for failure.
  */
object UpperCase {

  //This time, the extractor only defines an unapply, but not an apply. It would make no sense to define an apply, as there's nothing to construct.

  def unapply(s: String): Boolean = s.toUpperCase == s
}
