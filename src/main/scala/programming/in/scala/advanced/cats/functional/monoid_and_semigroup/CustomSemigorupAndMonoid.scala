package programming.in.scala.advanced.cats.functional.monoid_and_semigroup

object CustomFunc {

}

/*
A semigroup is just the combine part of a monoid.
 */
trait FuncSemigroup[A] {
  def combine(x: A, y: A): A
}

/*
Formally, a monoid for a type A is:
 * an operation combine with type (A, A) => A
 * an element empty of type A
 */
trait FuncMonoid[A] extends FuncSemigroup[A] {
  def combine(x: A, y: A): A
  def empty: A
}

object FuncMonoid {
  def apply[A](implicit monoid: FuncMonoid[A]): FuncMonoid[A] = monoid
}

//In addition to providing the combine and empty operations, monoids must formally obey several laws.
object MonoidLaws {
  def associativeLaw[A](x: A, y: A, z: A)
                       (implicit m: FuncMonoid[A]): Boolean = {
    m.combine(x, m.combine(y, z)) == m.combine(m.combine(x, y), z)
  }

  def identityLaw[A](x: A)
                    (implicit m: FuncMonoid[A]): Boolean = {
    (m.combine(x, m.empty) == x) && (m.combine(m.empty, x) == x)
  }
}

//Integer subtraction, for example, is not a monoid because subtraction is not associative:
// (1 - 2) - 3 => -4
// 1 - (2 - 3) => 2