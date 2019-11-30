package programming.in.scala.advanced.cats.functional.trampoline

object NonStackSafeRecursion {
  def main(args: Array[String]): Unit = {
    even((0 to 1000000).toList) // blows the stack
  }

  def odd[A](xs: List[A]): Boolean = {
    xs match {
      case Nil => true
      case ::(_, tail) => even(tail)
    }
  }

  def even[A](xs: List[A]): Boolean = {
    xs match {
      case Nil => true
      case ::(_, tail) => odd(tail)
    }
  }

}
