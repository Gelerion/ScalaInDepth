package programming.in.scala.chapter_20.overridning

/**
  * Created by denis.shuvalov on 10/04/2018.
  *
  * In other words, an abstract val constrains its legal implementation: Any implementation must be a val definition;
  * it may not be a var or a def. Abstract method declarations, on the other hand, may be implemented by both concrete
  * method definitions and concrete val definitions.
  */
class OverridingAbstractVals {

}

abstract class Fruit {
  val v: String // v for value
  def m: String //m for method
}

abstract class Apple extends Fruit {
  val v: String
  val m: String //OK to override a def with val
}

abstract class BadApple extends Fruit {
  //def v: String // ERROR: cannot override a `val' with a `def'
  def m: String
}

