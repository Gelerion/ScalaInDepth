package programming.in.scala.chapter_24.arrays

import scala.reflect.ClassTag

/**
  * Created by denis.shuvalov on 30/04/2018.
  */
object ClassTagsForTypeInference extends App {
  /*
  What about genericity? In Java you cannot write a T[] where T is a type parameter. How then is Scala's Array[T]
  represented? In fact a generic array like Array[T] could be at run time any of Java's eight primitive array types
  byte[], short[], char[], int[], long[], float[], double[], boolean[], or it could be an array of objects. The only
  common run-time type encompassing all of these types is AnyRef (or, equivalently java.lang.Object), so that's the
  type to which the Scala compiler maps Array[T]. At run-time, when an element of an array of type Array[T] is accessed
  or updated there is a sequence of type tests that determine the actual array type, followed by the correct array
  operation on the Java array. These type tests slow down array operations somewhat. You can expect accesses to
  generic arrays to be three to four times slower than accesses to primitive or object arrays. This means that if
  you need maximal performance, you should prefer concrete over generic arrays.
   */

  //Representing the generic array type is not enough, however, there must also be a way to create generic arrays.
  //This is an even harder problem, which requires a little bit of help from you. To illustrate the problem, consider
  //the following attempt to write a generic method that creates an array:

/*  //This is wrong
  def evenElems[T](xs: Vector[T]): Array[T] = {
    val arr = new Array[T]((xs.length + 1) / 2)
    for (i <- 0 until xs.length by 2)
      arr(i / 2) = xs(i)
    arr
  }*/

  /*
  The evenElems method returns a new array that consists of all elements of the argument vector xs that are at even
  positions in the vector. The first line of the body of evenElems creates the result array, which has the same element
  type as the argument. So depending on the actual type parameter for T, this could be an Array[Int], or an Array[Boolean],
  or an array of some of the other primitive types in Java, or an array of some reference type. But these types all have
  different runtime representations, so how is the Scala runtime going to pick the correct one? In fact, it can't do that
  based on the information it is given, because the actual type that corresponds to the type parameter T is erased at
  runtime. That's why you will get the following error message if you attempt to compile the code above:
        error: cannot find class tag for element type T
          val arr = new Array[T]((arr.length + 1) / 2)
                    ^
   */

  //What's required here is that you help the compiler by providing a runtime hint of what the actual type parameter of
  //evenElems is. This runtime hint takes the form of a class tag of type scala.reflect.ClassTag. A class tag describes
  //the erased type of a given type, which is all the information needed to construct an array of that type.

  //In many cases the compiler can generate a class tag on its own. Such is the case for a concrete type like Int or String.
  //It's also the case for certain generic types, like List[T], where enough information is known to predict the erased
  //type; in this example the erased type would be List.

  def evenElems[T : ClassTag](xs: Vector[T]): Array[T] = { //implicit context bound conversion
    val arr = new Array[T]((xs.length + 1) / 2)
    for (i <- xs.indices by 2)
      arr(i / 2) = xs(i)
    arr
  }

  //In this new definition, when the Array[T] is created, the compiler looks for a class tag for the type parameter T,
  //that is, it will look for an implicit value of type ClassTag[T]. If such a value is found, the class tag is used to
  //construct the right kind of array. Otherwise, you'll see an error message like the one shown previously.

  evenElems(Vector(1, 2, 3, 4, 5)) //Array(1, 3, 5)
  evenElems(Vector("this", "is", "a", "test", "run"))  //Array[java.lang.String] = Array(this, a, run)

  //In both cases, the Scala compiler automatically constructed a class tag for the element type (first Int, then String)
  //and passed it to the implicit parameter of the evenElems method. The compiler can do that for all concrete types,
  //but not if the argument is itself another type parameter without its class tag. For instance, the following fails:
  //    def wrap[U](xs: Vector[U]) = evenElems(xs)
  /*
    <console>:9: error: No ClassTag available for U
         def wrap[U](xs: Vector[U]) = evenElems(xs)
                                               ^
   */

  //What happened here is that the evenElems demands a class tag for the type parameter U, but none was found. The
  //solution in this case is, of course, to demand another implicit class tag for U. So the following works:
  def wrap[U : ClassTag](xs: Vector[U]) = evenElems(xs)
}

