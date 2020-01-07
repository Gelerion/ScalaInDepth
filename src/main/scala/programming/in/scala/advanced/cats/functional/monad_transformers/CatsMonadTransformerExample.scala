package programming.in.scala.advanced.cats.functional.monad_transformers

import cats.Monad
import cats.instances.list._     // for Monad
import cats.syntax.applicative._ // for pure
import cats.data.OptionT

object CatsMonadTransformerExample {
  type ListOption[A] = OptionT[List, A] //List[Option[A]]

  def main(args: Array[String]): Unit = {
    val result1: ListOption[Int] = OptionT(List(Option(10)))
    val result2 = 32.pure[ListOption]

    val program: OptionT[List, Int] = result1.flatMap((x: Int) => {
      result2.map( (y: Int) => {
        x + y
      })
    })

    println(program.value) //List(Some(42))

    println("buildingMonadStacks:")
    buildingMonadStacks()
  }

  import cats.instances.either._ // for Monad
  // Alias Either to a type constructor with one parameter:
  type ErrorOr[A] = Either[String, A]
  // Build our final monad stack using OptionT:
  type ErrorOrOption[A] = OptionT[ErrorOr, A]

  def buildingMonadStacks(): Unit = {

    /*
    // Create using apply:
    val errorStack1 = OptionT[ErrorOr, Int](Right(Some(10)))

    // Create using pure:
    val errorStack2 = 32.pure[ErrorOrOption]

    // Extracting the untransformed monad stack:
    errorStack1.value //ErrorOr[Option[Int]] = Right(Some(10))

    // Mapping over the Either in the stack:
    errorStack2.value.map(_.getOrElse(-1)) //Either[String,Int] = Right(32)
     */

    val a = 10.pure[ErrorOrOption] //ErrorOrOption[Int] = OptionT(Right(Some(10)))
    val b = 32.pure[ErrorOrOption]
    val c = a.flatMap(x => b.map(y => x + y))
    println(c.value) //Right(Some(42))
  }
}
