package programming.in.scala.chapter_19.upper.bounds

/**
  * Created by denis.shuvalov on 09/04/2018.
  */
class MergeSortUsingOrdered {

  //With the "T <: Ordered[T]" syntax, you indicate that the type parameter, T, has an upper bound, Ordered[T]
  def orderedMergeSort[T <: Ordered[T]](xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]) : List[T] =
      (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if(x < y) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }

    val n = xs.length / 2
    if(n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(orderedMergeSort(ys), orderedMergeSort(zs))
    }
  }

}

class Person(val firstName: String, val lastName: String) extends Ordered[Person] {

  override def compare(that: Person): Int = {
    val lastNameComparison = lastName.compareToIgnoreCase(that.lastName)
    if (lastNameComparison != 0) lastNameComparison
    else firstName.compareToIgnoreCase(that.firstName)
  }

  override def toString = firstName + " " + lastName
}