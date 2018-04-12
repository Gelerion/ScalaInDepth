package programming.in.scala.chapter_10

/**
  * Created by denis.shuvalov on 12/03/2018.
  */
class LineElement(s: String) extends ArrayElement(Array(s)) {
  //Scala requires such a modifier for all members that override a concrete member in a parent class.
  //The modifier is optional if a member implements an abstract member with the same name.
  override def width: Int = s.length
  override def height: Int = 1
}
