package programming.in.scala.chapter_11

/**
  * Created by denis.shuvalov on 13/03/2018.
  *
  * As mentioned in Section 11.1, you can define your own value classes to augment the ones that are built in.
  * Like the built-in value classes, an instance of your value class will usually compile to Java bytecode that
  * does not use the wrapper class. In contexts where a wrapper is needed, such as with generic code, the value
  * will get boxed and unboxed automatically.
  * *
  * Only certain classes can be made into value classes. For a class to be a value class, it must have exactly one
  * parameter and it must have nothing inside it except defs. Furthermore, no other class can extend a value class,
  * and a value class cannot redefine equals or hashCode.
  * *
  * To define a value class, make it a subclass of AnyVal, and put val before the one parameter. Here is an example
  * value class:
  */
object ValueTypeExample {

  class Dollars(val amount: Int) extends AnyVal {
    override def toString() = "$" + amount
  }

}
