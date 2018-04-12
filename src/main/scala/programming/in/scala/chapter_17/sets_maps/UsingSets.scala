package programming.in.scala.chapter_17.sets_maps

/**
  * Created by denis.shuvalov on 03/04/2018.
  */
class UsingSets {
  import scala.collection.mutable

  val text = "See Spot run. Run, Spot. Run!"
  val wordsArray = text.split("[ !,.]+")

  val words = mutable.Set.empty[String]

  for(word <- wordsArray) words += word.toLowerCase //Set(see, run, spot)

  val nums = Set(1, 2, 3)

  nums + 5	//Adds an element (returns Set(1, 2, 3, 5))
  nums - 3  //Removes an element (returns Set(1, 2))

  nums ++ List(5, 6) //Adds multiple elements (returns Set(1, 2, 3, 5, 6))
  nums -- List(1, 2) //nums -- List(1, 2)	Removes multiple elements (returns Set(3))

  nums & Set(1, 3, 5, 7) //Takes the intersection of two sets (returns Set(1, 3))

  words += "the"	//Adds an element (words.toString returns Set(the))
  words -= "the"	//Removes an element, if it exists (words.toString returns Set())

//  words ++= List("do", "re", "mi")	Adds multiple elements (words.toString returns Set(do, re, mi))
//  words --= List("do", "re")	Removes multiple elements (words.toString returns Set(mi))
}
