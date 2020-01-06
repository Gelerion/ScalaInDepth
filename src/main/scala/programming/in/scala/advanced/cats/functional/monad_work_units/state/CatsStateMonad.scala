package programming.in.scala.advanced.cats.functional.monad_work_units.state

import cats.data.State

object CatsStateMonad {

  val printState: State[Int, String] = State { state =>
    (state, s"The state is $state")
  }

  def main(args: Array[String]): Unit = {
    // We can “run” our monad by supplying an initial state.
    // State provides three methods—
    //  - run
    //  - runS
    //  - runA
    // that return different combinations of state and result.

    // Each method returns an instance of Eval, which State uses to maintain stack safety.
    // We call the value method as usual to extract the actual result

    // Get the state and the result:
    println(s"run -> (state, result) == ${printState.run(10).value}") //(10,The state is 10)
    // Get the state, ignore the result:
    println(s"runS -> (state) == ${printState.runS(10).value}") //10
    // Get the result, ignore the state:
    println(s"runA -> (result) == ${printState.runA(10).value}") //The state is 10

    println(s"Program: ${program.run(1).value}") //(3,(1,2,3000))
  }

  import State._
  def program: State[Int, (Int, Int, Int)] = for {
    a <- get[Int]
    _ <- set[Int](a + 1)
    b <- get[Int]
    _ <- modify[Int](_ + 1)
    c <- inspect[Int, Int](_ * 1000)
  } yield (a, b, c)

  def composingState(): Unit = {
    val step1 = State[Int, String] { num =>
      val ans = num + 1
      (ans, s"Result of step1: $ans")
    }

    val step2 = State[Int, String] { num =>
      val ans = num * 2
      (ans, s"Result of step2: $ans")
    }


    //desugared = step1.flatMap(a => step2.map(b => (a, b)))
    val both = for {
      a <- step1
      b <- step2
    } yield (a, b)

    val (state, result) = both.run(20).value
    println(s"State: $state") //42
    println(s"Result: $result") //(Result of step1: 21, Result of step2: 42)
  }

  def transformingState(): Unit = {
    /*
     - get extracts the state as the result;
     - set updates the state and returns unit as the result;
     - pure ignores the state and returns a supplied result;
     - inspect extracts the state via a transformation function;
     - modify updates the state using an update function.
     */

    val getDemo: State[Int, Int] = State.get[Int]
    getDemo.run(10).value // (Int, Int) = (10,10)

    val setDemo: State[Int, Unit] = State.set[Int](30)
    setDemo.run(10).value // (Int, Unit) = (30,())

    val pureDemo: State[Int, String] = State.pure[Int, String]("Result")
    pureDemo.run(10).value // (Int, String) = (10,Result)

    val inspectDemo: State[Int, String] = State.inspect[Int, String](_ + "!")
    inspectDemo.run(10).value // (Int, String) = (10,10!)

    val modifyDemo: State[Int, Unit] = State.modify[Int](_ + 1)
    modifyDemo.run(10).value // (Int, Unit) = (11,())
  }

}
