package programming.in.scala.chapter_29.stairwaybook.recipe.v1

import programming.in.scala.chapter_29.stairwaybook.recipe.Food

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
object SimpleBrowserV1 {

  def recipesUsing(food: Food) =
    SimpleDatabaseV1.allRecipes.filter(recipe => recipe.ingredients.contains(food))

  def displayCategory(category: SimpleDatabaseV1.FoodCategory) = {
    println(category)
  }
}
