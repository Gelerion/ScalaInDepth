package programming.in.scala.chapter_30.point

/**
  * Created by denis.shuvalov on 10/05/2018.
  */
class StrictPoint(val x: Int, val y: Int) {
  // the ## method is a shorthand for computing hash codes that works for primitive values, reference types, and null.
  override def hashCode(): Int = (x, y).##

  // A technically valid, but unsatisfying, equals method
  /*
   scala> val pAnon = new Point(1, 1) { override val y = 2 }
   pAnon: Point = $anon$1@5428bd62
   Is pAnon equal to p? The answer is no because the java.lang.Class objects associated with p and pAnon are different.
   For p it is Point, whereas for pAnon it is an anonymous subclass of Point. But clearly, pAnon is just another point
   at coordinates (1, 2). It does not seem reasonable to treat it as being different from p.
   */

//  override def equals(other: scala.Any): Boolean = other match {
//    case that: Point =>
//      this.x == that.x && this.y == that.y && this.getClass == that.getClass
//    case _ => false
//  }

  /*
  So it seems we are stuck. Is there a sane way to redefine equality on several levels of the class hierarchy while
  keeping its contract? In fact, there is such a way, but it requires one more method to redefine together with equals
  and hashCode. The idea is that as soon as a class redefines equals (and hashCode), it should also explicitly state
  that objects of this class are never equal to objects of some superclass that implement a different equality method.
  This is achieved by adding a method canEqual to every class that redefines equals. Here's the method's signature:

    def canEqual(other: Any): Boolean
   */

  override def equals(other: Any) = other match {
    case that: StrictPoint =>
      (that canEqual this) && (this.x == that.x) && (this.y == that.y)
    case _ => false
  }

  def canEqual(other: Any) = other.isInstanceOf[StrictPoint]
}
