package programming.in.scala.chapter_26.variable.args.pattern

/**
  * Created by denis.shuvalov on 08/05/2018.
  */
object ExpandedEmail {

  def unapplySeq(email: String): Option[(String, Seq[String])] = {
    val parts = email split "@"
    if (parts.length == 2)
      Some(parts(0), parts(1).split("\\.").reverse)
    else
      None
  }
}
