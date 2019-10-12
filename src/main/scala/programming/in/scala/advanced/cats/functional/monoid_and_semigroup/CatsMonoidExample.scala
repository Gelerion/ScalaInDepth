package programming.in.scala.advanced.cats.functional.monoid_and_semigroup

import cats.Monoid
import cats.instances.string._ // for Monoid
import cats.instances.int._ // for Monoid
import cats.instances.option._ // for Monoid
import cats.Semigroup
import cats.syntax.semigroup._ // for |+|

object CatsMonoidExample {
  def main(args: Array[String]): Unit = {
    println(Monoid[String].combine("Hi ", "there")) //Hi there
    //equivalent to: Monoid.apply[String].combine("Hi ", "there")
    println(Semigroup[String].combine("Hi ", "there"))

    val a = Option(22)
    val b = Option(20)
    println(Monoid[Option[Int]].combine(a, b))

    val stringResult = "Hi " |+| "there" |+| Monoid[String].empty
    val intResult = 1 |+| 2 |+| Monoid[Int].empty

    println("add: " + add(List(1, 2, 4))) //7
    println("addGeneric: " + addGeneric(List(Some(1), None, Some(2), None, Some(3))))

  }

  def add(items: List[Int]): Int = {
    //1. items.foldLeft(0)(_ + _)
    //2. items.foldLeft(Monoid[Int].empty)(_ |+| _)
    Monoid[Int].combineAll(items)
  }

  def addGeneric[A : Monoid](items: List[A]): A = {
    //(implicit monoid: Monoid[A]) items.foldLeft(monoid.empty)(_ |+| _)
    items.foldLeft(Monoid[A].empty)(_|+|_)
  }

  //We need to release this code really soon so we can’t make any modifications to add. Make it so!
  case class Order(totalCost: Double, quantity: Double)
  implicit val orderAddMonoid: Monoid[Order] = new Monoid[Order] {
    override def empty: Order = Order(0, 0)
    override def combine(x: Order, y: Order): Order = Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
  }

}
