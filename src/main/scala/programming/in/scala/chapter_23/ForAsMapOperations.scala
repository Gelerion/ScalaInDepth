package programming.in.scala.chapter_23

/**
  * Created by denis.shuvalov on 22/04/2018.
  */
object ForAsMapOperations {

  def map[A, B](xs: List[A], f: A => B) =
    for (x <- xs) yield f(x)

  def flatMap[A, B](xs: List[A], f: A => List[B]) =
    for(x <- xs; y <- f(x)) yield y

  def filter[A](xs: List[A], p: A => Boolean) =
    for (x <- xs if p(x)) yield x
}
