package programming.in.scala.advanced.type_classes.monoid

object MonoidExample_2_implicits {
  def main(args: Array[String]): Unit = {

    /*
    Here we can notice one interesting thing about type classes and implicits:
    when you need an implicit of type F[A] compiler will look for it inside companion objects of F and A
    if no implicit could be found in the current scope.
    This way, you don’t have to define/import them everywhere if you just want to rely on default.
     */

    //implicit type is taken out a Monoid's companion object
    val result = combineValuesV2(Map(
      "test1" -> List(1, 2, 3, 4, 5),
      "test2" -> List(6, 7, 8, 9, 0)
    ))

    println(result)

  }

  def combineValuesV2[T](map: Map[String, List[T]])
                      (implicit monoid: Monoid[T]): Map[String, T] =
    map.mapValues(monoid.concat)
}
