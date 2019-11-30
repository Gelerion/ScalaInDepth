package programming.in.scala.advanced.cats.functional.monad

import cats.syntax.either._ // for asRight

object EitherMonadExample {

  def main(args: Array[String]): Unit = {
    //These “smart constructors” have advantages over Left.apply and Right.apply because they
    //return results of type Either instead of Left and Right
    val a = 3.asRight[String]
    val b = 4.asRight[String]
    println(for {
      x <- a
      y <- b
    } yield x * x + y * y) //Right(25)

    println(countPositive(List(1, 2, 3)))
    println(countPositive(List(1, -2, 3)))

    //Additional
    /*
    Either.catchOnly[NumberFormatException]("foo".toInt)
    Either.catchNonFatal(sys.error("Badness"))

    Either.fromTry(scala.util.Try("foo".toInt))
    Either.fromOption[String, Int](None, "Badness")
     */
  }

  def countPositive(nums: List[Int]): Either[String, Int] =
    nums.foldLeft(0.asRight[String]) { (acc, num) =>
      if (num > 0) acc.map(_ + 1)
      else "Negative. Stopping!".asLeft[Int]
    }

}
