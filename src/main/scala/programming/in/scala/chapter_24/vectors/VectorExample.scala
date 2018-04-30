package programming.in.scala.chapter_24.vectors

/**
  * Created by denis.shuvalov on 30/04/2018.
  *
  * Vectors are a collection type that give efficient access to elements beyond the head. Access to any elements of a
  * vector take only "effectively constant time," as defined below. It's a larger constant than for access to the head
  * of a list or for reading an element of an array, but it's a constant nonetheless. As a result, algorithms using
  * vectors do not have to be careful about accessing just the head of the sequence. They can access and modify elements
  * at arbitrary locations, and thus they can be much more convenient to write.
  */
object VectorExample {
  val vec = scala.collection.immutable.Vector.empty

  val vec2 = vec :+ 1 :+ 2

  val vec3 = 100 +: vec2

  println(vec3(0)) //100

  /*
  Vectors are represented as broad, shallow trees. Every tree node contains up to 32 elements of the vector or contains
  up to 32 other tree nodes. Vectors with up to 32 elements can be represented in a single node. Vectors with up
  to 32 * 32 = 1024 elements can be represented with a single indirection. Two hops from the root of the tree to the
  final element node are sufficient for vectors with up to 215 elements, three hops for vectors with 220, four hops for
  vectors with 225 elements and five hops for vectors with up to 230 elements. So for all vectors of reasonable size, an
  element selection involves up to five primitive array selections. This is what we meant when we wrote that element
  access is "effectively constant time."
   */

  //Vectors are immutable, so you cannot change an element of a vector in place. However, with the updated method you
  //can create a new vector that differs from a given vector only in a single element:
  val vec4 = Vector(1, 2, 3)
  // a call to updated has no effect on the original vector vec
  vec4 updated (2, 4) //Vector(1, 2, 4)

  /*
  vector updates are also "effectively constant time." Updating an element in the middle of a vector can be done by
  copying the node that contains the element, and every node that points to it, starting from the root of the tree.
  This means that a functional update creates between one and five nodes that each contain up to 32 elements or subtrees.
  This is certainly more expensive than an in-place update in a mutable array, but still a lot cheaper than copying
  the whole vector.
   */
}
