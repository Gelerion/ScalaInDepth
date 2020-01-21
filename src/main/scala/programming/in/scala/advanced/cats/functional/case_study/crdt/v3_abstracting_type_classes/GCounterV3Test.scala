package programming.in.scala.advanced.cats.functional.case_study.crdt.v3_abstracting_type_classes

object GCounterV3Test {

  def main(args: Array[String]): Unit = {
    import cats.instances.int._ // for Monoid
    import GCounterV3._

    val g1 = Map("a" -> 7, "b" -> 3)
    val g2 = Map("a" -> 2, "b" -> 5)

    val counter = GCounterV3[Map, String, Int]

    val merged = counter.merge(g1, g2)
    // merged: Map[String,Int] = Map(a -> 7, b -> 5)
    println("merged = " + merged)

    val total  = counter.total(merged)
    // total: Int = 12
    println("total = " + total)
  }


}
