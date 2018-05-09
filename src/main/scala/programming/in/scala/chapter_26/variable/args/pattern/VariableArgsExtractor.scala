package programming.in.scala.chapter_26.variable.args.pattern

import programming.in.scala.chapter_26.email.extractor.Email

/**
  * Created by denis.shuvalov on 08/05/2018.
  */
object VariableArgsExtractor extends App {

  domMatches("java.sun.com")

  /*
   scala> isTomInDotCom("tom@sun.com")
  res3: Boolean = true

   scala> isTomInDotCom("peter@sun.com")
   res4: Boolean = false

   scala> isTomInDotCom("tom@acm.org")
   res5: Boolean = false
   */

  val s = "tom@support.epfl.ch"

  val ExpandedEmail(name, topdom, subdoms @ _*) = s
  // name: String = tom
  // topdom: String = ch
  // subdoms: Seq[String] = WrappedArray(epfl, support)

  def isTomInDotCom(s: String): Boolean = {
    s match {
      case Email("tom", Domain("com", _*)) => true
      case _ => false
    }
  }

  def domMatches(dom: String) = dom match {
    case Domain("org", "acm") => println("acm.org")
    case Domain("com", "sun", "java") => println("java.sun.com")
    case Domain("net", _*) => println("a .net domain")
    case _ => println("no match")
  }
}
