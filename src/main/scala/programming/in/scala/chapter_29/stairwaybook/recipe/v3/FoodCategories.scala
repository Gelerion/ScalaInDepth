package programming.in.scala.chapter_29.stairwaybook.recipe.v3

import programming.in.scala.chapter_29.stairwaybook.recipe.Food

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
trait FoodCategories {
  case class FoodCategory(name: String, foods: List[Food])
  def allCategories: List[FoodCategory]
}
