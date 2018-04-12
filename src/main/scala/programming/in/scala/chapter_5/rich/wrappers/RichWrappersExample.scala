package programming.in.scala.chapter_5.rich.wrappers

/**
  * Created by denis.shuvalov on 05/03/2018.
  *
  * You can invoke many more methods on Scala's basic types than were described in the previous sections.
  * These methods are available via implicit conversions
  */
object RichWrappersExample {

  val res1: Int = 0 max 5 //RichInt
  val res2: Int = 0 min 5 //RichInt
  -2.7 abs

}
