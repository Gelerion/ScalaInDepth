package programming.in.scala.chapter_30.parameterized.types

/**
  * Created by denis.shuvalov on 10/05/2018.
  */
trait Tree[+T] {
  def elem: T
  def left:  Tree[T]
  def right: Tree[T]

}

object EmptyTree extends Tree[Nothing] {
  def elem =
    throw new NoSuchElementException("EmptyTree.elem")
  def left =
    throw new NoSuchElementException("EmptyTree.left")
  def right =
    throw new NoSuchElementException("EmptyTree.right")
}

class Branch[+T](val elem: T, val left: Tree[T], val right: Tree[T]) extends Tree[T] {

  override def hashCode: Int = (elem, left, right).##

  override def equals(other: scala.Any): Boolean = other match {
    //Note! the system can only check that the other reference is (some kind of) Branch; it cannot check that the
    //element type of the tree is T.
    case that: Branch[_] =>
      (that canEqual this) &&
      this.elem == that.elem &&
      this.left == that.left &&
      this.right == that.right
    case _ => false
  }

  def canEqual(other: Any) = other match { //or other.isInstanceOf[Branch[_]]
    case that: Branch[_] => true
    case _ => false
  }
}


object TestBranch extends App {
  /*
  Fortunately, it turns out that you need not necessarily check that two Branches have the same element types when
  comparing them. It's quite possible that two Branches with different element types are equal, as long as their fields
  are the same. A simple example of this would be the Branch that consists of a single Nil element and two empty subtrees.
  It's plausible to consider any two such Branches to be equal, no matter what static types they have:
   */
  val b1 = new Branch[List[String]](Nil, EmptyTree, EmptyTree)
  val b2 = new Branch[List[Int]](Nil, EmptyTree, EmptyTree)

  println(b1 == b2) // true
}