package programming.in.scala.advanced.shapeless.chapter_7.polymorphic_functions

object polymorphic_functions {
  def main(args: Array[String]): Unit = {
    println(myPoly.apply(123)) //61.5

    println(shaplessPoly(123))

    println("multiply(3, 4)")
    println(multiply(3, 4))
    // res12: multiply.intIntCase.Result = 12

    println("multiply(3, \"4\")")
    println(multiply(3, "4"))
    // res13: multiply.intStrCase.Result = 444
  }
}

trait CustomCase[P, A] {
  type Result
  def apply(a: A): Result
}

trait CustomPoly {
  def apply[A](arg: A)(implicit cse: CustomCase[this.type, A]): cse.Result =
    cse.apply(arg)
}

object myPoly extends CustomPoly {
  implicit def intCase: CustomCase[this.type, Int] = new CustomCase[this.type, Int] {
    override type Result = Double
    override def apply(num: Int): Double = num / 2.0
  }

  implicit def stringCase: CustomCase[this.type, String] =
    new CustomCase[this.type, String] {
      type Result = Int
      def apply(str: String): Int = str.length
    }
}

import shapeless._

object shaplessPoly extends Poly1 {
  implicit val intCase: Case.Aux[Int, Double] =
    at(num => num / 2.0)

  implicit val stringCase: Case.Aux[String, Int] =
    at(str => str.length)
}

object multiply extends Poly2 {
  implicit val intIntCase: Case.Aux[Int, Int, Int] =
    at((a, b) => a * b)

  implicit val intStrCase: Case.Aux[Int, String, String] =
    at((a, b) => b * a)
}