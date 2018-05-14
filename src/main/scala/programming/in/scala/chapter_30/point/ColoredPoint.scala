package programming.in.scala.chapter_30.point

/**
  * Created by denis.shuvalov on 10/05/2018.
  */
class ColoredPoint(x: Int, y: Int, val color: Color.Value)
  //extends Point(x, y) Problem: equals not symmetric -> (p equals cp) true but (cp equals p) false
  extends StrictPoint(x, y)
{
  /*
  Note that in this case, class ColoredPoint need not override hashCode. Because the new definition of equals on
  ColoredPoint is stricter than the overridden definition in Point (meaning it equates fewer pairs of objects), the
  contract for hashCode stays valid. If two colored points are equal, they must have the same coordinates, so their
  hash codes are guaranteed to be equal as well.
   */

  override def equals(other: Any) = other match {
    case that: ColoredPoint => (this.color == that.color) && super.equals(that)
    case _ => false
  }
}
