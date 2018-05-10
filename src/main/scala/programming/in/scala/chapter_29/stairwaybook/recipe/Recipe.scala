package programming.in.scala.chapter_29.stairwaybook.recipe

/**
  * Created by denis.shuvalov on 09/05/2018.
  *
  * Entity
  */
class Recipe(val name: String,
             val ingredients: List[Food],
             val instructions: String) {

  override def toString: String = name
}
