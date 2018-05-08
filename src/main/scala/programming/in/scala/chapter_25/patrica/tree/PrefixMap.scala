package programming.in.scala.chapter_25.patrica.tree

import collection.{mutable, _}
import scala.collection.generic.CanBuildFrom

/**
  * Created by denis.shuvalov on 06/05/2018.
  *
  * For instance, a Patricia trie storing the five strings, "abc", "abd", "al", "all", "xy".
  *
  * To find the node corresponding to the string "abc" in this trie, simply follow the subtree labeled "a", proceed
  * from there to the subtree labeled "b" to finally reach its subtree labeled "c". If the Patricia trie is used as
  * a map, the value that's associated with a key is stored in the nodes that can be reached by the key. If it is a
  * set, you simply store a marker saying that the node is present in the set.
  */
class PrefixMap[T]
  extends mutable.Map[String, T] with mutable.MapLike[String, T, PrefixMap[T]] {

  //immutable maps that contain only a few elements are very efficient in both space and execution time
  var suffixes: immutable.Map[Char, PrefixMap[T]] = Map.empty
  var value: Option[T] = None

  override def get(key: String): Option[T] = {
    if (key.isEmpty) value
    else suffixes.get(key(0)).flatMap(subtree => subtree.get(key substring 1))
  }

  def withPrefix(prefix: String): PrefixMap[T] = {
    if (prefix.isEmpty) this
    else {
      val leading = prefix(0)
      suffixes get leading match {
        case None => suffixes += (leading -> empty)
        case _ =>
      }

      suffixes(leading) withPrefix (prefix substring 1)
    }
  }

  override def update(s: String, elem: T) = withPrefix(s).value = Some(elem)

  override def remove(s: String): Option[T] =
    if (s.isEmpty) { val prev = value; value = None; prev }
    else suffixes get s(0) flatMap (_.remove(s substring 1))

  override def iterator: Iterator[(String, T)] = {
    (for (v <- value.iterator) yield ("", v)) ++
     (for ((chr, m) <- suffixes.iterator; (s, v) <- m.iterator) yield (chr +: s, v))
  }

  override def +=(kv: (String, T)): this.type = {
    update(kv._1, kv._2)
    this
  }

  override def -=(key: String): this.type = {
    remove(key)
    this
  }

  override def empty = new PrefixMap[T]
}

//it is not strictly necessary to define this companion object, as class PrefixMap can stand well on its own.
//The main purpose of object PrefixMap is to define some convenience factory methods. It also defines a CanBuildFrom
//implicit to make typing work out better
object PrefixMap {
  def empty[T] = new PrefixMap[T]

  def apply[T](kvs: (String, T)*): PrefixMap[T] = {
    val m: PrefixMap[T] = empty
    for (kv <- kvs) m += kv
    m
  }

  def newBuilder[T]: mutable.Builder[(String, T), PrefixMap[T]] =
    new mutable.MapBuilder[String, T, PrefixMap[T]](empty)

  implicit def canBuildFrom[T]: CanBuildFrom[PrefixMap[_], (String, T), PrefixMap[T]] =
    new CanBuildFrom[PrefixMap[_], (String, T), PrefixMap[T]] {
      override def apply(): mutable.Builder[(String, T), PrefixMap[T]] = newBuilder[T]
      override def apply(from: PrefixMap[_]): mutable.Builder[(String, T), PrefixMap[T]] = newBuilder[T]
    }
}
