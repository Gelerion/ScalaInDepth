package programming.in.scala.chapter_7

/**
  * Created by denis.shuvalov on 07/03/2018.
  */
object WhileLoops {

  var line = ""
  do {
    line = io.StdIn.readLine()
    println("Read: " + line)
  } while (line != "")

  /*
  The while and do-while constructs are called "loops," not expressions, because they don't result in an interesting
  value. The type of the result is Unit. It turns out that a value (and in fact, only one value) exists whose type is
  Unit. It is called the unit value and is written (). The existence of () is how Scala's Unit differs from Java's void.
   */

  def greet() = { println("hi") } //procedure
  //greet: ()Unit

  //--- unit value and is written ()
  () == greet() //true

  //----------------------------
  var line1 = ""
  while ((line1 = readLine()) != "") // This doesn't work!
    println("Read: " + line)

  //When you compile this code, Scala will give you a warning that comparing values of type Unit and String using !=
  //will always yield true. Whereas in Java, assignment results in the value assigned (in this case a line from the
  //standard input), in Scala assignment always results in the unit value, (). Thus, the value of the assignment
  //"line = readLine()" will always be () and never be "". As a result, this while loop's condition will never be
  //false, and the loop will, therefore, never terminate.
}
