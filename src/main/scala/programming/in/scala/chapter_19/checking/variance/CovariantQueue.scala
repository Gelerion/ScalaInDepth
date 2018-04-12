package programming.in.scala.chapter_19.checking.variance

/**
  * Created by denis.shuvalov on 09/04/2018.
  */
class CovariantQueue[+T] {
  //error: covariant type T occurs in contravariant position in type T of value x
  //  def enqueue(x: T): Unit = {
  //    Reassignable fields are a special case of the rule that disallows type parameter
  //    annotated with + from being used as method parameter types. As mentioned, a reassignable field,
  //    "var x: T", is treated in Scala as a getter method, "def x: T", and a setter method, "def x_=(y: T)".
  //    As you can see, the setter method has a parameter of the field's type T. So that type may not be covariant.
  //  }

  /*
     class StrangeIntQueue extends CovariantQueue[Int] {
      override def enqueue(x: Int) = {
        println(math.sqrt(x))
        super.enqueue(x)
      }
    }

    Now, you can write a counterexample in two lines:
      val x: Queue[Any] = new StrangeIntQueue
      x.enqueue("abc")

    Clearly it's not just mutable fields that make covariant types unsound. The problem is more general.
    It turns out that as soon as a generic parameter type appears as the type of a method parameter, the
    containing class or trait may not be covariant in that type parameter.

    For queues, the enqueue method violates this condition
   */
}
