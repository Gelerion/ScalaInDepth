package programming.in.scala.advanced.cats.functional.functors.contravariant

/**
  * The first of our type classes, the contravariant functor, provides an operation called contramap that represents
  * “prepending” an operation to a chain.
  *
  * The contramap method only makes sense for data types that represent transformations.
  * For example, we can’t define contramap for an Option because there is no way of feeding a value in an Option[B]
  * backwards through a function A => B
  */
object ContravariantFunctorExample {
  def main(args: Array[String]): Unit = {
    println(format("hello"))
    println(format(true))

    //Now define an instance of Printable for the following Box case class.
    //You’ll need to write this as an implicit def
    final case class Box[A](value: A)

    /*
    definition by hand:
    implicit def boxPrintable[A](implicit p: Printable[A]) =
      new Printable[Box[A]] {
        def format(box: Box[A]): String =
          p.format(box.value)
      }
     */

    //Rather than writing out the complete definition from scratch (new Printable[Box] etc…),
    //create your instance from an existing instance using contramap.
    implicit def boxPrintable[A](implicit printable: Printable[A]): Printable[Box[A]] =
      printable.contramap[Box[A]](_.value)

    println(format(Box("hello world")))
  }

  def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)

}


trait Printable[A] {
  self =>

  def format(value: A): String

  /*
  Here’s a working implementation. We call func to turn the B into an A and then use our original
  Printable to turn the A into a String. In a small show of sleight of hand we use a self alias to distinguish
  the outer and inner Printables:
   */
  def contramap[B](func: B => A): Printable[B] = new Printable[B] {
    def format(value: B): String = self.format(func(value))
  }
}

object Printable {

  implicit val stringPrintable: Printable[String] =
    new Printable[String] {
      def format(value: String): String =
        "\"" + value + "\""
    }

  implicit val booleanPrintable: Printable[Boolean] =
    new Printable[Boolean] {
      def format(value: Boolean): String =
        if(value) "yes" else "no"
    }
}