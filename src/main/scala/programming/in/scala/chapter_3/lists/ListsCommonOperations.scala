package programming.in.scala.chapter_3.lists

/**
  * Created by denis.shuvalov on 27/02/2018.
  */
object ListsCommonOperations extends App {

  //List("Cool", "tools", "rule") - Creates a new List[String] with the three values "Cool", "tools", and "rule"
  //List("a", "b") ::: List("c", "d") - Concatenates two lists (returns a new List[String] with values "a", "b", "c", and "d")

  val thrill = "Will" :: "fill" :: "until" :: Nil

  println(thrill)
  println("thrill(2): " + thrill(2))

  thrill.count(s => s.length == 4) //Counts the number of string elements in thrill that have length 4
  thrill.drop(2) //Returns the thrill list without its first 2 elements (returns List("until"))
  thrill.dropRight(2) //Returns the thrill list without its rightmost 2 elements (returns List("Will"))
  thrill.exists(s => s == "until") //Determines whether a string element exists in thrill that has the value "until" (returns true)
  thrill.filter(s => s.length == 4) //Returns a list of all elements, in order, of the thrill list that have length 4 (returns List("Will", "fill"))
  thrill.forall(s => s.endsWith("l")) //Indicates whether all elements in the thrill list end with the letter "l" (returns true)
  thrill.foreach(print)
  thrill.head //Returns the first element in the thrill list (returns "Will")
  thrill.init //Returns a list of all but the last element in the thrill list (returns List("Will", "fill"))
  thrill.last //Returns the last element in the thrill list (returns "until")
  thrill.tail //Returns the thrill list minus its first element (returns List("fill", "until"))
  thrill.mkString(", ") //Makes a string with the elements of the list (returns "Will, fill, until")
  thrill.filterNot(s => s.length == 4) //Returns a list of all elements, in order, of the thrill list except those that have length 4 (returns List("until"))
  thrill.reverse
  thrill.sorted // Sort using Natural ordering as defined for Strings in Scala Library
  thrill.sortWith((s ,t) => s.charAt(0).toLower < t.charAt(0).toLower) //Returns a list containing all elements of the thrill list in alphabetical order of the first character lowercased (returns List("fill", "until", "Will"))

}
