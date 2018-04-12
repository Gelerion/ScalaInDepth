package programming.in.scala.chapter_7

import java.io.File

import scala.io.Source

/**
  * Created by denis.shuvalov on 07/03/2018.
  */
object ForExpression extends App {

  for (i <- 1 to 4) println(s"Iteration with to: $i")
  println("------------------------------------------------")
  for (i <- 1 until 4) println(s"Iteration with unit: $i")
  println("------------------------------------------------")

  // --------- Filtering ---------
  val filesHere = new File("D:\\Scala\\Learning\\Overview\\src\\main\\scala\\programming\\in\\scala\\chapter_7").listFiles

  for (file <- filesHere if file.getName.endsWith(".scala")) println(file)
  println("------------------------------------------------")
  //more defensive
  for (file <- filesHere
    if file.isFile
    if file.getName.endsWith(".scala")
  ) println(file)
  println("------------------------------------------------")

  // --------- Nested Iteration ---------

  def fileLines(file: File) = Source.fromFile(file).getLines().toList

  def grep(pattern: String) = {
    for(
      file <- filesHere if file.getName.endsWith(".scala");
      line <- fileLines(file) if line.trim.matches(pattern) //! inner loop
      //Mid-stream variable bindings
      //trimmed = line.trim
      //if trimmed.matches(pattern)
    ) println("grep: [" + file + ": " + line.trim + "]")
  }

  grep(".*gcd.*")
  println("------------------------------------------------")

  // --------- Producing a new collection ---------
  //Each time the body of the for expression executes, it produces one value, in this case simply file. When the
  //for expression completes, the result will include all of the yielded values contained in a single collection.
  def scalaFiles = //Array[File]
    for {
      file <- filesHere
      if file.getName.endsWith(".scala")
    } yield file

  // for clauses yield body

}
