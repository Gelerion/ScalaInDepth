package programming.in.scala.advanced.shapeless.chapter_8

object random_generator {
  def main(args: Array[String]): Unit = {
    for(i <- 1 to 3) println(random[Int])
    //1,2,3
    for(i <- 1 to 3) println(random[Char])
    //V,N,J

    for(i <- 1 to 3) println(random[Cell])
    //Cell(L,6)
    //Cell(M,2)
    //Cell(N,5)
  }

  def random[A](implicit rnd: Random[A]): A = rnd.get
}

case class Cell(col: Char, row: Int)

trait Random[A] {
  def get: A
}

import shapeless._

object Random {
  def createRandom[A](func: () => A): Random[A] = {
    new Random[A] {
      override def get: A = func()
    }
  }

  // Random numbers from 0 to 9:
  implicit val intRandom: Random[Int] =
    createRandom(() => scala.util.Random.nextInt(10))

  // Random characters from A to Z:
  implicit val charRandom: Random[Char] =
    createRandom(() => ('A'.toInt + scala.util.Random.nextInt(26)).toChar)

  // Random booleans:
  implicit val booleanRandom: Random[Boolean] =
    createRandom(() => scala.util.Random.nextBoolean)

  // ------------------- Random Products
  implicit def genericRandom[A, R](
      implicit
      gen: Generic.Aux[A, R],
      random: Lazy[Random[R]]): Random[A] =
    createRandom(() => gen.from(random.value.get))

  implicit val hnilRandom: Random[HNil] =
    createRandom(() => HNil)

  implicit def hlistRandom[H, T <: HList](
      implicit
      hRandom: Lazy[Random[H]],
      tRandom: Random[T]
      ): Random[H :: T] =
    createRandom(() => hRandom.value.get :: tRandom.get)
}
