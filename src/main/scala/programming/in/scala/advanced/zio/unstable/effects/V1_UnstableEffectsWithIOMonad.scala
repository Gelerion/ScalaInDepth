package programming.in.scala.advanced.zio.unstable.effects

import scala.io.StdIn

object V1_UnstableEffectsWithIOMonad {
  /*
  Functional programming ordinarily gives us the incredible ability to easily test our software.

  The reason for this is quite simple: in functional programming, all a function does is map its input to some output.
  Functions are total (they return an output for every input), deterministic (they return the same output for the same input),
  and free of side effects (they only compute the return value, and don’t interact with the outside world).

  Surprisingly to many, these properties also hold for functions that returns functional effects.
  A functional effect, it turns out, is just an immutable data structure that describes an effect, without actually executing it.

  Functional programs construct and compose these data structures together using operations like map and flatMap,
  resulting in a data structure that \models\ the entire effectful application. Then in the application’s main function,
  the data structure is translated, step-by-step, into the effectful operations that it describes.

  The simplest way to build a functional effect is to describe an effect by using a data structure to store a thunk
  (a Function0 in Scala’s terminology) that holds an arbitrary hunk of effectful Scala code.
   */

  //Here’s a data type called IO which does exactly this:
  class IO[+A](val unsafeInterpret: () => A) { self =>
    def map[B](func: A => B) : IO[B] =
      flatMap(func.andThen(IO.effect(_)))
    def flatMap[B](func: A => IO[B]): IO[B] =
      IO.effect(func(self.unsafeInterpret())).unsafeInterpret()
  }

  object IO {
    def effect[A](eff: => A) = new IO(() => eff)
  }

  //Now we can construct pure functions that return functional effects (models of effects) quite simply:
  def putStrLn(line: String): IO[Unit] = IO.effect(println(line))
  val getStrLn: IO[String] = IO.effect(StdIn.readLine())
  //These functions are total, deterministic, and free of side effects, because they don’t do anything effectful,
  //they merely build a data structure that describes effectful operations.

  //Using map and flatMap, we can build describes of whole effectful programs.
  //For example, the following IO program asks the user for some input and prints it back out to them:
  val program: IO[String] =
  for {
    _    <- putStrLn("Good morning, what's your name?")
    name <- getStrLn
    _    <- putStrLn(s"Great to meet you, $name")
  } yield name

  def main(args: Array[String]): Unit = {
    //Now if you evaluate program in the Scala REPL, you’ll find that it doesn’t actually do anything except
    //construct an IO value, which is itself an immutable data structure.
    //However, you can (non-functionally) interpret this program to the effects that it describes by calling
    //the unsafeInterpret() function:
    program.unsafeInterpret()
    /*
    In this way, while we can’t avoid doing something “non-functional” forever, we can at least make the vast
    majority of our code purely functional, and benefit from increased power of abstraction, refactoring, and testability.

    Well, in theory. There’s a big problem with testability.

    In our tests, we need to call functions and verify their outputs match our expectations. Unfortunately,
    IO values, like the above program value, cannot be compared to other IO values. The reason is that they embed
    arbitrary hunks of Scala code inside them (functions), and Scala functions cannot be compared for equality.

    Although Scala functions do have equals and hashCode, like all ojbects, these do not have meaningful implementations;
    they are not based on what the function does, but rather, based on the reference of the constructed object.

    > putStrLn("Hello") == putStrLn("Hello")
    : false

    Even though both of these IO values represent the same program, Scala cannot know that, because functions cannot
    be sensibly compared for equality. This is not just a limitation of Scala, but rather a fundamental limitation of computation:
    in Turing complete languages, we cannot know for sure if two functions are equal, even if we look at their implementations.

    This means that while functional effect systems do provide us lots of concrete, tangible benefits
    (asynchronicity, concurrency, resource-safety, etc.), and while they do give us increased powers of abstraction
    and refactoring, they don’t make it any easier to test effectful code.
     */
  }
}


