package programming.in.scala.chapter_19

/**
  * Created by denis.shuvalov on 08/04/2018.
  */
class LegacyInteraction {

  //Java  void sort(Object[] a, Comparator cmp) { ... }

  //Scala tries to be purer than Java in not treating arrays as covariant. Here's what you get if you translate
  //the first two lines of the array example to Scala:
  val a1 = Array("abc")

  /*
  // this is Java
    String[] a1 = { "abc" };
    Object[] a2 = a1;
    a2[0] = new Integer(17);
    String s = a1[0];
   */

  //val a2: Array[Any] = a1 //error: type mismatch;

  //The cast is always legal at compile-time, and it will always succeed at run-time because the JVM's underlying
  //run-time model treats arrays as covariant, just as Java the language does. But you might get ArrayStore exceptions
  //afterwards, again just as you would in Java
  val a2: Array[Object] = a1.asInstanceOf[Array[Object]]
}
