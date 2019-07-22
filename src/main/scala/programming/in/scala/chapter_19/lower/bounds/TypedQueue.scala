package programming.in.scala.chapter_19.lower.bounds

/**
  * Created by denis.shuvalov on 09/04/2018.
  */
class TypedQueue[+T](private val leading: List[T], private val trailing: List[T]) {

  //LOWER BOUNDS

  //there's a way to get unstuck: you can generalize enqueue by making it polymorphic (i.e., giving the enqueue
  //method itself a type parameter) and using a lower bound for its type parameter.
  // U must be supertype of T inclusive
  def enqueue[U >: T](x: U) = new TypedQueue[U](leading, x :: trailing)

  //The new definition gives enqueue a type parameter U, and with the syntax, "U >: T", defines T as the lower bound for U.
  //As a result, U is required to be a supertype of T.
  //The parameter to enqueue is now of type U instead of type T, and the return value of the method is now Queue[U] instead of Queue[T]

  def head = mirror.leading.head
  def tail = new TypedQueue(mirror.leading.tail, mirror.trailing)
  private def mirror =
    if(leading.isEmpty) new TypedQueue(trailing.reverse, Nil)
    else this

}

abstract class Fruit(name: String)
case class Apple(name: String = "apple") extends Fruit(name)
case class Orange(name: String = "orange") extends Fruit(name)

//For example, suppose there is a class Fruit with two subclasses, Apple and Orange. With the new definition of
//class Queue, it is possible to append an Orange to a Queue[Apple]. The result will be a Queue[Fruit].
//This revised definition of enqueue is type correct. Intuitively, if T is a more specific type than expected
//(for example, Apple instead of Fruit), a call to enqueue will still work because U (Fruit) will still be a supertype of T (Apple)
object TestCovariantQueue extends App {

  val queue: TypedQueue[Orange] = new TypedQueue[Orange](Nil, Nil)

  val oranges: TypedQueue[Orange] = queue.enqueue(new Orange)
  val fruits:  TypedQueue[Fruit]  = oranges.enqueue(new Apple)
  val fruits2 = fruits.enqueue(new Orange)

  println(fruits.head)
  println(fruits.tail.head)

  if(oranges.isInstanceOf[TypedQueue[Fruit]]) {
    println("TypedQueue[Orange] instance of TypedQueue[Fruit]")
  }

  /*
  This shows that variance annotations and lower bounds play well together. They are a good example of type-driven
  design, where the types of an interface guide its detailed design and implementation. In the case of queues, it's
  likely you would not have thought of the refined implementation of enqueue with a lower bound. But you might have
  decided to make queues covariant, in which case, the compiler would have pointed out the variance error for enqueue.
  Correcting the variance error by adding a lower bound makes enqueue more general and queues as a whole more usable.

  This observation is also the main reason that Scala prefers declaration-site variance over use-site variance as it
  is found in Java's wildcards. With use-site variance, you are on your own designing a class. It will be the clients
  of the class that need to put in the wildcards, and if they get it wrong, some important instance methods will no
  longer be applicable. Variance being a tricky business, users usually get it wrong, and they come away thinking that
  wildcards and generics are overly complicated. With definition-side variance, you express your intent to the compiler,
  and the compiler will double check that the methods you want available will indeed be available.
   */

}