package programming.in.scala.chapter_32.async.programming.v1

import scala.concurrent.ExecutionContext.global

object CallbackHell {
  type Async[A] = (A => Unit) => Unit

  def main(args: Array[String]): Unit = {
    timesTwo(20) { result => println(s"Times two:\nResult $result") }

    //Looks simple now, but we are only combining two results, one after another.
    timesFour(20) { result => println(s"Times four:\nResult: $result") }


    //global is daemon
    Thread.sleep(100)
  }

  //Let's build an artificial example made to illustrate our challenges. Say we need to initiate
  //two asynchronous processes and combine their result.
  def timesTwo(n: Int): Async[Int] =
    onFinish => {
      global.execute(() => {
        val result = n * 2
        onFinish(result)
      })
    }

  //Sequencing (Purgatory of Side-effects)
  //Let's combine two asynchronous results, with the execution happening one after another, in a neat sequence:
  def timesFour(n: Int): Async[Int] =
    onFinish => {
      timesTwo(n) { a =>
        timesTwo(n) { b =>
          //combining the two results
          onFinish(a + b)
        }
      }
    }
  //Looks simple now, but we are only combining two results, one after another.

  /*
  The big problem however is that asynchrony infects everything it touches. Let's assume for the sake of
  argument that we start with a pure function:

     def timesFour(n: Int): Int = n * 4

  But then your enterprise architect, after hearing about these Enterprise JavaBeans and a lap dance, decides
  that you should depend on this asynchronous timesTwo function. And now our timesFour implementation changes
  from a pure mathematical function to a side-effectful one and we have no choice in the matter.
  And without a well grown Async type, we are forced to deal with side-effectful callbacks for the whole pipeline.
   */
}
