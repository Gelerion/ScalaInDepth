package programming.in.scala.advanced.cats.type_safe_comparing

import cats.Eq
import cats.instances.int._ // for Eq
import cats.syntax.eq._ // for === and =!=
import java.util.Date
import cats.instances.long._ // for Eq

object TypeSafeCompareWithCats {
  def main(args: Array[String]): Unit = {
    val eqInt = Eq[Int]

    eqInt.eqv(123, 123) //true

    //eqInt.eqv(123, "234") -> compile error

    println(123 === 123)
    println(123 =!= 234)

    val x = new Date() // now
    Thread.sleep(100)
    val y = new Date() // a bit later than now
    println(x === y)

  }

  //Comparing Custom Types
  implicit val dateEq: Eq[Date] =
    Eq.instance[Date] { (date1, date2) =>
      date1.getTime === date2.getTime
    }


}
