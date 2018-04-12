package programming.in.scala.chapter_19.contravariance

/**
  * Created by denis.shuvalov on 09/04/2018.
  * Here, OutputChannel is defined to be contravariant in T. So an output channel of AnyRefs, say,
  * is a subtype of an output channel of Strings
  */
trait OutputChannel[-T] {

  //The only supported operation is writing a String to it. The same operation can also be done on an
  //OutputChannel[AnyRef]. So it is safe to substitute an OutputChannel[AnyRef] for an OutputChannel[String].
  //By contrast, it would not be safe to substitute an OutputChannel[String] where an OutputChannel[AnyRef] is
  //required. After all, you can send any object to an OutputChannel[AnyRef], whereas an OutputChannel[String]
  //requires that the written values are all strings.

  //This reasoning points to a general principle in type system design: It is safe to assume that a type T is a
  //subtype of a type U if you can substitute a value of type T wherever a value of type U is required. This is
  //called the Liskov Substitution Principle
  //The principle holds if T supports the same operations as U, and all of T's operations require less
  //and provide more than the corresponding operations in U.
  //In the case of output channels, an OutputChannel[AnyRef] can be a subtype of an OutputChannel[String] because
  //the two support the same write operation, and this operation requires less in OutputChannel[AnyRef] than in
  //OutputChannel[String]. "Less" means the argument is only required to be an AnyRef in the first case, whereas
  //it is required to be a String in the second case
  def write(x: T)

}


/*
  Sometimes covariance and contravariance are mixed in the same type
  trait Function1[-S, +T] {
    def apply(x: S): T
  }
*/

class Publication(val title: String)
class Book(title: String) extends Publication(title)

object Library {
  val books: Set[Book] = Set(new Book("Programming in Scala"), new Book("Walden"))

  //For instance, whenever you write the function type A => B, Scala expands this to Function1[A, B]
  def printBookList(info: Book => AnyRef) = {
    for (book <- books) println(info(book))
  }
}

object Customer extends App {
  def getTitle(p: Publication): String = p.title
  Library.printBookList(getTitle)
}
