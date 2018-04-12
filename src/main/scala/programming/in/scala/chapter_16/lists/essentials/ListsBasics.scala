package programming.in.scala.chapter_16.lists.essentials

/**
  * Created by denis.shuvalov on 26/03/2018.
  */
class ListsBasics {
  //Lists are quite similar to arrays, but there are two important differences. First, lists are immutable.
  //That is, elements of a list cannot be changed by assignment. Second, lists have a recursive structure
  //(i.e., a linked list), whereas arrays are flat.
  val fruit = List("apples", "oranges", "pears")
  val nums = List(1, 2, 3, 4)
  val diag3 =
    List(
      List(1, 0, 0),
      List(0, 1, 0),
      List(0, 0, 1)
    )
  val empty: List[Nothing] = List()

  //Like arrays, lists are homogeneous: the elements of a list all have the same type.
  //The type of a list that has elements of type T is written List[T].

  //!!
  //The list type in Scala is covariant. This means that for each pair of types S and T, if S is a subtype of T,
  //then List[S] is a subtype of List[T]. For instance, List[String] is a subtype of List[Object]. This is natural
  //because every list of strings can also be seen as a list of objects

  // -- Constructing lists
  //All lists are built from two fundamental building blocks, Nil and :: (pronounced "cons").
  //Nil represents the empty list. The infix operator, ::, expresses list extension at the front. That
  //is, x :: xs represents a list whose first element is x, followed by (the elements of) list xs.
  //Hence, the previous list values could also have been defined as follows:
  val fruit1 = "apples" :: ("orange" :: ("pears" :: Nil))
  val nums1  = 1 :: (2 :: (3 :: (4 :: Nil)))
  val diag3_1 = (1 :: (0 :: (0 :: Nil))) ::
                (0 :: (1 :: (0 :: Nil))) ::
                (0 :: (0 :: (1 :: Nil))) :: Nil
  val empty1 = Nil
  //Because it ends in a colon, the :: operation associates to the right: A :: B :: C is interpreted as A :: (B :: C)

  // -- Basic ops
//   head	returns the first element of a list
//   tail	returns a list consisting of all elements except the first
//   isEmpty	returns true if the list is empty

  // -- Patterns
  //The pattern List(a, b, c) matches lists of length 3, and binds the three elements to the pattern variables a, b, and c.
  val List(a, b, c) = fruit
//  a: String = apples
//  b: String = oranges
//  c: String = pears

  //If you don't know the number of list elements beforehand, it's better to match with :: instead. For instance,
  //the pattern a :: b :: rest matches lists of length 2 or greater:
  val d :: e :: rest = fruit
//  d: String = apples
//  e: String = oranges
//  rest: List[String] = List(pears)
}
