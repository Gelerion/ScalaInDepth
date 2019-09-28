package programming.in.scala.advanced.type_classes.monoid

object MonoidExample_1 {
  def main(args: Array[String]): Unit = {
    val intMonoid = new Monoid[Int] {
      override def empty: Int = 0
      override def append(left: Int, right: Int): Int = left + right
    }


    /*
    Thing is, usually, you want to have only one type class for each type in the scope. Additionally, such usage
    would introduce clumsiness the original solution hasn’t. Therefore the idea of implicits was born.
    Originally, implicits were introduced as a way of automatically passing type classes where needed basing
    solely on their type
     */
    val combined = combineValues(Map(
      "test1" -> List(1, 2, 3, 4, 5),
      "test2" -> List(6, 7, 8, 9, 0)
    ))(intMonoid)

    //Map(test1 -> 15, test2 -> 30)
    println(combined)

  }

  def combineValues[T](map: Map[String, List[T]])(monoid: Monoid[T]): Map[String, T] =
    map.mapValues(monoid.concat)

}
