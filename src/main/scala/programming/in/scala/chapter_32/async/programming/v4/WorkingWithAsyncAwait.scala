package programming.in.scala.chapter_32.async.programming.v4

object WorkingWithAsyncAwait {
  //https://github.com/scala/scala-async

  /*
  import scala.async.Async.{async, await}

  def timesFour(n: Int)(implicit ec: ExecutionContext): Future[Int] =
    async {
      val a = await(timesTwo(a))
      val b = await(timesTwo(b))
      a + b
    }

   The scala-async library is powered by macros and will translate your code to something equivalent to
   flatMap and map calls. So in other words await does not block threads, even though it gives
   the illusion that it does.

   This looks great actually, unfortunately it has many limitations. The library cannot rewrite your code
   in case the await is inside an anonymous function and unfortunately Scala code is usually full of such expressions.
   This does not work:

   // BAD SAMPLE
  def sum(list: List[Future[Int]])(implicit ec; ExecutionContext): Future[Int] =
    async {
      var sum = 0
      // Nope, not going to work because "for" is translated to "foreach"
      for (f <- list) {
        sum += await(f)
      }
  }

  This approach gives the illusion of having first-class continuations, but these continuations are unfortunately
  not first class, being just a compiler-managed rewrite of the code. And yes, this restriction applies
  to C# and ECMAScript as well. Which is a pity, because it means async code will not be heavy on FP.
   */

}
