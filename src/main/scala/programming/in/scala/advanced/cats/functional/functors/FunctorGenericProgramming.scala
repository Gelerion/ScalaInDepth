package programming.in.scala.advanced.cats.functional.functors

import cats.Functor
import cats.instances.function._
import cats.syntax.functor._     // for map

object FunctorGenericProgramming {

  def main(args: Array[String]): Unit = {

    import cats.instances.option._ // for Functor
    import cats.instances.list._   // for Functor

    println(doMath(Option(20))) //Some(42)
    println(doMath(List(1, 2, 3))) //List(4, 6, 8)

    /*
    To illustrate how this works, let’s take a look at the definition of the map method in cats.syntax.functor.
    Here’s a simplified version of the code:

    implicit class FunctorOps[F[_], A](src: F[A]) {
      def map[B](func: A => B)(implicit functor: Functor[F]): F[B] =
        functor.map(src)(func)
    }

    The compiler can use this extension method to insert a map method wherever no built-in map is available:

    foo.map(value => value + 1)

    Assuming foo has no built-in map method, the compiler detects the potential error and wraps the expression
    in a FunctorOps to fix the code:

    new FunctorOps(foo).map(value => value + 1)

    The map method of FunctorOps requires an implicit Functor as a parameter. This means this code will only
    compile if we have a Functor for F in scope.

     */

    //custom
    final case class Box[A](value: A)

    implicit val boxFunctor: Functor[Box] = new Functor[Box] {
      override def map[A, B](fa: Box[A])(f: A => B): Box[B] = Box(f(fa.value))
    }

    val box = Box[Int](123)
    println(doMath(box)) //Box(248)

  }


  //This time we’ll abstract over functors so we’re not working with any particular concrete type.
  def doMath[F[_]](start: F[Int])
                  (implicit functor: Functor[F]): F[Int] = {
    start.map(n => (n + 1) * 2)
  }

}
