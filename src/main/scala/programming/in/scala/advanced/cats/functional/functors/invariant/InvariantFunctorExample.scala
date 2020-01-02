package programming.in.scala.advanced.cats.functional.functors.invariant

object InvariantFunctorExample {

  def main(args: Array[String]): Unit = {
    println(encode(123.4)) //str
    val dbl: Double = decode[Double]("123.4")
    println(dbl)

    case class Box[A](value: A)
    implicit def boxCodec[A](implicit codec: FCodec[A]) =
      codec.imap[Box[A]](Box(_), _.value)

    println(encode(Box(123.4)))
    println(decode[Box[Double]]("123.4"))
  }

  def encode[A](value: A)(implicit c: FCodec[A]): String =
    c.encode(value)

  def decode[A](value: String)(implicit c: FCodec[A]): A =
    c.decode(value)

}

trait FCodec[A] {
  def encode(value: A): String
  def decode(value: String): A

  def imap[B](dec: A => B, enc: B => A): FCodec[B] = {
    val self = this

    new FCodec[B] {
      def encode(value: B): String = self.encode(enc(value))
      def decode(value: String): B = dec(self.decode(value))
    }
  }
}

object FCodec {
  //As an example use case, imagine we have a basic Codec[String], whose encode and decode methods
  implicit val stringCodec: FCodec[String] =
    new FCodec[String] {
      def encode(value: String): String = value
      def decode(value: String): String = value
    }

  //We can construct many useful Codecs for other types by building off of stringCodec using imap:
  implicit val intCodec: FCodec[Int] = stringCodec.imap(_.toInt, _.toString)
  implicit val booleanCodec: FCodec[Boolean] = stringCodec.imap(_.toBoolean, _.toString)
  implicit val doubleCodec: FCodec[Double] = stringCodec.imap(_.toDouble, _.toString)

  /*
  Note that the decode method of our Codec type class doesn’t account for failures. If we want to model more
  sophisticated relationships we can move beyond functors to look at lenses and optics.

  Optics are beyond the scope of this book. However, Julien Truffaut’s library Monocle provides a great starting
  point for further investigation.
   */
}


