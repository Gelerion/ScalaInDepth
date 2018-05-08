package programming.in.scala.chapter_25.patrica.tree

/**
  * Created by denis.shuvalov on 06/05/2018.
  */
object PrefixMapTest extends App {

  val m = PrefixMap("abc" -> 0, "abd" -> 1, "al" -> 2, "all" -> 3, "xy" -> 4)

  private val maybeInt: Option[Int] = m.get("abc")
  println(maybeInt)
  //println(m withPrefix "a")

  //The other member in object PrefixMap is an implicit CanBuildFrom instance. It has the same purpose as the
  // CanBuildFrom definition in the last section: to make methods like map return the best possible type. For instance,
  // consider mapping a function over the key/value pairs of a PrefixMap. As long as that function produces pairs of strings
  // and some second type, the result collection will again be a PrefixMap. Here's an example:
  //
  //  scala> res0 map { case (k, v) => (k + "!", "x" * v) }
  //  res8: PrefixMap[String] = Map((hello!,xxxxx), (hi!,xx))
  private val pm2: PrefixMap[String] = m map { case (k, v) => (k + "!", "x" * v) }
  println(pm2)


}
