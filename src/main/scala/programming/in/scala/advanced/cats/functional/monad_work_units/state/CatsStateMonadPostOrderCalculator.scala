package programming.in.scala.advanced.cats.functional.monad_work_units.state

import cats.data.State

object CatsStateMonadPostOrderCalculator {

  /*
  The State monad allows us to implement simple interpreters for complex expressions, passing the values of mutable
  registers along with the result. We can see a simple example of this by implementing a calculator for
  post-order integer arithmetic expressions.

  In case you haven’t heard of post-order expressions before (don’t worry if you haven’t), they are a mathematical
  notation where we write the operator after its operands. So, for example, instead of writing 1 + 2 we would write:
    1 2 +

  Although post-order expressions are difficult for humans to read, they are easy to evaluate in code.
  All we need to do is traverse the symbols from left to right, carrying a stack of operands with us as we go:
     - when we see a number, we push it onto the stack;
     - when we see an operator, we pop two operands off the stack, operate on them, and push the result in their place.

   This allows us to evaluate complex expressions without using parentheses.
   For example, we can evaluate (1 + 2) * 3) as follows:
    1 2 + 3 * // see 1, push onto stack
    2 + 3 *   // see 2, push onto stack
    + 3 *     // see +, pop 1 and 2 off of stack,
              //        push (1 + 2) = 3 in their place
    3 3 *     // see 3, push onto stack
    3 *       // see 3, push onto stack
    *         // see *, pop 3 and 3 off of stack,
              //        push (3 * 3) = 9 in their place
   */

  def main(args: Array[String]): Unit = {
    /*
    Let’s write an interpreter for these expressions. We can parse each symbol into a State instance
    representing a transformation on the stack and an intermediate result. The State instances
    can be threaded together using flatMap to produce an interpreter for any sequence of symbols.
     */

    //get the result, ignore the state
    println(s"evalOne('42') = ${evalOne("42").runA(Nil).value}")

    val program = for {
      _   <- evalOne("1")
      _   <- evalOne("2")
      ans <- evalOne("+")
    } yield ans
    println(s"program(1 2 +) = ${program.runA(Nil).value}")

    val program2 = evalAll(List("1", "2", "+", "3", "*"))
    println(s"program(List(1, 2, +, 3, *)) = ${program2.runA(Nil).value}")

    /*
    Because evalOne and evalAll both return instances of State, we can thread these results together using flatMap.
    evalOne produces a simple stack transformation and evalAll produces a complex one, but they’re both
    pure functions and we can use them in any order as many times as we like:
     */
    val program3 = for {
      _   <- evalAll(List("1", "2", "+"))
      _   <- evalAll(List("3", "4", "+"))
      ans <- evalOne("*")
    } yield ans
    println(s"program(for...) = ${program3.runA(Nil).value}")

    evalInput("1 2 + 3 4 + *")
  }

  type CalcState[A] = State[List[Int], A]

  def evalInput(input: String): Int = evalAll(input.split(" ").toList).runA(Nil).value

  import cats.syntax.applicative._ // for pure
  def evalAll(input: List[String]): CalcState[Int] = {
    input.foldLeft(0.pure[CalcState]) { (state, sym) =>
      state.flatMap(_ => evalOne(sym))
    }
  }

  def evalOne(sym: String): CalcState[Int] = sym match {
    case "+" => operator(_ + _)
    case "-" => operator(_ - _)
    case "*" => operator(_ * _)
    case "/" => operator(_ / _)
    case num => operand(num.toInt)
  }

  def operand(num: Int): CalcState[Int] =
    State[List[Int], Int] { stack =>
      (num :: stack, num)
    }

  def operator(func: (Int, Int) => Int): CalcState[Int] =
    State[List[Int], Int] {
      case b :: a :: tail =>
        val ans = func(a, b)
        (ans :: tail, ans)
      case _ => sys.error("Fail!")
    }
}
