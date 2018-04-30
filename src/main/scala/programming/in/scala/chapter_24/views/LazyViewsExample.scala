package programming.in.scala.chapter_24.views

/**
  * Created by denis.shuvalov on 30/04/2018.
  */
object LazyViewsExample {
  /*
  Collections have quite a few methods that construct new collections. Some examples are map, filter, and ++. We call
  such methods transformers because they take at least one collection as their receiver object and produce another
  collection in their result.

  Transformers can be implemented in two principal ways: strict and non-strict (or lazy). A strict transformer
  constructs a new collection with all of its elements. A non-strict, or lazy, transformer constructs only a proxy for
  the result collection, and its elements are constructed on demand.
   */

  //As an example of a non-strict transformer, consider the following implementation of a lazy map operation:
  def lazyMap[T, U](coll: Iterable[T], f: T => U) =
    new Iterable[U] {
      override def iterator: Iterator[U] = coll.iterator map f
    }
  //Note that lazyMap constructs a new Iterable without stepping through all elements of the given collection coll.
  //The given function f is instead applied to the elements of the new collection's iterator as they are demanded.

  /*
  Scala collections are by default strict in all their transformers, except for Stream, which implements all its
  transformer methods lazily. However, there is a systematic way to turn every collection into a lazy one and vice versa,
  which is based on collection views. A view is a special kind of collection that represents some base collection, but
  implements all of its transformers lazily.
   */

  //To go from a collection to its view, you can use the view method on the collection. If xs is some collection,
  //then xs.view is the same collection, but with all transformers implemented lazily. To get back from a view to a
  //strict collection, you can use the force method.

  val v = Vector(1 to 10: _*) //Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  v map (_ + 1) map (_ * 2)
  /*
  In the last statement, the expression v map (_ + 1) constructs a new vector that is then transformed into a third
  vector by the second call to map (_ * 2). In many situations, constructing the intermediate result from the first
  call to map is a bit wasteful. In the pseudo example, it would be faster to do a single map with the composition of
  the two functions (_ + 1) and (_ * 2). If you have the two functions available in the same place you can do this by hand.
  But quite often, successive transformations of a data structure are done in different program modules. Fusing those
  transformations would then undermine modularity. A more general way to avoid the intermediate results is by turning
  the vector first into a view, applying all transformations to the view, and finally forcing the view to a vector:
   */

  (v.view map (_ + 1) map (_ * 2)).force //Seq[Int] = Vector(4, 6, 8, 10, 12, 14, 16, 18, 20, 22)
  //1. val vv = v.view scala.collection.SeqView[Int,Vector[Int]] = SeqView(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  //The type SeqView has two type parameters. The first, Int, shows the type of the view's elements. The second,
  //Vector[Int], shows you the type constructor you get back when forcing the view.

  //2. vv map (_ + 1) //SeqView[Int,Seq[_]] = SeqViewM(...)
  //The result of the map is a value that prints SeqViewM(...). This is in essence a wrapper that records the fact that
  //a map with function (_ + 1) needs to be applied on the vector v. It does not apply that map until the view is forced,
  //however. The "M" after SeqView is an indication that the view encapsulates a map operation. Other letters indicate
  //other delayed operations. For instance "S" indicates a delayed slice operation, and "R" indicates a reverse.
  //We'll now apply the second map to the last result.

  //3. res13 map (_ * 2) //SeqView[Int,Seq[_]] = SeqViewMM(...)

  //4. res14.force
  //Both stored functions get applied as part of the execution of the force operation and a new vector is constructed.
  //That way, no intermediate data structure is needed.

  /*
  One detail to note is that the static type of the final result is a Seq, not a Vector. Tracing the types back we see
  that as soon as the first delayed map was applied, the result had static type SeqViewM[Int, Seq[_]]. That is, the
  "knowledge" that the view was applied to the specific sequence type Vector got lost. The implementation of a view for
  any particular class requires quite a bit of code, so the Scala collection libraries provide views mostly only for
  general collection types, not for specific implementations.
   */
}
