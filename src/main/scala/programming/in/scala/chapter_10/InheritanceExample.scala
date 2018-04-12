package programming.in.scala.chapter_10

import Element.elem
/**
  * Created by denis.shuvalov on 12/03/2018.
  */
object InheritanceExample extends App {

  val e1: Element = new ArrayElement(Array("hello", "world"))
  println(e1)
  val ae: ArrayElement = new LineElement("hello")
  val e2: Element = ae
  val e3: Element = new UniformElement('x', 2, 3)

  println("-----------------------------------------")
  val column1 = elem("hello") above elem("***")
  val column2 = elem("***") above elem("world")
  println(column1 beside column2)



}
