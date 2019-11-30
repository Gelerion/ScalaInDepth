package programming.in.scala.advanced.cats.functional.trampoline.adt

import scala.annotation.tailrec

object TrampolineInterpreter {

  /*
  Done case is straightforward, More case has to first execute our suspended function
  to get either More[A] or Done[A] and recursively call run on that.

  This kind if recursion is safe and will be optimized by the compiler.
   */
  @tailrec def run[A](trampoline: Trampoline[A]): A = {
    trampoline match {
      case Done(value) => value
      case More(fn) => run(fn()) // <- tail recursive, yay
    }
  }

}
