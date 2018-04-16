package programming.in.scala.chapter_21

/**
  * Created by denis.shuvalov on 16/04/2018.
  *
  * It can happen that multiple implicit conversions are in scope and each would work. For the most part,
  * Scala refuses to insert a conversion in such a case. Implicits work well when the conversion left out is completely
  * obvious and pure boilerplate. If multiple conversions apply, then the choice isn't so obvious after all.
  */
object MultipleConversionsApply extends App {
  /*
  The ambiguity here is real. Converting an integer to a sequence of digits is completely different from converting
  it to a range. In this case, the programmer should specify which one is intended and be explicit. Up through Scala 2.7,
  that was the end of the story. Whenever multiple implicit conversions applied, the compiler refused to choose between them.
  The situation was just as with method overloading. If you try to call foo(null) and there are two different foo overloads
  that accept null, the compiler will refuse. It will say that the method call's target is ambiguous.

  Scala 2.8 loosened this rule. If one of the available conversions is strictly more specific than the others, then the
  compiler will choose the more specific one. The idea is that whenever there is a reason to believe a programmer would
  always choose one of the conversions over the others, don't require the programmer to write it explicitly. After all,
  method overloading has the same relaxation. Continuing the previous example, if one of the available foo methods takes
  a String while the other takes an Any, then choose the String version. It's clearly more specific.

  To be more precise, one implicit conversion is more specific than another if one of the following applies:
   - The argument type of the former is a subtype of the latter's.
   - Both conversions are methods, and the enclosing class of the former extends the enclosing class of the latter.
  The motivation to revisit this issue and revise the rule was to improve interoperation between Java collections,
  Scala collections, and strings.
   */
  val cba = "abc".reverse
  //val cba = Predef.augmentString("abc").reverse

  //What is the type inferred for cba? Intuitively, the type should be String. Reversing a string should yield another
  //string, right? However, in Scala 2.7, what happened was that "abc" was converted to a Scala collection. Reversing
  //a Scala collection yields a Scala collection, so the type of cba would be a collection. There's also an implicit
  //conversion back to a string, but that didn't patch up every problem. For example, in versions prior
  //to Scala 2.8, "abc" == "abc".reverse.reverse was false!

  //With Scala 2.8, the type of cba is String. The old implicit conversion to a Scala collection (now named WrappedString)
  //is retained. However, there is a more specific conversion supplied from String to a new type called StringOps.
  //StringOps has many methods such as reverse, but instead of returning a collection, they return a String.
  //The conversion to StringOps is defined directly in Predef, whereas the conversion to a Scala collection is defined
  //in a new class, LowPriorityImplicits, which is extended by Predef. Whenever a choice exists between these two conversions,
  //the compiler chooses the conversion to StringOps, because it's defined in a subclass of the class where the other
  //conversion is defined.
}

class Example {
  //There is a method that takes a sequence, a conversion that turns an integer into a range,
  //and a conversion that turns an integer into a list of digits:
  def printLength(seq: Seq[Int]) = println(seq.length)

  /*
    implicit def intToRange(i: Int) = 1 to i
    implicit def intToDigits(i: Int) = i.toString.toList.map(_.toInt)

    printLength(12)*/
  /*
  error: type mismatch;
     found   : Int(12)
     required: Seq[Int]
    Note that implicit conversions are not applicable because
    they are ambiguous
   */
}
