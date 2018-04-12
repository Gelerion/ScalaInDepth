package programming.in.scala.chapter_7

/**
  * Created by denis.shuvalov on 07/03/2018.
  */
object MultiplicationTable extends App {

  println(multiTable())

  def multiTable() = {
    val tableSeq = for (row <- 1 to 10) yield makeRow(row)
    tableSeq.mkString("\n")
  }

  def makeRow(row: Int) = makeRowSeq(row).mkString

  def makeRowSeq(row: Int) = {
    for (col <- 1 to 10) yield {
      val prod = (row * col).toString
      val padding = " " * (4 - prod.length)
      padding + prod
    }
  }
}
