package programming.in.scala.chapter_19.functional.queues

/**
  * Created by denis.shuvalov on 08/04/2018.
  *
  * Hiding a primary constructor by making it private.
  */
class PrivateConstructorQueue[T] private(
                                          private val leading: List[T],
                                          private val trailing: List[T]
                                        ) {
  /*
  The private modifier between the class name and its parameters indicates that the constructor of Queue is private:
  it can be accessed only from within the class itself and its companion object. The class name Queue is still public,
  so you can use it as a type, but you cannot call its constructor:
   */

  //Now that the primary constructor of class Queue can no longer be called from client code, there needs to be some
  //other way to create new queues. One possibility is to add an auxiliary constructor, like this:
  def this() = this(Nil, Nil)

  //The auxiliary constructor shown in the previous example builds an empty queue. As a refinement,
  // the auxiliary constructor could take a list of initial queue elements:
  //def this(elems: T*) = this(elems.toList, Nil)

}

//Another possibility is to add a factory method that builds a queue from such a sequence of initial elements.
object PrivateConstructorQueue {

  def apply[T](xs: T*) = new PrivateConstructorQueue(xs.toList, Nil)

}
