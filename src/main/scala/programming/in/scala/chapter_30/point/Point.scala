package programming.in.scala.chapter_30.point

/**
  * Created by denis.shuvalov on 10/05/2018.
  */
//vals not vars!
class Point(val x: Int, val y: Int) {
  // the ## method is a shorthand for computing hash codes that works for primitive values, reference types, and null.
  override def hashCode(): Int = (x, y).##

  override def equals(other: scala.Any): Boolean = other match {
    case that: Point => this.x == that.x && this.y == that.y
    case _ => false
  }
}
