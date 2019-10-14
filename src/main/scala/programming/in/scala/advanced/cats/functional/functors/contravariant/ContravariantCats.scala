package programming.in.scala.advanced.cats.functional.functors.contravariant

import cats.Contravariant
import cats.Show
import cats.instances.string._
import cats.syntax.contravariant._ // for contramap

object ContravariantCats {

  def main(args: Array[String]): Unit = {
    val showString = Show[String]

    val showSymbol = Contravariant[Show].
      contramap(showString)((sym: Symbol) => s"'${sym.name}")

    showSymbol.show('dave)
    showString.contramap[Symbol](_.name).show('dave)
  }

}
