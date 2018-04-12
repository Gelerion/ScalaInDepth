package programming.in.scala.chapter_12

/**
  * Created by denis.shuvalov on 14/03/2018.
  *
  * It has the default superclass of AnyRef
  */
trait Philosophical {

  /*
   * difference between classes and traits is that whereas in classes, super calls are statically bound, in traits,
   * they are dynamically bound. If you write "super.toString" in a class, you know exactly which method implementation
   * will be invoked. When you write the same thing in a trait, however, the method implementation to invoke for the
   * super call is undefined when you define the trait. Rather, the implementation to invoke will be determined anew
   * each time the trait is mixed into a concrete class. This curious behavior of super is key to allowing traits to
   * work as stackable modifications
   */

  def philosophize() = {
    println("I consume memory, therefore I am!")
  }

}
