package programming.in.scala.chapter_10

/**
  * Created by denis.shuvalov on 12/03/2018.
  *
  * You could define a new form of Element that has a given width and height, and is filled everywhere with a given character:
  */
class UniformElement(ch: Char,
                     override val width: Int,
                     override val height: Int
                    ) extends Element {

  private val line = ch.toString * width
  def contents: Array[String] = Array.fill(height)(line)
}
