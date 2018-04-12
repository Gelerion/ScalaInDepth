package programming.in.scala.chapter_3.sets.and.maps

import scala.collection.mutable
/**
  * Created by denis.shuvalov on 28/02/2018.
  */
object SetsAndMapsExample extends App {

//  define a new var named jetSet and initialize it with an immutable set containing the two strings, "Boeing" and "Airbus".
//  As this example shows, you can create sets in Scala similarly to how you create lists and arrays: by invoking a
//  factory method named apply on a Set companion object. we invoke apply on the companion object
//  for scala.collection.immutable.Set, which returns an instance of a default, immutable Set
  var jetSet = Set("Boeing", "Airbus")
  jetSet += "Lear" //shorthand for jetSet = jetSet + "Lear"
  println(jetSet)

  //If you want a mutable set, you'll need to use an import:
  val movieSet = mutable.Set("Hitch", "Poltergeist")
  movieSet += "Shrek" //same as movieSet.+=("Shrek")
  println(movieSet)

  //Creating, initializing, and using a mutable map.
  val treasureMap = mutable.Map[Int, String]()
  treasureMap += (1 -> "Go to island.") //(1).->("Go to island.")
  //when you say 1 -> "Go to island.", you are actually calling a method named -> on an integer with the value 1,
  //passing in a string with the value "Go to island." This -> method, which you can invoke on any object in a Scala
  //program, returns a two-element tuple containing the key and value.
  val iAmTuple2: (Int, String) = 1 -> "Go to island." //implicit conversions

  treasureMap += (2 -> "Find big X on ground.")
  treasureMap += (3 -> "Dig.")
  println(treasureMap(2))

  val romanNumeral = Map(
    1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V"
  )
  println(romanNumeral(4))
}
