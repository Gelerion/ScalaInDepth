package programming.in.scala.advanced.shapeless.chapter_2.adt

object AdtTypesAlternativeEncoding {
  def main(args: Array[String]): Unit = {
    val rect2: Shape2 = Left((3.0, 4.0))
    val circ2: Shape2 = Right(1.0)

    area2(rect2)
    area2(circ2)

  }

  type Rectangle2 = (Double, Double)
  type Circle2    = Double
  type Shape2     = Either[Rectangle2, Circle2]

  def area2(shape: Shape2): Double =
    shape match {
      case Left((w, h)) => w * h
      case Right(r)     => math.Pi * r * r
    }
}
