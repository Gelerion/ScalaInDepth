package programming.in.scala.chapter_29.stairwaybook

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
package object recipe {

  //some singleton instances of these classes, which can be used when writing tests
  object Apple extends Food("Apple")
  object Orange extends Food("Orange")
  object Cream extends Food("Cream")
  object Sugar extends Food("Sugar")

  object FruitSalad extends Recipe(
    "fruit salad",
    List(Apple, Orange, Cream, Sugar),
    "Stir it all together."
  )
}
