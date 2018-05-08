package programming.in.scala.chapter_25.rna

/**
  * Created by denis.shuvalov on 03/05/2018.
  */
abstract class Base
case object A extends Base
case object T extends Base
case object G extends Base
case object U extends Base

object Base {
  /*
  The toInt function is implemented as a Map from Base values to integers. The reverse function, fromInt, is implemented
  as an array. This makes use of the fact that both maps and arrays are functions because they inherit from the Function1 trait
   */
  val fromInt: Int => Base = Array(A, T, G, U)
  val toInt: Base => Int = Map(A -> 0, T -> 1, G -> 2, U -> 3)
}