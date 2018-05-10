package programming.in.scala.chapter_29.stairwaybook.recipe.v3

import programming.in.scala.chapter_29.stairwaybook.recipe.Food

/**
  * Created by denis.shuvalov on 10/05/2018.
  */
abstract class Browser {
  val database: Database

  def recipesUsing(food: Food) =
    database.allRecipes.filter(recipe => recipe.ingredients.contains(food))

  def displayCategory(category: database.FoodCategory) = {
    println(category)
  }

}
