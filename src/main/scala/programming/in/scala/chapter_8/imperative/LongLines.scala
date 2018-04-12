package programming.in.scala.chapter_8.imperative

import scala.io.Source

/**
  * Created by denis.shuvalov on 08/03/2018.
  *
  * demonstrated an important design principle of the functional programming style: programs should be decomposed
  * into many small functions that each do a well-defined task.
  */
object LongLines {

  def processFile(filename: String, width: Int) = {
    val source = Source.fromFile(filename)
    for (line <- source.getLines()) {
      processLine(filename, width, line)
    }
  }

  private def processLine(filename: String, width: Int, line: String) = {
    if (line.length > width) {
      println(s"$filename : ${line.trim}")
    }
  }

}
