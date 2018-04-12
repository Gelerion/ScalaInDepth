package programming.in.scala.chapter_4.classes

import scala.collection.mutable
/**
  * Created by denis.shuvalov on 01/03/2018.
  */
class ChecksumAccumulator {
  private var sum = 0

  //def add(b: Byte) = sum += b
  //def checksum() = ~(sum & 0xFF) + 1

  //  Although the Scala compiler will correctly infer the result types of the add and checksum methods shown
  //  in the previous example, readers of the code will also need to mentally infer the result types by studying
  //  the bodies of the methods. As a result it is often better to explicitly provide the result types of public
  //  methods declared in a class even when the compiler would infer it for you

  def add(b: Byte): Unit = sum += b
  def checksum(): Int = ~(sum & 0xFF) + 1

  //Methods with a result type of Unit, such as ChecksumAccumulator's add method, are executed for their side effects.

  //A side effect is generally defined as mutating state somewhere external to the method or performing an I/O action.
  //In add's case, the side effect is that sum is reassigned.
  //A method that is executed only for its side effects is known as a procedure.
}


/**
  * When a singleton object shares the same name with a class, it is called that class's companion object.
  * You must define both the class and its companion object in the same source file.
  * The class is called the companion class
  *
  * singleton objects extend a superclass and can mix in traits
  *
  * One difference between classes and singleton objects is that singleton objects cannot take parameters, whereas
  * classes can. Because you can't instantiate a singleton object with the new keyword, you have no way to pass
  * parameters to it. Each singleton object is implemented as an instance of a synthetic class referenced from a
  * static variable, so they have the same initialization semantics as Java statics (ChecksumAccumulator$)
  */
object ChecksumAccumulator {
  private val cache = mutable.Map.empty[String, Int]

  def calculate(s: String): Int = {
    if(cache.contains(s))
      cache(s)
    else {
      val acc = new ChecksumAccumulator
      for (c <- s) acc.add(c.toByte)
      val cs = acc.checksum()
      cache += (s -> cs)
      cs
    }
  }
}

object Map extends App {
  val checksum: Int = ChecksumAccumulator.calculate("Every value is an object")
  println(checksum)
}
