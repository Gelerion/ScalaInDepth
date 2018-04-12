package programming.in.scala.chapter_20

/**
  * Created by denis.shuvalov on 10/04/2018.
  */
trait Abstract {
  //The following trait declares one of each kind of abstract member: an abstract
  //type (T), method (transform), val (initial), and var (current)
  type T

  def transform(x: T): T

  //Client code would refer to both the val and the method in exactly the same way (i.e., obj.initial).
  //However, if initial is an abstract val, the client is guaranteed that obj.initial will yield the same value
  //every time it is referenced. If initial were an abstract method, that guarantee would not hold because, in that case,
  //initial could be implemented by a concrete method that returns a different value every time it's called.
  val initial: T

  var current: T

}
