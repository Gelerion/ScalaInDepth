package programming.in.scala.advanced.type_classes.monoid

trait Monoid[A] {
  def empty: A
  def append(left: A, right: A): A
  def concat(list: List[A]): A = list.foldRight(empty)(append)
}

//object Monoid {
//  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
//    def empty: Int = 0
//    def append(left: Int, right: Int): Int = left + right
//  }
//
//}

object Monoid {
  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    def empty: Int = 0
    def append(left: Int, right: Int): Int = left + right
  }

  //In type-class-heavy libraries like e.g. Cats you can see an interesting pattern to avoid using the whole implicitly[Typeclass[T]]:
  def apply[T : Monoid]: Monoid[T] = implicitly[Monoid[T]]
  def append[A : Monoid](left: A, right: A): A = apply[A].append(left, right)
  def concat[A : Monoid](list: List[A]): A = apply[A].concat(list)
}
