package programming.in.scala.chapter_16.lists

import programming.in.scala.chapter_16.lists.essentials.concatenation.CustomListFunc.msort

/**
  * Created by denis.shuvalov on 01/04/2018.
  */
class ScalaTypeInference {
  //One difference between the previous uses of sortWith and msort concerns the admissible syntactic
  //forms of the comparison function.
  //  msort((x: Char, y: Char) => x > y)(abcde)

  //  abcde sortWith (_ > _) // List[Char] = List(e, d, c, b, a)

  //However, the short form cannot be used with msort.
  //  msort(_ > _)(abcde) -> error: missing parameter type for expanded

  //To understand why, you need to know some details of Scala's type inference algorithm.
  //Type inference in Scala is flow based. In a method application m(args),
  // 1. the inferencer first checks whether the method m has a known type.
  //    If it does, that type is used to infer the expected type of the arguments

  //For instance, in abcde.sortWith(_ > _), the type of abcde is List[Char]. Hence, sortWith is known to be a method
  //that takes an argument of type (Char, Char) => Boolean and produces a result of type List[Char]
  //Since the parameter types of the function arguments are known, they need not be written explicitly. With what it
  //knows about sortWith, the inferencer can deduce that (_ > _) should expand to ((x: Char, y: Char) => x > y)
  //where x and y are some arbitrary fresh names.

  //Now consider the second case, msort(_ > _)(abcde). The type of msort is a curried, polymorphic method type that
  //takes an argument of type (T, T) => Boolean to a function from List[T] to List[T] where T is some as-yet unknown type.
  //The msort method needs to be instantiated with a type parameter before it can be applied to its arguments.

  // 2. Because the precise instance type of msort in the application is not yet known, it cannot be used to infer
  //    the type of its first argument. The type inferencer changes its strategy in this case; it first type checks
  //    method arguments to determine the proper instance type of the method. However, when tasked to type check the
  //    short-hand function literal, (_ > _), it fails because it has no information about the types of the implicit
  //    function parameters that are indicated by underscores.

  //One way to resolve the problem is to pass an explicit type parameter to msort, as in:
  //  msort[Char](_ > _)
  //Because the correct instance type of msort is now known, it can be used to infer the type of the arguments.

  //Another possible solution is to rewrite the msort method so that its parameters are swapped
  def msortSwapped[T](xs: List[T])(less: (T, T) => Boolean) = {
    // same implementation as msort
  }

  //What has happened is that the inferencer used the known type of the first parameter abcde to determine the type
  //parameter of msortSwapped. Once the precise type of msortSwapped was known, it could be used in turn to infer the
  //type of the second parameter, (_ > _)

  /*
  Generally, when tasked to infer the type parameters of a polymorphic method, the type inferencer consults the types
  of all value arguments in the first parameter list but no arguments beyond that. Since msortSwapped is a curried
  method with two parameter lists, the second argument (i.e., the function value) did not need to be consulted to
  determine the type parameter of the method.
   */

  /*
  Now to the more complicated case of a fold operation. Why is there the need for an explicit type parameter in an expression like the body of the flattenRight method shown here?

  (xss :~List[T]()) (_ ::: _)
  The type of the fold-right operation is polymorphic in two type variables. Given an expression:

  (xs :~z) (op)
  The type of xs must be a list of some arbitrary type A, say xs: List[A]. The start value z can be of some other type B.
  The operation op must then take two arguments of type A and B, and return a result of type B, i.e., op: (A, B) => B.
  Because the type of z is not related to the type of the list xs, type inference has no context information for z.

  Now consider the expression in the erroneous version of flattenRight, also shown here:

  (xss :~List()) (_ ::: _)  // this won't compile
  The start value z in this fold is an empty list, List(), so without additional type information its type is inferred
  to be a List[Nothing]. Hence, the inferencer will infer that the B type of the fold is List[Nothing]. Therefore,
  the operation (_ ::: _) of the fold is expected to be of the following type:

  (List[T], List[Nothing]) => List[Nothing]
  This is indeed a possible type for the operation in that fold but it is not a very useful one! It says that the
  operation always takes an empty list as second argument and always produces an empty list as result.

  In other words, the type inference settled too early on a type for List(); it should have waited until it had seen
  the type of the operation op. So the (otherwise very useful) rule to only consider the first argument section in a
  curried method application for determining the method's type is at the root of the problem here. On the other hand,
  even if that rule were relaxed, the inferencer still could not come up with a type for op because its parameter types
  are not given. Hence, there is a Catch-22 situation that can only be resolved by an explicit type annotation from the programmer.

  This example highlights some limitations of the local, flow-based type inference scheme of Scala. It is not present in
  the more global Hindley-Milner style of type inference used in functional languages, such as ML or Haskell. However,
  Scala's local type inference deals much more gracefully with object-oriented subtyping than the Hindley-Milner style
  does. Fortunately, the limitations show up only in some corner cases, and are usually easily fixed by adding an
  explicit type annotation.

  Adding type annotations is also a useful debugging technique when you get confused by type error messages related to
  polymorphic methods. If you are unsure what caused a particular type error, just add some type arguments or other
  type annotations, which you think are correct. Then you should be able to quickly see where the real problem is.
   */
}
