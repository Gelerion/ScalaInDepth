package programming.in.scala.chapter_29.stairwaybook.recipe.v3

import programming.in.scala.chapter_29.stairwaybook.recipe.{Food, Recipe}

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
abstract class Database extends FoodCategories {
  def allFoods: List[Food]
  def allRecipes: List[Recipe]
  def foodNamed(name: String) = allFoods.find(f => f.name == name)
}