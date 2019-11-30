package programming.in.scala.advanced.cats.functional.monad

import cats.Eval

object EvalMonad {

  def main(args: Array[String]): Unit = {
    val greeting = Eval.always { println("Step 1"); "Hello" }
      .map { str => println("Step 2"); s"$str world" }

    println(greeting.value)
    //Step 1
    //Step 2
    //Hello world

    val saying = Eval.
      always { println("Step 1"); "The cat" }.
      map { str => println("Step 2"); s"$str sat on" }.
      memoize.
      map { str => println("Step 3"); s"$str the mat" }

    println(s"First access ${saying.value}")
    //Step 1
    //Step 2
    //Step 3
    //First access: The cat sat on the mat
    println(s"Second access ${saying.value}")
    //Step 3
    //Second access: The cat sat on the mat


    // === Stack save recursion
    println(foldRightNotSafe(List(1, 2, 3, 4, 5), 0)((a, b) => a + b))
    println(foldRightSafe(List(1, 2, 3, 4, 5), 0)((a, b) => a + b).value)
  }

  def factorialStackSafe(n: Int): Eval[Int] = {
    if (n == 1) Eval.now(n)
    else Eval.defer(factorialStackSafe(n - 1).map(_ * n))
  }


  def factorialBlowsStack(n: Int): Int = {
    if (n == 1) n
    else n * factorialBlowsStack(n - 1)
  }

  /**
    * Oops! That didn’t work—our stack still blew up! This is because we’re still making all the
    * recursive calls to factorial before we start working with Eval's map method
    */
  def factorialWithEvalBlowsStack(n: Int): Eval[Int] = {
    if (n == 1) Eval.now(n)
    else factorialWithEvalBlowsStack(n - 1).map(_ * n)
  }

  def foldRightNotSafe[A, B](as: List[A], acc: B)
                            (fn: (A, B) => B): B = {
    as match {
      case head :: tail => fn(head, foldRightNotSafe(tail, acc)(fn))
      case Nil => acc
    }
  }

  def foldRightSafe[A, B](as: List[A], acc: B)(fn: (A, B) => B): Eval[B] = {
    as match {
      case Nil => Eval.now(acc)
      case head :: tail => Eval.defer(foldRightSafe(tail, acc)(fn).map(b => fn(head, b)))
    }
  }
}
