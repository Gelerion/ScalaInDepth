package programming.in.scala.chapter_15.pattern.matching

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
class TypedPatterns {

  def generalSize(x: Any) = x match {
    case s: String => s.length
    case m: Map[_,_] => m.size
    case _ => -1
  }

  //same as
//  if (x.isInstanceOf[String]) {
//    val s = x.asInstanceOf[String]
//    s.length
//  } else ...

  //Type erasure
//  def isIntIntMap(x: Any) = x match {
//    case m: Map[Int, Int] => true
//    case _ => false
//  }
  /*
  <console>:9: warning: non-variable type argument Int in type
  pattern scala.collection.immutable.Map[Int,Int] (the
  underlying of Map[Int,Int]) is unchecked since it is
  eliminated by erasure
           case m: Map[Int, Int] => true
   */

  //Scala uses the erasure model of generics, just like Java does. This means that no information about type arguments
  //is maintained at runtime. Consequently, there is no way to determine at runtime whether a given Map object has
  //been created with two Int arguments, rather than with arguments of different types. All the system can do is
  //determine that a value is a Map of some arbitrary type parameters.

  //The only exception to the erasure rule is arrays, because they are handled specially in Java as well as in Scala.
  def isStringArray(x: Any) = x match {
    case a: Array[String] => "yes"
    case _ => "no"
  }
}
