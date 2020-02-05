package programming.in.scala.advanced.zio.unstable.effects

import programming.in.scala.advanced.zio.unstable.effects.V1_UnstableEffectsWithIOMonad.IO

import scala.io.StdIn

/*
  Massive Ramp-Up
  As demonstrated in this article, the tagless-final technique is not for the faint of heart. It requires advanced knowledge
  of the Scala programming language, functional programming, and how we model some functional constructs in Scala.

  In particular, to competently use tagless-final in all common scenarios, you will have to understand:
   1. Functional Effects.
   2. Parametric Polymorphism.
   3. Higher-kinded Types.
   4. Type Classes & their Scala encoding.
   5. Type Class Instances & their Scala encoding.
   6. Partial Type Application (AKA Type Lambdas).
   7. The Monad Hierarchy.

  These are not topics that one co-worker can casually introduce to another co-worker over a lunch break. It’s not
  possible to sneak tagless-final into a code base. Some combination of training and / or mentorship are required.
 */
object V2_TaglessFinal101 {
  /*
  In tagless-final, we often use type classes to model effects (although it’s possible to use records, this approach seems not very popular in Scala).

  So instead of interacting with putStrLn and getStrLn directly, we define a type class to describe console capabilities.
  The type class is parameterized over the effect type:
   */
  trait Console[F[_]] {
    def putStrLn(line: String): F[Unit]
    val getStrLn: F[String]
  }
  object Console {
    def apply[F[_]](implicit F: Console[F]): Console[F] = F
  }

  //Then we can define instances of this type class for IO:
  implicit val ConsoleIO: Console[IO] = new Console[IO] {
    def putStrLn(line: String): IO[Unit] = IO.effect(println(line))
    val getStrLn: IO[String] = IO.effect(StdIn.readLine())
  }

  import IOMonad._
  //Now we write programs that are polymorphic in the effect type, which express which capabilities they require
  //from the effect by using type class constraints (commonly modeled using context bounds, which desugar to implicit parameter lists):
  /*
   without syntax
   def program[F[_]: Console](implicit F: Monad[F]): F[String] = {
    F.flatMap(Console[F].putStrLn("Good morning, what's your name?"))(_ => {
      F.flatMap(Console[F].getStrLn)(name => {
        F.map(Console[F].putStrLn(s"Great to meet you, $name"))(_ => name)
      })
    })
  }
  */

  def program[F[_]: Console: Monad]: F[String] =
    for {
      _    <- Console[F].putStrLn("Good morning, what's your name?")
      name <- Console[F].getStrLn
      _    <- Console[F].putStrLn(s"Great to meet you, $name")
    } yield name


  //Once all this machinery is in place, it becomes fairly straightforward to define a data type just for testing:
  case class TestData(input: List[String], output: List[String])
  case class TestIO[A](run: TestData => (TestData, A)) { self =>
    def map[B](f: A => B): TestIO[B] = flatMap(a => TestIO.value(f(a)))
    def flatMap[B](f: A => TestIO[B]): TestIO[B] =
      TestIO(d => (self run d) match { case (d, a) => f(a) run d })
  }
  object TestIO {
    def value[A](a: => A): TestIO[A] = TestIO(d => (d, a))
  }

  //Once you define this and the Monad instance, you can instantiate the polymorphic program to the test effect:

  //val programTest: TestIO[String] = program[TestIO]

  //Finally, at long last testability has been regained: you can write fast, deterministic unit tests that thoroughly
  //test your application logic. Your CI builds will complete quickly and you can refactor with confidence.

  //Unfortunately, this benefit comes at considerable cost.


  def main(args: Array[String]): Unit = {
    val programIO: IO[String] = program[IO]
    programIO.unsafeInterpret()
  }

  //---------------------------------
  trait Monad[F[_]] extends {
    def pure[A](a: A): F[A]
    def map[A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(v => pure(f(v)))
    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  }

  class IOMonad extends Monad[IO] {
    def pure[A](a: A): IO[A] = IO.effect(a)
    def flatMap[A, B](fa: IO[A])(f: A => IO[B]): IO[B] = fa.flatMap(f)
    override def map[A, B](fa: IO[A])(f: A => B): IO[B] = fa.map(f)
  }

  object IOMonad {
    implicit def ioInstance: IOMonad = new IOMonad

    implicit class ioSyntax[F[_], A](private val fa: F[A]) {
      def map[B](f: A => B)(implicit M: Monad[F]): F[B] = M.map(fa)(f)
      def flatMap[B](f: A => F[B])(implicit M: Monad[F]): F[B] = M.flatMap(fa)(f)
    }
  }
}
