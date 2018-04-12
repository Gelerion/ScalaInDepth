package programming.in.scala.chapter_8.imperative

/**
  * Created by denis.shuvalov on 08/03/2018.
  */
object FindLongLines {

  def main(args: Array[String]) = {
    val width = args(0).toInt

    for (arg <- args.drop(1)) {
      LongLines.processFile(arg, width)
    }
  }

}
