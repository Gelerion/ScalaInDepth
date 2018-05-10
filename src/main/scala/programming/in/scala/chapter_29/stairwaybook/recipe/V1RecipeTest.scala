package programming.in.scala.chapter_29.stairwaybook.recipe

import programming.in.scala.chapter_29.stairwaybook.recipe.v1.{SimpleBrowserV1, SimpleDatabaseV1}

/**
  * Created by denis.shuvalov on 09/05/2018.
  */
object V1RecipeTest extends App {
  val apple = SimpleDatabaseV1.foodNamed("Apple").get
  //Food = Apple

  println(SimpleBrowserV1.recipesUsing(apple))

  /*
  Although the examples shown so far did manage to partition your application into separate database and browser modules,
  the design is not yet very "modular." The problem is that there is essentially a "hard link" from the browser module
  to the database modules:

  SimpleDatabase.allRecipes.filter(recipe => ...

  Because the SimpleBrowser module mentions the SimpleDatabase module by name, you won't be able to plug in a different
  implementation of the database module without modifying and recompiling the browser module
   */

  //How can the code be made reconfigurable, so that you can configure it using either database implementation?
  //The answer is a familiar one: If a module is an object, then a template for a module is a class. Just like a class
  //describes the common parts of all its instances, a class can describe the parts of a module that are common to all
  //of its possible configurations.

}
