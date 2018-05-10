package programming.in.scala.chapter_29.stairwaybook.recipe.v1

import programming.in.scala.chapter_29.stairwaybook.recipe._

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
object SimpleDatabaseV1 {
  def allFoods = List(Apple, Orange, Cream, Sugar)

  def foodNamed(name: String): Option[Food] = allFoods.find(_.name == name)

  def allRecipes: List[Recipe] = List(FruitSalad)

  case class FoodCategory(name: String, foods: List[Food])

  private var categories = List(
    FoodCategory("fruits", List(Apple, Orange)),
    FoodCategory("misc", List(Cream, Sugar)))

  def allCategories = categories
}

