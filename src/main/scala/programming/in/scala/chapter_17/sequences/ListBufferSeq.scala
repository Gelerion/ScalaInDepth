package programming.in.scala.chapter_17.sequences

import scala.collection.mutable.ListBuffer

/**
  * Created by denis.shuvalov on 03/04/2018.
  */
class ListBufferSeq {
  /*
  Class List provides fast access to the head of the list, but not the end. Thus, when you need to build a list
  by appending to the end, consider building the list backwards by prepending elements to the front. Then when
  you're done, call reverse to get the elements in the order you nee

  Another alternative, which avoids the reverse operation, is to use a ListBuffer. A ListBuffer is a mutable object
  (contained in package scala.collection.mutable), which can help you build lists more efficiently when you need to
  append. ListBuffer provides constant time append and prepend operations. You append elements with the += operator,
  and prepend them with the +=: operator. When you're done building, you can obtain a List by invoking toList on
  the ListBuffer.
   */

  val buf = new ListBuffer[Int]
  buf += 1 //ListBuffer(1)
  buf += 2 //ListBuffer(1, 2)
  3 +=: buf //ListBuffer(3, 1, 2)
}
