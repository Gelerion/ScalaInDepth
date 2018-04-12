package programming.in.scala.chapter_7

/**
  * Created by denis.shuvalov on 07/03/2018.
  */
object MatchExpressions extends App {
  //Scala's match expression lets you select from a number of alternatives, just like switch statements in
  //other languages. In general a match expression lets you select using arbitrary patterns

  val firstArg = if (args.length > 0) args(0) else ""

  //there are no breaks at the end of each alternative. Instead the break is implicit, and there is no fall through from one alternative to the next.

  firstArg match {
    case "salt" => println("pepper")
    case "chips" => println("salsa")
    case "eggs" => println("bacon")
    case _ => println("huh?") // default case is specified with an underscore (_)
  }

  //match expressions result in a value
  val friend =
    firstArg match {
      case "salt" => "pepper"
      case "chips" => "salsa"
      case "eggs" => "bacon"
      case _ => "huh?"
    }

  println(friend)

  //Aside from the code getting shorter (in number of tokens anyway), the code now disentangles
  //two separate concerns: first it chooses a food and then prints it.
}
