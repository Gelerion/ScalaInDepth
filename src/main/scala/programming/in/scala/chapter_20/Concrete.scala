package programming.in.scala.chapter_20

/**
  * Created by denis.shuvalov on 10/04/2018.
  */
class Concrete extends Abstract {
  //A concrete implementation of Abstract needs to fill in definitions for each of its abstract members.
  //The implementation gives a concrete meaning to the type name T by defining it as an alias of type String
  //type T in class Concrete, as a way to define a new name, or _alias_, for a type
  override type T = String
  override def transform(x: String) = x + x
  override val initial = "hi"
  override var current = initial
}
