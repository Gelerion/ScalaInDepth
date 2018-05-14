package programming.in.scala.chapter_30.point

/**
  * Created by denis.shuvalov on 10/05/2018.
  */
class StrictColoredPoint(x: Int, y: Int, val color: Color.Value) extends StrictPoint(x, y) {

  override def hashCode = (super.hashCode, color).##

  override def equals(other: Any) = other match {
    case that: StrictColoredPoint =>
      (that canEqual this) && super.equals(that) && this.color == that.color
    case _ => false
  }

  override def canEqual(other: Any) = other.isInstanceOf[StrictColoredPoint]


}
