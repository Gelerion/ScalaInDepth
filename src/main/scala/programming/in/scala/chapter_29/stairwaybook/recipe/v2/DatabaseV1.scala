package programming.in.scala.chapter_29.stairwaybook.recipe.v2

import programming.in.scala.chapter_29.stairwaybook.recipe.{Food, Recipe}

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
abstract class DatabaseV1 {

  def allFoods: List[Food]
  def allRecipes: List[Recipe]
  def foodNamed(name: String) = allFoods.find(f => f.name == name)

  case class FoodCategory(name: String, foods: List[Food])
  def allCategories: List[FoodCategory]

}
