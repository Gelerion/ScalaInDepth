package programming.in.scala.chapter_8.imperative

import scala.io.Source

/**
  * Created by denis.shuvalov on 08/03/2018.
  */
object LongLinesLocalFunctions {

  def processFile(filename: String, width: Int) = {

    //local function
    def processLine(line: String) = {
      //local functions can access the parameters of their enclosing function
      if (line.length > width) {
        println(s"$filename : ${line.trim}")
      }
    }
    //-----------

    val source = Source.fromFile(filename)
    for (line <- source.getLines()) {
      processLine(line)
    }
  }

}
