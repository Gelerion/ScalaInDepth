package programming.in.scala.advanced.cats.functional.functors

import scala.language.higherKinds
import cats.Functor
import cats.instances.list._   // for Functor
import cats.instances.option._ // for Functor
import cats.syntax.functor._     // for map

object CatsFunctorExample {

  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3)
    println(Functor[List].map(list)(_ * 2))

    val option = Option(123)
    println(Functor[Option].map(option)(_.toString))

    //Functor also provides the lift method, which converts a function of type A => B to one that
    //operates over a functor and has type F[A] => F[B]:
    val func = (x: Int) => x + 1
    //lift -> Option[Int] => Option[Int]
    println(Functor[Option].lift(func)(Option(1)))// 2

    //For the examples to work we need to add the following compiler option to build.sbt
    //scalacOptions += "-Ypartial-unification"
/*    val func1 = (a: Int) => a + 1
    val func2 = (a: Int) => a * 2
    val func3 = (a: Int) => a + "!"
    val func4 = func1.map(func2).map(func3)
    println(func4(123)) //248*/
  }
}
