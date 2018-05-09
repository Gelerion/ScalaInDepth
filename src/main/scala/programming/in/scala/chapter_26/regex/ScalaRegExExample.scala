package programming.in.scala.chapter_26.regex

/**
  * Created by denis.shuvalov on 08/05/2018.
  */
object ScalaRegExExample extends App {

  //simply append a .r to a string to obtain a regular expression. This is possible because there is a method named r in class StringOps
  val Decimal = """(-)?(\d+)(\.\d*)?""".r //Regex = (-)?(\d+)(\.\d*)?

  //searching for regex
  // - regex findFirstIn str
  //   Finds first occurrence of regular expression regex in string str, returning the result in an Option type.
  // - regex findAllIn str
  //   Finds all occurrences of regular expression regex in string str, returning the results in an Iterator.
  // - regex findPrefixOf str
  //   Finds an occurrence of regular expression regex at the start of string str, returning the result in an Option type.


  val input = "for -1.0 to 99 by 3"

  for (s <- Decimal findAllIn input) println(s) //-1.0 99 3

  println(Decimal findFirstIn input) //-1.0
  println(Decimal findPrefixOf input) // None

  //Extracting with regular expressions
  //What's more, every regular expression in Scala defines an extractor. The extractor is used to identify substrings
  //that are matched by the groups of the regular expression.

  val Decimal(sign, integerpart, decimalpart) = "-1.23"
  //  sign: String = -
  //  integerpart: String = 1
  //  decimalpart: String = .23

  //In this example, the pattern, Decimal(...), is used in a val definition. What happens here is that the Decimal regular
  //expression value defines an unapplySeq method. That method matches every string that corresponds to the regular
  //expression syntax for decimal numbers. If the string matches, the parts that correspond to the three groups in the
  //regular expression (-)?(\d+)(\.\d*)? are returned as elements of the pattern and are then matched by the three
  //pattern variables sign, integerpart, and decimalpart. If a group is missing, the element value is set to null,
}


