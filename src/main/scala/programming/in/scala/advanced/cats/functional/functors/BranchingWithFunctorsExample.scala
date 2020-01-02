package programming.in.scala.advanced.cats.functional.functors

import cats.Functor
import cats.instances.function._
import cats.syntax.functor._     // for map

object BranchingWithFunctorsExample {

  /**
    * Write a Functor for the following binary tree data type. Verify that the code works as expected on instances
    * of Branch and Leaf
    */
  def main(args: Array[String]): Unit = {
    //Branch(Leaf(10), Leaf(20)).map(_ * 2)
    //error: value map is not a member of wrapper.Branch[Int] Branch(Leaf(10), Leaf(20)).map(_ * 2)
    //!! Oops! This falls foul of the invariance problem
    //The compiler can find a Functor instance for Tree but not for Branch or Leaf

    //After adding branch and leaf we can use our Functor properly:
    println(Tree.leaf(100).map(_ * 2))
    println(Tree.branch(Tree.leaf(10), Tree.leaf(20)).map(_ * 2))
  }

}

sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

object Tree {
  //add some smart constructors to compensate invariance problem
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)

//  implicit val leafFunctor: Functor[Leaf] = new Functor[Leaf] {
//    override def map[A, B](leaf: Leaf[A])(func: A => B): Leaf[B] = Leaf(func(leaf.value))
//  }
//
//  implicit val branchFunctor = new Functor[Branch] {
//    override def map[A, B](branch: Branch[A])(func: A => B): Branch[B] = {
//      branch.left match {
//        case Branch(left, right) =>
//        case Leaf(value) => func(value)
//      }
////      Branch(map(branch.left), map(branch.right))
//    }
//  }

  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](tree: Tree[A])(func: A => B): Tree[B] = tree match {
      case Branch(left, right) => Branch(map(left)(func), map(right)(func))
      case Leaf(value) =>         Leaf(func(value))
    }
  }
}