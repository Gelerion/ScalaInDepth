package programming.in.scala.chapter_10

import Element.elem
/**
  * Created by denis.shuvalov on 12/03/2018.
  *
  * Our first task is to define type Element, which represents layout elements. Since elements are two dimensional
  * rectangles of characters, it makes sense to include a member, contents, that refers to the contents of a layout
  * element. The contents can be represented as an array of strings, where each string represents a line. Hence,
  * the type of the result returned by contents will be Array[String]
  */
abstract class Element {

  def contents: Array[String] //no implementation, abstract member

  //Another bit of terminology distinguishes between declarations and definitions.
  //Class Element declares the abstract method contents, but currently defines no concrete methods.

  //defining parameterless methods
  def height: Int = contents.length //method returns the number of lines in content
  def width: Int = if(height == 0) 0 else contents(0).length //returns the length of the first line

  //empty-paren methods:
  //methods defined with empty parentheses, such as def height(): Int

  //-----------------------------------------------------------------
  //The recommended convention is to use a parameterless method whenever there are no parameters and the method
  //accesses mutable state only by reading fields of the containing object (in particular, it does not change mutable state).
  //This convention supports the uniform access principle, which says that client code should not be affected by a
  //decision to implement an attribute as a field or method.
  //-----------------------------------------------------------------
  //val height ...
  //val width ...
  //The two pairs of definitions are completely equivalent from a client's point of view. The only difference is that
  //field accesses might be slightly faster than method invocations because the field values are pre-computed when the
  //class is initialized, instead of being computed on each method call. On the other hand, the fields require extra
  //memory space in each Element object. So it depends on the usage profile of a class whether an attribute is better
  //represented as a field or method, and that usage profile might change over time. The point is that clients of the
  //Element class should not be affected when its internal implementation changes.
  //-----------------------------------------------------------------


  //The ++ operation concatenates two arrays. Arrays in Scala are represented as Java arrays, but support many more methods
  //Specifically, arrays in Scala can be converted to instances of a class scala.Seq, which represents sequence-like
  //structures and contains a number of methods for accessing and transforming sequences.
  def aboveV1(that: Element): Element = new ArrayElement(this.contents ++ that.contents)
  //In fact, the code shown previously is not quite sufficient because it does not let you put elements of different widths on top of each other.

  def besideImperative(that: Element): Element = {
    val contents = new Array[String](this.contents.length)
    for (i <- 0 until this.contents.length)
      contents(i) = this.contents(i) + that.contents(i)
    new ArrayElement(contents)
  }

  //two arrays, this.contents and that.contents, are transformed into an array of pairs (as Tuple2s are called) using the zip operator
  def besideV1(that: Element): Element = {
    new ArrayElement(
      for (
        (line1, line2) <- this.contents zip that.contents
      ) yield line1 + line2
    )
  }
  /*
     Array(1, 2, 3) zip Array("a", "b")
    will evaluate to:
     Array((1, "a"), (2, "b"))
   */
  //---------------------------------

  def above(that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width
    elem(this1.contents ++ that1.contents)
  }

  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    elem(
      for ((line1, line2) <- this1.contents zip that1.contents)
        yield line1 + line2)
  }

  def widen(w: Int): Element =
    if (w <= width) this
    else {
      val left = elem(' ', (w - width) / 2, height)
      val right = elem(' ', w - width - left.width, height)
      left beside this beside right
    }

  def heighten(h: Int): Element =
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      val bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }

  override def toString = contents mkString "\n"
}

//DEFINING A FACTORY OBJECT
//You now have a hierarchy of classes for layout elements. This hierarchy could be presented to your clients "as is,"
//but you might also choose to hide the hierarchy behind a factory object.
//A factory object contains methods that construct other objects. Clients would then use these factory methods to construct
//objects, rather than constructing the objects directly with new.

object Element {
  //In addition, given the factory methods, the subclasses, ArrayElement, LineElement, and UniformElement, could
  //now be private because they no longer need to be accessed directly by clients.
  def elem(contents: Array[String]): Element = new ArrayElement(contents)

  def elem(chr: Char, width: Int, height: Int): Element = new UniformElement(chr, width, height)

  def elem(line: String): Element = new LineElement(line)
}
