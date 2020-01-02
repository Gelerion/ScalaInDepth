package programming.in.scala.advanced.shapeless.chapter_3.idiomatic_type_classes_definitions

import shapeless.{::, Generic, HList, HNil}

trait CsvEncoder[T] {
  def encode(value: T): List[String]
}

object CsvEncoder {
  // "Summoner" method
  def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] = enc

  // "Constructor" method
  def instance[A](func: A => List[String]): CsvEncoder[A] =
    new CsvEncoder[A] {
      def encode(value: A): List[String] =
        func(value)
    }

  // Globally visible type class instances

  /*
  The apply method, known as a “summoner” or “materializer”, allows us to summon a type class instance given a target type:
  CsvEncoder[IceCream] or implicitly[CsvEncoder[IceCream]]
   */

  implicit val booleanEncoder: CsvEncoder[Boolean] =
    instance(b => if(b) List("yes") else List("no"))


  //Instances for HLists
  def createEncoder[A](func: A => List[String]): CsvEncoder[A] = instance(value => func(value))
//    new CsvEncoder[A] {
//      def encode(value: A): List[String] = func(value)
//    }

  implicit val stringEncoder: CsvEncoder[String] =
    createEncoder(str => List(str))

  implicit val intEncoder: CsvEncoder[Int] =
    createEncoder(num => List(num.toString))

  //We can combine these building blocks to create an encoder for our HList
  implicit val hnilEncoder: CsvEncoder[HNil] =
    createEncoder(hnil => Nil)

  implicit def hlistEncoder[H, T <: HList](implicit hEncoder: CsvEncoder[H], tEncoder: CsvEncoder[T]): CsvEncoder[H :: T] =
    createEncoder { case h :: t => hEncoder.encode(h) ++ tEncoder.encode(t) }

  //and the final chapter, generic encoder
  implicit def genericEncoder[A, R](
      implicit
      //create generic representation R (HList) for instance A
      gen: Generic.Aux[A, R],
      //resolved vila hlistEncoder implicits
      enc: CsvEncoder[R]) : CsvEncoder[A] =
    //createEncoder - shortcut for new CsvEncoder[A]
    //gen.to - create Hlist out of a A type
    //enc.encode - creates List[String] out of the primitive types
    createEncoder(a => enc.encode(gen.to(a)))

}
