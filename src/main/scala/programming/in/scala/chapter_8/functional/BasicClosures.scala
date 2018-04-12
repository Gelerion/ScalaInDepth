package programming.in.scala.chapter_8.functional

/**
  * Created by denis.shuvalov on 08/03/2018.
  */
object BasicClosures extends App {
  // (x: Int) => x + more  // how much more?
  //From the point of view of this function, more is a free variable because the function literal does not itself give a meaning to it
  //The x variable, by contrast, is a bound variable because it does have a meaning in the context of the function:
  //it is defined as the function's lone parameter, an Int.

  //The function value (the object) that's created at runtime from this function literal is called a closure.
  //The name arises from the act of "closing" the function literal by "capturing" the bindings of its free variables.
  var more = 1
  val addMore = (x: Int) => x + more
  addMore(10) // 11

  // * A function literal with no free variables, such as (x: Int) => x + 1, is called a closed term,
  //   where a term is a bit of source code
  //   Thus a function value created at runtime from this function literal is not a closure in the strictest sense,
  //   because (x: Int) => x + 1 is already closed as written.

  // * Any function literal with free variables, such as (x: Int) => x + more, is an open term.
  //   Therefore, any function value created at runtime from (x: Int) => x + more will, by definition, require
  //   that a binding for its free variable, more, be captured

}
