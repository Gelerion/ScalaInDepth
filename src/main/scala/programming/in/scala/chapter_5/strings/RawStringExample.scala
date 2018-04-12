package programming.in.scala.chapter_5.strings

/**
  * Created by denis.shuvalov on 05/03/2018.
  */
object RawStringExample extends App {

  //You start and end a raw string with three double quotation marks in a row (""")
  println("""Welcome to Ultimax 3000
             Type "HELP" for help""")

  //However, running this code does not produce quite what is desired:
/*  Welcome to Ultimax 3000
              Type "HELP" for help*/

  //To help with this common situation, you can call stripMargin on strings. To use this method, put a pipe
  //character (|) at the front of each line, and then call stripMargin on the whole string:
  println("""Welcome to Ultimax 3000
            |Type "HELP" for help
    """.stripMargin)



}
