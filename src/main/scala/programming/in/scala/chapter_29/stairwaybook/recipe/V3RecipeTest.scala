package programming.in.scala.chapter_29.stairwaybook.recipe

import programming.in.scala.chapter_29.stairwaybook.recipe.v3.{Browser, Database, SimpleDatabaseV3, StudentDatabase}

/**
  * Created by denis.shuvalov on 10/05/2018.
  */
object V3RecipeTest {
  def main(args: Array[String]) = {
    val db: Database =
      if(args(0) == "student")
        StudentDatabase
      else
        SimpleDatabaseV3

    object browser extends Browser {
      //Sometimes, though, you may encounter a case where two types are the same but the compiler can't verify it.
      //You will see the compiler complaining that two types are not the same, even though you as the programmer know they are.
      //In such cases you can often fix the problem using singleton types
      val database: db.type = db
    }

    val apple = SimpleDatabaseV3.foodNamed("Apple").get

    for(recipe <- browser.recipesUsing(apple))
      println(recipe)
  }

  /*
  For example, in the GotApples program, the type checker does not know that db and browser.database are the same.
  This will cause type errors if you try to pass categories between the two objects:

  object GotApples {
    // same definitions...
    for (category <- db.allCategories)
      browser.displayCategory(category)
    // ...
  }
  GotApples2.scala:14: error: type mismatch;
   found   : db.FoodCategory
   required: browser.database.FoodCategory
          browser.displayCategory(category)
                                  ^
  one error found
   */
}
