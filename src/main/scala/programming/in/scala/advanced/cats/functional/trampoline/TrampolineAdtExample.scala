package programming.in.scala.advanced.cats.functional.trampoline

import programming.in.scala.advanced.cats.functional.trampoline.adt.{Done, More, Trampoline, TrampolineInterpreter}

object TrampolineAdtExample {
  def main(args: Array[String]): Unit = {
    //example
    val tramExample: Trampoline[Int] = More(() => More(() => More(() => Done(42))))
    println("Simple trampoline example:")
    println(TrampolineInterpreter.run(tramExample))

    println("isEven? trampoline example:")
    println(TrampolineInterpreter.run(even((0 to 100).toList)))

    println("isEven? with embedded interpreter")
    println(even((0 to 100).toList).run)
  }

  def even[A](lst: Seq[A]): Trampoline[Boolean] = {
    lst match {
      case Nil => Done(true)
      case x :: xs => More(() => odd(xs))
    }
  }

  def odd[A](lst: Seq[A]): Trampoline[Boolean] = {
    lst match {
      case Nil => Done(false)
      case x :: xs => More(() => even(xs))
    }
  }

}
