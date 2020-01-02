package programming.in.scala.advanced.shapeless.chapter_7.polymorphic_functions

import shapeless._

object hlist_poly {
  def main(args: Array[String]): Unit = {
    println((10 :: "hello" :: true :: HNil).map(sizeOf))
    //10 :: 5 :: 1 :: HNil

    //We can also flatMap over an HList, as long as every corresponding case in our Poly returns another HList:
    println("flatMap")
    println((10 :: "hello" :: true :: HNil).flatMap(valueAndSizeOf))
    //10 :: 10 :: hello :: 5 :: true :: 1 :: HNil

    println("fold")
    (10 :: "hello" :: 100 :: HNil).foldLeft(0)(sum)
  }
}

object sizeOf extends Poly1 {
  implicit val intCase: Case.Aux[Int, Int] =
    at(identity)

  implicit val stringCase: Case.Aux[String, Int] =
    at(_.length)

  implicit val booleanCase: Case.Aux[Boolean, Int] =
    at(bool => if (bool) 1 else 0)
}

object valueAndSizeOf extends Poly1 {
  implicit val intCase: Case.Aux[Int, Int :: Int :: HNil] =
    at(num => num :: num :: HNil)

  implicit val stringCase: Case.Aux[String, String :: Int :: HNil] =
    at(str => str :: str.length :: HNil)

  implicit val booleanCase: Case.Aux[Boolean, Boolean :: Int :: HNil] =
    at(bool => bool :: (if(bool) 1 else 0) :: HNil)
}

object sum extends Poly2 {
  implicit val intIntCase: Case.Aux[Int, Int, Int] =
    at((a, b) => a + b)

  implicit val intStringCase: Case.Aux[Int, String, Int] =
    at((a, b) => a + b.length)
}