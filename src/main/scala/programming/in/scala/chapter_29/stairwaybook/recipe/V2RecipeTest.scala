package programming.in.scala.chapter_29.stairwaybook.recipe

import programming.in.scala.chapter_29.stairwaybook.recipe.v2.{SimpleBrowserV2, SimpleDatabaseV2}

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
object V2RecipeTest extends App {
  val apple = SimpleDatabaseV2.foodNamed("Apple").get
  //Food = Apple

  println(SimpleBrowserV2.recipesUsing(apple))
}
