package programming.in.scala.chapter_33.json.parser

import java.io.FileReader

import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Created by denis.shuvalov on 15/05/2018.
  *
  * This parser follows the same structure as the arithmetic expression parser. It is again a straightforward mapping
  * of the productions of the JSON grammar. The productions use one shortcut that simplifies the grammar: The repsep
  * combinator parses a (possibly empty) sequence of terms that are separated by a given separator string.
  * For instance repsep(member, ",") parses a comma-separated sequence of member terms. Otherwise, the productions
  * in the parser correspond exactly to the productions in the grammar, as was the case for the arithmetic
  * expression parsers.
  */
class JSONParser extends JavaTokenParsers {

  def value:  Parser[Any] = obj | arr | stringLiteral | floatingPointNumber | "null" | "true" | "false"
  def obj:    Parser[Any] = "{"~repsep(member, ",")~"}"
  def arr:    Parser[Any] = "["~repsep(value, ",")~"]"
  def member: Parser[Any] = stringLiteral~":"~value
}

object ParseJSON extends JSONParser {
  def main(args: Array[String]) = {
    val input =
      """
        |   {
        |      "address book": {
        |        "name": "John Smith",
        |        "address": {
        |          "street": "10 Market Street",
        |          "city"  : "San Francisco, CA",
        |          "zip"   : 94111
        |        },
        |        "phone numbers": [
        |          "408 338-4238",
        |          "408 111-6892"
        |        ]
        |      }
        |    }
      """.stripMargin

    println(parseAll(value, input))
  }
}

class AdvJsonParser extends JavaTokenParsers {
  def value:  Parser[Any] =
    obj |
    arr |
    stringLiteral |
    floatingPointNumber |
    "null" ^^ (_ => null) |
    "true" ^^ (_ => true) |
    "false" ^^ (_ => false)


  //the ~ operator produces as its result an instance of a case class with the same name: ~
  //first version
  //  def obj:    Parser[Map[String, Any]] = "{"~repsep(member, ",")~"}" ^^ {
  //    case "{"~ms~"}" => Map() ++ ms
  //  }

  /*
  The name of the class '~' is intentionally the same as the name of the sequence combinator method, ~.
  That way, you can match parser results with patterns that follow the same structure as the parsers themselves.
  For instance, the pattern "{"~ms~"}" matches a result string "{" followed by a result variable ms, which is followed
  in turn by a result string "}". This pattern corresponds exactly to what is returned by the parser on the left of the ^^.
  In its desugared versions where the ~ operator comes first, the same pattern reads ~(~("{", ms), "}"), but this is
  much less legible.

  The purpose of the "{"~ms~"}" pattern is to strip off the braces so that you can get at the list of members resulting
  from the repsep(member, ",") parser. In cases like these there is also an alternative that avoids producing unnecessary
  parser results that are immediately discarded by the pattern match. The alternative makes use of the ~> and <~ parser
  combinators. Both express sequential composition like ~, but ~> keeps only the result of its right operand, whereas <~
  keeps only the result of its left operand. Using these combinators, the JSON object parser can be expressed
  more succinctly:
   */
  def obj: Parser[Map[String, Any]] =
  "{"~> repsep(member, ",") <~"}" ^^ (Map() ++ _)

  def arr: Parser[List[Any]] =
    "["~> repsep(value, ",") <~"]"

  def member: Parser[(String, Any)] =
    stringLiteral~":"~value ^^ { case name~":"~value => (name, value) }
}

case class ~[+A, +B](x: A, y: B) {
  override def toString: String = "(" + x + "~" + y + ")"
}

object AdvParseJSON extends AdvJsonParser {
  def main(args: Array[String]) = {
    val input =
      """
        |   {
        |      "address book": {
        |        "name": "John Smith",
        |        "address": {
        |          "street": "10 Market Street",
        |          "city"  : "San Francisco, CA",
        |          "zip"   : 94111
        |        },
        |        "phone numbers": [
        |          "408 338-4238",
        |          "408 111-6892"
        |        ]
        |      }
        |    }
      """.stripMargin

    println(parseAll(value, input))
  }
}