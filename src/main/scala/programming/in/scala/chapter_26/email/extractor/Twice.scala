package programming.in.scala.chapter_26.email.extractor

/**
  * Created by denis.shuvalov on 08/05/2018.
  */
object Twice {
  def apply(s: String): String = s + s

  //option with one argument not tuple
  def unapply(s: String): Option[String] = {
    val length = s.length / 2
    val half = s.substring(0, length)
    if (half == s.substring(length)) Some(half) else None
  }
}
