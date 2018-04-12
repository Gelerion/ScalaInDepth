package programming.in.scala.chapter_15.sequences

/**
  * Created by denis.shuvalov on 25/03/2018.
  */
class CaseSequencesAsPartialFunctions {

  //A sequence of cases (i.e., alternatives) in curly braces can be used anywhere a function literal can be used.
  //Essentially, a case sequence is a function literal, only more general. Instead of having a single entry point
  //and list of parameters, a case sequence has multiple entry points, each with their own list of parameters.
  //Each case is an entry point to the function, and the parameters are specified with the pattern. The body of
  //each entry point is the right-hand side of the case.

  val withDefault: Option[Int] => Int = {
    case Some(x) => x
    case None => 0
  }
  //withDefault(Some(10)) -> 10
  //withDefault(None) -> 0

  //This facility is quite useful for the Akka actors library, because it allows its receive method to be defined as a series of cases:
/*  var sum = 0

  def receive = {

    case Data(byte) =>
      sum += byte

    case GetChecksum(requester) =>
      val checksum = ~(sum & 0xFF) + 1
      requester ! checksum
  }*/

  //!! One other generalization is worth noting: a sequence of cases gives you a partial function.

  //If you apply such a function on a value it does not support, it will generate a run-time exception.
  //For example, here is a partial function that returns the second element of a list of integers:
  val second: List[Int] => Int = {
    case x :: y :: _ => y //When you compile this, the compiler will correctly warn that the match is not exhaustive
  }

  /**
    * The "cons" pattern x :: xs is a special case of an infix operation pattern. You know already
    * that, when seen as an expression, an infix operation is equivalent to a method call. For patterns,
    * the rules are different: When seen as a pattern, an infix operation such as p op q is equivalent to
    * op(p, q). That is, the infix operator op is treated as a constructor pattern. In particular, a cons
    * pattern such as x :: xs is treated as ::(x, xs). This hints that there should be a class named :: that
    * corresponds to the pattern constructor.
    *
    * This hints that there should be a class named :: that corresponds to the pattern constructor.
    * Indeed, there is such a class—it is named scala.::
    */

  //This function will succeed if you pass it a three-element list, but not if you pass it an empty list:
  second(List(5, 6, 7)) // 6
  second(List()) // MatchError

  //If you want to check whether a partial function is defined, you must first tell the compiler that
  //you know you are working with partial functions.

  val secondExplicit: PartialFunction[List[Int], Int] = {
    case x :: y :: _ => y
  }

  secondExplicit.isDefinedAt(List(5,6,7)) //true
  secondExplicit.isDefinedAt(List())   //false

  //For instance, the function literal { case x :: y :: _ => y } gets translated to the following partial function value:
  new PartialFunction[List[Int], Int] {
    def apply(xs: List[Int]) = xs match {
      case x :: y :: _ => y
    }
    def isDefinedAt(xs: List[Int]) = xs match {
      case x :: y :: _ => true
      case _ => false
    }
  }
}
