package programming.in.scala.chapter_29.stairwaybook.recipe.v3

import programming.in.scala.chapter_29.stairwaybook.recipe._

/**
  * Created by denis.shuvalov on 10/05/2018.
  */
trait SimpleRecipes {
  //Given the new self type, Pear is now available. Implicitly, the reference to Pear is thought of as this.Pear.
  //This is safe, because any concrete class that mixes in SimpleRecipes must also be a subtype of SimpleFoods,
  //which means that Pear will be a member. Abstract subclasses and traits do not have to follow this restriction,
  //but since they cannot be instantiated with new, there is no risk that the this.Pear reference will fail.
  this: SimpleFoods =>

  object FruitSalad extends Recipe(
    "fruit salad",
    List(Apple, Pear), //pear available via self-type
    "Stir it all together."
  )

  def allRecipes = List(FruitSalad)
}
