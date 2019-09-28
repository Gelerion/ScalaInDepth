package programming.in.scala.advanced.shapeless.chapter_2.adt

object AdtTypes {

  def main(args: Array[String]): Unit = {
    val rect: Shape = Rectangle(3.0, 4.0)
    val circ: Shape = Circle(1.0)

    //The beauty of ADTs is that they are completely type safe.
    //The compiler has complete knowledge of the algebras we define, so it can help us
    //write complete, correctly typed methods involving our types
    area(rect)
    area(circ)
  }

  def area(shape: Shape): Double =
    shape match {
      case Rectangle(w, h) => w * h
      case Circle(r)       => math.Pi * r * r
    }
}

sealed trait Shape
final case class Rectangle(width: Double, height: Double) extends Shape
final case class Circle(radius: Double) extends Shape
