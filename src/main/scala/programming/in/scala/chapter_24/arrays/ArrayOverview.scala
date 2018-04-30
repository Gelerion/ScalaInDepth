package programming.in.scala.chapter_24.arrays

/**
  * Created by denis.shuvalov on 30/04/2018.
  */
object ArrayOverview {
  /*
  Arrays are a special kind of collection in Scala. One the one hand, Scala arrays correspond one-to-one to Java arrays.
  That is, a Scala array Array[Int] is represented as a Java int[], an Array[Double] is represented as a Java double[]
  and an Array[String] is represented as a Java String[]. But at the same time, Scala arrays offer much more their Java
  analogues. First, Scala arrays can be generic. That is, you can have an Array[T], where T is a type parameter or
  abstract type. Second, Scala arrays are compatible with Scala sequences—you can pass an Array[T] where a Seq[T] is required.
   */

  val a1 = Array(1, 2, 3)
  val a2= a1 map (_ * 3) //Array(3, 6, 9)
  val a3 = a2 filter (_ % 2 != 0) //Array(3, 9)

  //Given that Scala arrays are represented just like Java arrays, how can these additional features be supported in Scala?
  //The answer lies in systematic use of implicit conversions. An array cannot pretend to be a sequence, because the
  //data type representation of a native array is not a subtype of Seq. Instead, whenever an array would be used as a
  //Seq, implicitly wrap it in a subclass of Seq.
  //The name of that subclass is scala.collection.mutable.WrappedArray.

  val seq: Seq[Int] = a1
  //val seq: Seq[Int] = LowPriorityImplicits.wrapIntArray(a1)
  val a4 = seq.toArray //Array(1, 2, 3)

  a1 eq a4 //Boolean = true

  /*
  There is yet another implicit conversion that gets applied to arrays. This conversion simply "adds" all sequence
  methods to arrays but does not turn the array itself into a sequence. "Adding" means that the array is wrapped in
  another object of type ArrayOps, which supports all sequence methods. Typically, this ArrayOps object is short-lived;
  it will usually be inaccessible after the call to the sequence method and its storage can be recycled. Modern VMs
  often avoid creating this object entirely.
   */
  seq.reverse //res2: Seq[Int] = WrappedArray(3, 2, 1)

  val ops: collection.mutable.ArrayOps[Int] = a1 //ops: scala.collection.mutable.ArrayOps[Int] = [I(1, 2, 3)
  ops.reverse // res3: Array[Int] = Array(3, 2, 1)
  //implicit
  intArrayOps(a1).reverse

  //You see that calling reverse on seq, which is a WrappedArray, will give again a WrappedArray. That's logical,
  //because wrapped arrays are Seqs, and calling reverse on any Seq will give again a Seq. On the other hand, calling
  //reverse on the ops value of class ArrayOps will result in an Array, not a Seq.

  /*
  This raises the question how the compiler picked intArrayOps over the other implicit conversion to WrappedArray in the
  line above. After all, both conversions map an array to a type that supports a reverse method, which is what the input
  specified. The answer to that question is that the two implicit conversions are prioritized. The ArrayOps conversion
  has a higher priority than the WrappedArray conversion. The first is defined in the Predef object whereas the second
  is defined in a class scala.LowPriorityImplicits, which is a superclass of Predef. Implicits in subclasses and subobjects
  take precedence over implicits in base classes. So if both conversions are applicable, the one in Predef is chosen.
   */
}
