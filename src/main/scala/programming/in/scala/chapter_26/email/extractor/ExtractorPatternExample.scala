package programming.in.scala.chapter_26.email.extractor

/**
  * Created by denis.shuvalov on 08/05/2018.
  */
object ExtractorPatternExample extends App {

  println(userTwiceUpper("DIDI@hotmail.com")) //match: DI in domain hotmail.com
  println(userTwiceUpper("DIDO@hotmail.com")) //no match
  println(userTwiceUpper("didi@hotmail.com")) //no match

  //The first pattern of this function matches strings that are email addresses whose user part consists
  //of two occurrences of the same string in uppercase letters
  def userTwiceUpper(s: String) = s match {
    case Email(Twice(x @ UpperCase()), domain) => "match: " + x + " in domain " + domain
    case _ => "no match"
  }

  /*
  Note that UpperCase in function userTwiceUpper takes an empty parameter list. This cannot be omitted as otherwise the
  match would test for equality with the object UpperCase! Note also that, even though UpperCase() itself does not bind
  any variables, it is still possible to associate a variable with the whole pattern matched by it. To do this, you use
  the standard scheme of variable binding: the form x @ UpperCase() associates the variable x
  with the pattern matched by UpperCase(). For instance, in the first userTwiceUpper invocation above, x was bound to "DI",
  because that was the value against which the UpperCase() pattern was matched
   */
}
