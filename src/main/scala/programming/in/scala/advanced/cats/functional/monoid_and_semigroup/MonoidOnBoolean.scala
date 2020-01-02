package programming.in.scala.advanced.cats.functional.monoid_and_semigroup

object MonoidOnBoolean {
  /*
  Consider Boolean.
  How many monoids can you define for this type?
  For each monoid, define the combine and empty operations and convince yourself that the monoid laws hold.
   */
  def main(args: Array[String]): Unit = {
    println("AND Boolean associativeLaw (true, true, true):  " +
      MonoidLaws.associativeLaw[Boolean](true, true, true)(andMonoid))
    println("AND Boolean associativeLaw (false, true, true): " +
      MonoidLaws.associativeLaw[Boolean](false, true, true)(andMonoid))

    println("AND Boolean identityLaw (true):  " +
      MonoidLaws.identityLaw[Boolean](true)(andMonoid))
    println("AND Boolean identityLaw (false): " +
      MonoidLaws.identityLaw[Boolean](false)(andMonoid))

    //---------------------------
    println("OR Boolean associativeLaw (true, true, true):  " +
      MonoidLaws.associativeLaw[Boolean](true, true, true)(orMonoid))
    println("OR Boolean associativeLaw (false, true, true): " +
      MonoidLaws.associativeLaw[Boolean](false, true, true)(orMonoid))

    println("OR Boolean identityLaw (true):  " +
      MonoidLaws.identityLaw[Boolean](true)(orMonoid))
    println("OR Boolean identityLaw (false): " +
      MonoidLaws.identityLaw[Boolean](false)(orMonoid))
  }

  //There are four monoids for Boolean! First, we have and with operator && and identity true:
  implicit val andMonoid: FuncMonoid[Boolean] = new FuncMonoid[Boolean] {
    override def combine(x: Boolean, y: Boolean): Boolean = x && y
    override def empty: Boolean = true
  }

  //Second, we have or with operator || and identity false:
  implicit val orMonoid: FuncMonoid[Boolean] = new FuncMonoid[Boolean] {
    override def combine(x: Boolean, y: Boolean): Boolean = x || y
    override def empty: Boolean = false
  }

  //Third, we have exclusive or with identity false:
  implicit val booleanEitherMonoid: FuncMonoid[Boolean] = new FuncMonoid[Boolean] {
      def combine(a: Boolean, b: Boolean): Boolean = (a && !b) || (!a && b)
      def empty = false
  }

  //Finally, we have exclusive nor (the negation of exclusive or) with identity true:
  implicit val booleanXnorMonoid: FuncMonoid[Boolean] = new FuncMonoid[Boolean] {
      def combine(a: Boolean, b: Boolean): Boolean = (!a || b) && (a || !b)
      def empty = true
    }
}


