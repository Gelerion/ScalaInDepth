package programming.in.scala.chapter_15.pattern.matching

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
class ConstructorPatterns {
  //Constructors are where pattern matching becomes really powerful. A constructor pattern looks like
  //"BinOp("+", e, Number(0))". It consists of a name (BinOp) and then a number of patterns within
  //parentheses: "+", e, and Number(0). Assuming the name designates a case class, such a pattern means to
  //first check that the object is a member of the named case class, and then to check that the constructor
  //parameters of the object match the extra patterns supplied.

  //These extra patterns mean that Scala patterns support deep matches. Such patterns not only check the top-level
  //object supplied, but also the contents of the object against further patterns. Since the extra patterns can
  //themselves be constructor patterns, you can use them to check arbitrarily deep into an object.

//  expr match {
//    case BinOp("+", e, Number(0)) => println("a deep match")
//    case _ =>
//  }
}
