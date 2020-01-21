package programming.in.scala.advanced.cats.functional.case_study.crdt.v4_abstracting_kv_store

trait KeyValueStore[F[_, _]] {
  def put[K, V](f: F[K, V])(k: K, v: V): F[K, V]

  def get[K, V](f: F[K, V])(k: K): Option[V]

  def getOrElse[K, V](f: F[K, V])(k: K, default: V): V = get(f)(k).getOrElse(default)

  def values[K, V](f: F[K, V]): List[V]
}

object KeyValueStore {
  def apply[F[_, _]](implicit store: KeyValueStore[F]): KeyValueStore[F] = store

  implicit val mapInstance: KeyValueStore[Map] = new KeyValueStore[Map] {
    def put[K, V](map: Map[K, V])(k: K, v: V): Map[K, V] = map + (k -> v)

    def get[K, V](map: Map[K, V])(k: K): Option[V] = map.get(k)

    override def getOrElse[K, V](map: Map[K, V])(k: K, default: V): V = map.getOrElse(k, default)

    def values[K, V](map: Map[K, V]): List[V] = map.values.toList
  }

  //With our type class in place we can implement syntax to enhance data types for which we have instances:
  implicit class KvsOps[F[_,_], K, V](f: F[K, V]) { //aka syntax
    def put(key: K, value: V)(implicit kvs: KeyValueStore[F]): F[K, V] =
      kvs.put(f)(key, value)

    def get(key: K)(implicit kvs: KeyValueStore[F]): Option[V] =
      kvs.get(f)(key)

    def getOrElse(key: K, default: V)(implicit kvs: KeyValueStore[F]): V =
      kvs.getOrElse(f)(key, default)

    def values(implicit kvs: KeyValueStore[F]): List[V] =
      kvs.values(f)
  }
}

