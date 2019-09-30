package programming.in.scala.advanced.shapeless.chapter_3.idiomatic_type_classes_definitions

import shapeless.{::, HNil}
import shapeless.Generic

object GenericEncoderMain {

//  implicit val iceCreamEncoder: CsvEncoder[IceCream] = {
//    val gen = Generic[IceCream]
//    val enc = CsvEncoder[gen.Repr]
//    createEncoder(iceCream => enc.encode(gen.to(iceCream)))
//  }


  def main(args: Array[String]): Unit = {
    //Taken together, these five instances allow us to summon CsvEncoders for any HList involving Strings, Ints, and Booleans:
    val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly
    println(reprEncoder.encode("abc" :: 123 :: true :: HNil)) //List(abc, 123, yes)

    //MAGIC!!!
    //we don't have encoder for IceCream, but giving the fact it just a bunch of primitives generic encoder fits as well
    println(writeCsv(IceCream("Sundae", 1, false) :: Nil))

    /*
    writeCsv(iceCreams)(
      genericEncoder(
        Generic[IceCream],
        hlistEncoder(stringEncoder,
          hlistEncoder(intEncoder,
            hlistEncoder(booleanEncoder, hnilEncoder)))))
     */
  }

  def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String =
    values.map(value => enc.encode(value).mkString(",")).mkString("\n")

}

case class IceCream(name: String, numCherries: Int, inCone: Boolean)