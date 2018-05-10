package programming.in.scala.chapter_29.stairwaybook.recipe.v2

import programming.in.scala.chapter_29.stairwaybook.recipe._

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
object SimpleDatabaseV2 extends DatabaseV1 {
  def allFoods: List[Food] = List(Apple, Orange, Cream, Sugar)

  def allRecipes: List[Recipe] = List(FruitSalad)

  private var categories = List(
    FoodCategory("fruits", List(Apple, Orange)),
    FoodCategory("misc", List(Cream, Sugar)))

  def allCategories = categories
}
