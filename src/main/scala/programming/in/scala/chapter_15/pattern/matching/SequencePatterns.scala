package programming.in.scala.chapter_15.pattern.matching

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
class SequencePatterns {
  //You can match against sequence types, like List or Array, just like you match against case classes.
  //Use the same syntax, but now you can specify any number of elements within the pattern.

  //pattern that checks for a three-element list starting with zero.
//  expr match {
//    case List(0, _, _) => println("found it")
//    case _ =>
//  }

  //If you want to match against a sequence without specifying how long it can be, you can specify _* as
  //the last element of the pattern.
//  expr match {
//    case List(0, _*) => println("found it")
//    case _ =>
//  }
}
