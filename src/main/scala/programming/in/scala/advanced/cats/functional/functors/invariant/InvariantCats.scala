package programming.in.scala.advanced.cats.functional.functors.invariant

import cats.Monoid
import cats.instances.string._ // for Monoid
import cats.syntax.invariant._ // for imap
import cats.syntax.semigroup._ // for |+|

object InvariantCats {
  def main(args: Array[String]): Unit = {
    implicit val symbolMonoid: Monoid[Symbol] =
      Monoid[String].imap(Symbol.apply)(_.name)

    println(Monoid[Symbol].empty) //'

//    val s: Symbol = 'a |+| 'few |+| 'words 'afewwords
//    println(s)
  }
}
