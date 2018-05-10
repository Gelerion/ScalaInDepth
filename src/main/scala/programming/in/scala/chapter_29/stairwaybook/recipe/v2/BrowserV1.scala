package programming.in.scala.chapter_29.stairwaybook.recipe.v2

import programming.in.scala.chapter_29.stairwaybook.recipe.Food

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
abstract class BrowserV1 {
  val database: DatabaseV1

  def recipesUsing(food: Food) =
    database.allRecipes.filter(recipe => recipe.ingredients.contains(food))

  def displayCategory(category: database.FoodCategory) = {
    println(category)
  }
}
