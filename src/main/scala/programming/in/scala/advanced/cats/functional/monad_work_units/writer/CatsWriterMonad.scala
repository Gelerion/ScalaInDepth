package programming.in.scala.advanced.cats.functional.monad_work_units.writer

import cats.data.Writer
import cats.instances.vector._ // for Monoid

object CatsWriterMonad {
  //cats.data.Writer is a monad that lets us carry a log along with a computation.
  def main(args: Array[String]): Unit = {
    Writer(Vector("It was the best of times", "it was the worst of times"), 1859)
    //type -> WriterT[Id, Vector[String], Int]

    //Cats implements Writer in terms of another type, WriterT.
    //WriterT is an example of a new concept called a monad transformer
  }

}
