package programming.in.scala.chapter_3.io

import scala.io.Source

/**
  * Created by denis.shuvalov on 28/02/2018.
  *
  * D:\Scala\Learning\Overview\src\main\scala\programming\in\scala\chapter_3\io\ReadLinesFromFile.scala
  */
object ReadLinesFromFile extends App {

  if(args.length > 0) {
    //for (line <- Source.fromFile(args(0)).getLines()) println(s"${line.length} $line")
    val lines = Source.fromFile(args(0)).getLines().toList
    //The reduceLeft method applies the passed function to the first two elements in lines, then applies it to
    //the result of the first application and the next element in lines, and so on, all the way through the list.
    val longestLine = lines.reduceLeft(
      (a, b) => if (a.length > b.length) a else b
    )
    val maxWidth = widthOfLength(longestLine)
    for (line <- lines) {
      val numSpaces = maxWidth - widthOfLength(line)
      val padding = " " * numSpaces
      println(padding + line.length + " | " + line)
    }

  }
  else
    Console.err.println("Please enter filename")


  def widthOfLength(s: String) = s.length.toString.length
}
