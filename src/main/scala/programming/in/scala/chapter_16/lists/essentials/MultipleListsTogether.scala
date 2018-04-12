package programming.in.scala.chapter_16.lists.essentials

/**
  * Created by denis.shuvalov on 01/04/2018.
  */
class MultipleListsTogether {

  //The zipped method on tuples generalizes several common operations to work on multiple lists instead of just one.
  (List(10, 20), List(3, 4, 5)).zipped.map(_ * _) //List(30, 80)
  //val zipped: Tuple2Zipped[Int, List[Int], Int, List[Int]] = (List(10, 20), List(3, 4, 5)).zipped

  (List("abc", "de"), List(3, 2)).zipped.forall(_.length == _) //true
  (List("abc", "de"), List(3, 2)).zipped.exists(_.length != _) //false

}
