package programming.in.scala.chapter_17.sequences

/**
  * Created by denis.shuvalov on 03/04/2018.
  */
class ArraySeq {
  //Arrays allow you to hold a sequence of elements and efficiently access an element at an arbitrary position,
  //either to get or update the element, with a zero-based index.

  val fiveInts = new Array[Int](5)
  val fiveToOne = Array(5,4,3,2,1)

  //arrays are accessed in Scala by placing an index in parentheses
  fiveInts(0) = fiveToOne(4) //Array(1, 0, 0, 0, 0)
}
