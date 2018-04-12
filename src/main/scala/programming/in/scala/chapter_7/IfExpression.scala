package programming.in.scala.chapter_7

/**
  * Created by denis.shuvalov on 07/03/2018.
  */
object IfExpression {

  def main(args: Array[String]): Unit = {
    //Scala's if is an expression that results in a value.

    //Imperative
    var filename = "default.txt"
    if(!args.isEmpty) filename = args(0)

    //Functional
    val filenameVal = if(args.isEmpty) "default.txt" else args(0)
  }



}
