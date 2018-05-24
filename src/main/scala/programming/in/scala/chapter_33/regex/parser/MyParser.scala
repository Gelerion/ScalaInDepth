package programming.in.scala.chapter_33.regex.parser

import scala.util.parsing.combinator.RegexParsers

/**
  * Created by denis.shuvalov on 15/05/2018.
  *
  * The parser for arithmetic expressions made use of another parser, named floatingPointNumber. This parser, which was
  * inherited from Arith's supertrait, JavaTokenParsers, recognizes a floating point number in the format of Java.
  * But what do you do if you need to parse numbers in a format that's a bit different from Java's? In this situation,
  * you can use a regular expression parser.
  */
object MyParser extends RegexParsers{

  //RegexParsers.regex

  //The idea is that you can use any regular expression as a parser. The regular expression parses all strings that
  //it can match. Its result is the parsed string.
  val ident: Parser[String] = """[a-zA-Z_]\w*""".r

}
