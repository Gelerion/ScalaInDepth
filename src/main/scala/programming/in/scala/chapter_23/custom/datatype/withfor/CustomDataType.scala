package programming.in.scala.chapter_23.custom.datatype.withfor

/**
  * Created by denis.shuvalov on 22/04/2018.
  */
abstract class CustomDataType[A] {
  def map[B](f: A => B): CustomDataType[B]
  def flatMap[B](f: A => CustomDataType[B]): CustomDataType[B]
  //In class C above, the withFilter method produces a new collection of the same class
  def withFilter(p: A => Boolean): CustomDataType[A]
  def foreach(b: A => Unit): Unit
}
