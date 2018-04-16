package programming.in.scala.chapter_21

/**
  * Created by denis.shuvalov on 15/04/2018.
  *
  * Implicit classes were added in Scala 2.10 to make it easier to write rich wrapper classes. An implicit class is a
  * class that is preceded by the implicit keyword. For any such class, the compiler generates an implicit conversion
  * from the class's constructor parameter to the class itself. Such a conversion is just what you need if you plan
  * to use the class for the rich wrappers pattern.
  */
class ImplicitClasses {


  //If you use this class very frequently, you might want to use the rich wrappers pattern so you can more easily construct it.
  //Here's one way to do so.
  implicit class RectangleMaker(width: Int) {
    def x(height: Int) = Rectangle(width, height)
  }

  //The above definition defines a RectangleMaker class in the usual manner. In addition, it causes the following conversion to be automatically generated:
  //
  //  Automatically generated
  //  implicit def RectangleMaker(width: Int) =
  //    new RectangleMaker(width)

  val myRectangle = 3 x 4 //Rectangle(3,4)
  /*
  This is how it works: Since type Int has no method named x, the compiler will look for an implicit conversion from
  Int to something that does. It will find the generated RectangleMaker conversion, and RectangleMaker does have a method
  named x. The compiler inserts a call to this conversion, after which the call to x type checks and does what is desired.

As a warning to the adventurous, it might be tempting to think that any class can have implicit put in front of it.
It's not so. An implicit class cannot be a case class, and its constructor must have exactly one parameter. Also,
an implicit class must be located within some other object, class, or trait. In practice, so long as you use implicit
classes as rich wrappers to add a few methods onto an existing class, these restrictions should not matter.
   */
}

//For example, suppose you have a class named Rectangle for representing the width and height of a rectangle on the screen:
case class Rectangle(width: Int, height: Int)


