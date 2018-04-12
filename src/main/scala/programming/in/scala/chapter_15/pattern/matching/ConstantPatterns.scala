package programming.in.scala.chapter_15.pattern.matching

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
class ConstantPatterns {

  def describe(x: Any) = x match {
    case 5 => "five"
    case true => "truth"
    case "hello" => "hi!"
    case Nil => "the empty list"
    case _ => "something else"
  }


}
