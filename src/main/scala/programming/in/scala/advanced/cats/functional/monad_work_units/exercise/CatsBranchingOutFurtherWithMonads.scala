package programming.in.scala.advanced.cats.functional.monad_work_units.exercise

import cats.Monad

sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

object CatsBranchingOutFurtherWithMonads {

  def main(args: Array[String]): Unit = {
    import cats.syntax.functor._ // for map
    import cats.syntax.flatMap._ // for flatMap

    val tree = branch(leaf(100), leaf(200)).
      flatMap(x => branch(leaf(x - 1), leaf(x + 1)))

    println(tree) //Branch(Branch(Leaf(99),Leaf(101)),Branch(Leaf(199),Leaf(201)))

    val tree2 = for {
      a <- branch(leaf(100), leaf(200))
      b <- branch(leaf(a - 10), leaf(a + 10))
      c <- branch(leaf(b - 1), leaf(b + 1))
    } yield c

    print(tree2) //Branch(Branch(Branch(Leaf(89),Leaf(91)),Branch(Leaf(109),Leaf(111))),Branch(Branch(Leaf(189),Leaf(191)),Branch(Leaf(209),Leaf(211))))
  }

  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)


  //Its only downside is that Cats cannot make guarantees about stack safety.
  implicit val treeMonad: Monad[Tree] = new Monad[Tree] {
    override def pure[A](x: A): Tree[A] = leaf(x)

    override def flatMap[A, B](tree: Tree[A])(func: A => Tree[B]): Tree[B] =
      tree match {
        case Branch(left, right) => branch(flatMap(left)(func), flatMap(right)(func))
        case Leaf(value) => func(value)
      }

    override def tailRecM[A, B](a: A)(fn: A => Tree[Either[A, B]]): Tree[B] =
      flatMap(fn(a)) {
        case Left(value) => tailRecM(value)(fn)
        case Right(value) => leaf(value)
      }
  }
}

