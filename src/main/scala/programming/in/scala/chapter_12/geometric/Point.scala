package programming.in.scala.chapter_12.geometric

/**
  * Created by denis.shuvalov on 14/03/2018.
  */
class Point(val x: Int, val y: Int)

trait Rectangular {
  def topLeft: Point
  def bottomRight: Point

  def left = topLeft.x
  def right = bottomRight.x
  def width = right - left
  // and many more geometric methods...
}

//Class Component can mix in this trait to get all the geometric methods provided by Rectangular:
abstract class Component extends Rectangular {
  // other methods
}

//Similarly, Rectangle itself can mix in the trait:
class Rectangle(val topLeft: Point, val bottomRight: Point) extends Rectangular

object Main {
  val rect = new Rectangle(new Point(1, 1), new Point(10, 10))

//  scala> rect.left
//  res2: Int = 1
//
//  scala> rect.right
//  res3: Int = 10
//
//  scala> rect.width
//  res4: Int = 9
}