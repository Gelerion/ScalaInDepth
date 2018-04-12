package programming.in.scala

/**
  * Created by denis.shuvalov on 18/03/2018.
  *
  * Any kind of definition that you can put inside a class can also be at the top level of a package. If you have
  * some helper method you'd like to be in scope for an entire package, go ahead and put it right at the top level of the package.
  *
  * To do so, put the definitions in a package object. Each package is allowed to have one package object. Any
  * definitions placed in a package object are considered members of the package itself.
  */
package object chapter_13 {

/*
  // In file bobsdelights/package.scala
  package object bobsdelights {
    def showFruit(fruit: Fruit) = {
      import fruit._
      println(name + "s are " + color)
    }
  }

  // In file PrintMenu.scala
  package printmenu
  import bobsdelights.Fruits
  import bobsdelights.showFruit

  object PrintMenu {
    def main(args: Array[String]) = {
      for (fruit <- Fruits.menu) {
        showFruit(fruit)
      }
    }
  }
 */

}
