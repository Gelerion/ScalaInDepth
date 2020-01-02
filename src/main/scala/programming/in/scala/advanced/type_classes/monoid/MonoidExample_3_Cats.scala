package programming.in.scala.advanced.type_classes.monoid


object MonoidExample_3_Cats {

  def main(args: Array[String]): Unit = {
    val result = combineValues(Map(
      "test1" -> List(1, 2, 3, 4, 5),
      "test2" -> List(6, 7, 8, 9, 0)
    ))

    println(result)
  }

  //scope is automatically derived out of a companion object
  def combineValues[T: Monoid](map: Map[String, List[T]]): Map[String, T] =
    map.mapValues(Monoid.concat[T])
}
