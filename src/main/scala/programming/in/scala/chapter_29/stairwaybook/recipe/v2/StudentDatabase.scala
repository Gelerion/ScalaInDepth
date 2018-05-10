package programming.in.scala.chapter_29.stairwaybook.recipe.v2

import programming.in.scala.chapter_29.stairwaybook.recipe.{Food, Recipe}

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
object StudentDatabase extends DatabaseV1 {
  object FrozenFood extends Food("FrozenFood")

  object HeatItUp extends Recipe(
    "heat it up",
    List(FrozenFood),
    "Microwave the 'food' for 10 minutes.")

  def allFoods = List(FrozenFood)
  def allRecipes = List(HeatItUp)
  def allCategories = List(FoodCategory("edible", List(FrozenFood)))
}

object StudentBrowser extends BrowserV1 {
  val database = StudentDatabase
}
