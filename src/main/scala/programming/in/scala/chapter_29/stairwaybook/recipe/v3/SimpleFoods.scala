package programming.in.scala.chapter_29.stairwaybook.recipe.v3

import programming.in.scala.chapter_29.stairwaybook.recipe.{Apple, Food}

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
trait SimpleFoods {
  object Pear extends Food("Pear")
  def allFoods = List(Apple, Pear)
  def allCategories = Nil
}
