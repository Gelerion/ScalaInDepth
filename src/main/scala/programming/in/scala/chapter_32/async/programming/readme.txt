https://alexn.org/blog/2017/01/30/asynchronous-programming-scala.html

Just use Monix: https://monix.io/

As a concept it is more general than multithreading, although some people confuse the two.
If you're looking for a relationship, you could say:

   Multithreading <: Asynchrony

We can represent asynchronous computations with a type:

   type Async[A] = (Try[A] => Unit) => Unit

If it looks ugly with those Unit return types, that's because asynchrony is ugly. An asynchronous computation is any task,
thread, process, node somewhere on the network that:
  1. executes outside of your program's main flow or from the point of view of the caller,
     it doesn't execute on the current call-stack
  2. receives a callback that will get called once the result is finished processing
  3. it provides no guarantee about when the result is signaled, no guarantee that a result will be signaled at all

It's important to note asynchrony subsumes concurrency, but not necessarily multithreading. Remember that in
Javascript the majority of all I/O actions (input or output) are asynchronous and even heavy business logic is
 made asynchronous (with setTimeout based scheduling) in order to keep the interface responsive. But no kernel-level
 multithreading is involved, Javascript being an N:1 multithreaded platform.

Introducing asynchrony into your program means you'll have concurrency problems because you never know when asynchronous
computations will be finished, so composing the results of multiple asynchronous computations running at the same time
means you have to do synchronization, as you can no longer rely on ordering. And not relying on an order is a recipe
for nondeterminism.

  === The Big Illusion ===
We like to pretend that we can describe functions that can convert asynchronous results to synchronous ones:

   def await[A](fa: Async[A]): A

Fact of the matter is that we can't pretend that asynchronous processes are equivalent with normal functions.
If you need a lesson in history for why we can't pretend that, you only need to take a look at why CORBA failed.

With asynchronous processes we have the following very common fallacies of distributed computing:
 1. The network is reliable
 2. Latency is zero
 3. Bandwidth is infinite
 4. The network is secure
 5. Topology doesn't change
 6. There is one administrator
 7. Transport cost is zero
 8. The network is homogeneous

None of them are true of course. Which means code gets written with little error handling for network failures,
ignorance of network latency or packet loss, ignorance of bandwidth limits and in general ignorance of the ensuing nondeterminism.

People have tried to cope with this by:
 * callbacks, callbacks everywhere, equivalent to basically ignoring the problem, as it happens in Javascript,
   which leads to the well known effect of callback hell, paid for with the sweat and blood of programmers that constantly
   imagine having chosen a different life path

 * blocking threads, on top of 1:1 (kernel-level) multithreading platforms

 * first-class continuations, implemented for example by Scheme in call/cc, being the ability to save the execution
   state at any point and return to that point at a later point in the program

 * the async / await language extension from C#, also implemented in the scala-async library and in the latest ECMAScript

  * green threads managed by the runtime, possibly in combination with M:N multithreading, to simulate blocking for
    asynchronous actions; examples including Golang but also Haskell

  * the actor model as implemented in Erlang or Akka, or CSP such as in Clojure's core.async or in Golang

  * monads being used for ordering and composition, such as Haskell's Async type in combination with the IO type,
    or F# asynchronous workflows, or Scala's Futures and Promises, or the Monix Task or the Scalaz Task, etc, etc.

If there are so many solutions, that's because none of them is suitable as a general purpose mechanism for dealing
with asynchrony. The no silver bullet dilemma is relevant here, with memory management and concurrency being the
biggest problems that we face as software developers.

