By default when you write "Set" or "Map" you get an immutable object. If you want the mutable variant,
you need to do an explicit import. Scala gives you easier access to the immutable variants, as a gentle
encouragement to prefer them over their mutable counterparts. The easy access is provided via the Predef
object, which is implicitly imported into every Scala source file. Listing 17.1 shows the relevant definitions:

    object Predef {
      type Map[A, +B] = collection.immutable.Map[A, B]
      type Set[A] = collection.immutable.Set[A]
      val Map = collection.immutable.Map
      val Set = collection.immutable.Set
      // ...
    }

The "type" keyword is used in Predef to define Set and Map as aliases for the longer fully qualified names of the
immutable set and map traits. The vals named Set and Map are initialized to refer to the singleton objects for
the immutable Set and Map. So Map is the same as Predef.Map, which is defined to be the same as
scala.collection.immutable.Map. This holds both for the Map type and Map object.

The scala.collection.immutable.Map() factory method will return a different class depending on how many key-value
pairs you pass to it. As with sets, for immutable maps with fewer than five elements, a
special class devoted exclusively to maps of each particular size is used to maximize performance. Once a map has
five or more key-value pairs in it, however, an immutable HashMap is used.

Number of elements	Implementation
    0	        scala.collection.immutable.EmptySet | scala.collection.immutable.EmptyMap
    1	        scala.collection.immutable.Set1     | scala.collection.immutable.Map1
    2	        scala.collection.immutable.Set2     | scala.collection.immutable.Map2
    3	        scala.collection.immutable.Set3     | scala.collection.immutable.Map3
    4	        scala.collection.immutable.Set4     | scala.collection.immutable.Map4
    5 or more	scala.collection.immutable.HashSet  | scala.collection.immutable.HashMap