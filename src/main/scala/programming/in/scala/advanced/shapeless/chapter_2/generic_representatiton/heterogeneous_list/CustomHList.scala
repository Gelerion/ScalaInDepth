package programming.in.scala.advanced.shapeless.chapter_2.generic_representatiton.heterogeneous_list

import scala.language.implicitConversions

final class CustomHListOps[L <: CustomHList](hlist : L) {
  def ::[H](head : H) : H :: L = new ::(head, hlist)
}

sealed trait CustomHList
case object CustomHList {
  implicit def hlistOps[L <: CustomHList](hlist: L): CustomHListOps[L] = new CustomHListOps(hlist)
}

sealed trait CustomHNil extends CustomHList {
  def ::[H](head: H) = new ::(head, this)
}
case object CustomHNil extends CustomHNil

//prepend element
final case class ::[H, T <: CustomHList](head: H, tail: T) extends CustomHList

object TestHeterogeneousProduct {
  def main(args: Array[String]): Unit = {
    val h: CustomHList = "test" :: 10 :: CustomHNil /// String :: Int :: HNil
    println(h)
  }
}