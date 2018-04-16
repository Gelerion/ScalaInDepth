package programming.in.scala.chapter_21

/**
  * Created by denis.shuvalov on 16/04/2018.
  *
  * Note that when you use implicit on a parameter, not only will the compiler try to supply that parameter with an
  * implicit value, but the compiler will also use that parameter as an available implicit in the body of the method!
  * Thus, the first use of ordering within the body of the method can be left out.
  */
object ContextBounds extends App {
  def maxList[T](elements: List[T])(implicit ordering: Ordering[T]): T =

    elements match {
      case List() => throw new IllegalArgumentException("empty list!")
      case List(x) => x
      case x :: rest =>
        val maxRest = maxList(rest)     // (ordering) is implicit
        if (ordering.gt(x, maxRest)) x  // this ordering is
        else maxRest                    // still explicit
    }

  //When the compiler examines the code it will see that the types do not match up.
  //The expression maxList(rest) only supplies one parameter list, but maxList requires two. Since the second
  //parameter list is implicit, the compiler does not give up type checking immediately. Instead, it looks for an
  //implicit parameter of the appropriate type, in this case Ordering[T]. In this case, it finds one and rewrites
  //the call to maxList(rest)(ordering), after which the code type checks.

  //There is also a way to eliminate the second use of ordering. It involves the following method defined in the standard library:

  //  def implicitly[T](implicit t: T) = t

  //The effect of calling implicitly[Foo] is that the compiler will look for an implicit definition of type Foo.
  //It will then call the implicitly method with that object, which in turn returns the object right back.
  //Thus you can write implicitly[Foo] whenever you want to find an implicit object of type Foo in the current scope.

  def maxListV2[T](elements: List[T])(implicit ordering: Ordering[T]): T =
    elements match {
      case List() => throw new IllegalArgumentException("empty list!")
      case List(x) => x
      case x :: rest =>
        val maxRest = maxList(rest)     // (ordering) is implicit
        if (implicitly[Ordering[T]].gt(x, maxRest)) x  // this ordering is
        else maxRest                    // still explicit
    }

  //For that matter, this version works as well:
  //
  //  def maxList[T](elements: List[T])
  //        (implicit iceCream: Ordering[T]): T = // same body...

  // -------------------------------------------------------------------------------------------
  //Because this pattern is common, Scala lets you leave out the name of this parameter and shorten the method header
  //by using a context bound. Using a context bound, you would write the signature of maxList[T : Ordering].
  //The syntax [T : Ordering] is a context bound, and it does two things. First, it introduces a type parameter T as normal.
  //Second, it adds an implicit parameter of type Ordering[T]. In previous versions of maxList, that parameter was called
  //ordering, but when using a context bound you don't know what the parameter will be called. As shown earlier,
  //you often don't need to know what the parameter is called.
  def maxListV3[T : Ordering](elements: List[T]): T =
    elements match {
      case List() => throw new IllegalArgumentException("empty list!")
      case List(x) => x
      case x :: rest =>
        val maxRest = maxList(rest)     // (ordering) is implicit
        if (implicitly[Ordering[T]].gt(x, maxRest)) x  // this ordering is
        else maxRest                    // still explicit
    }
}
