package programming.in.scala.advanced.cats.functional.monad_work_units.reader

import cats.data.Reader

object CatsReaderMonad {
  val catName: Reader[Cat, String] = Reader(cat => cat.name)

  val greetKitty: Reader[Cat, String] = catName.map(name => s"Hello ${name}")
  val feedKitty: Reader[Cat, String] = Reader(cat => s"Have a nice bowl of ${cat.favoriteFood}")

  def main(args: Array[String]): Unit = {
    val garfiled = Cat("Garfiled", "lasange")

    catName.run(garfiled)
    //cats.Id[String] = Garfield

    println(greetAndFeed(Cat("Garfield", "lasagne")))
    //Hello Garfield. Have a nice bowl of lasagne.

    //===== Exercise: Hacking on Readers

  }

  def greetAndFeed: Reader[Cat, String] =
    for {
      greet <- greetKitty
      feed  <- feedKitty
    } yield s"$greet. $feed."

  case class Cat(name: String, favoriteFood: String)
}
