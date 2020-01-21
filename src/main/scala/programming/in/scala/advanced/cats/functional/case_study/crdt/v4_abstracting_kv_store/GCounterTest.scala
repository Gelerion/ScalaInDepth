package programming.in.scala.advanced.cats.functional.case_study.crdt.v4_abstracting_kv_store


object GCounterTest {

  def main(args: Array[String]): Unit = {
    import programming.in.scala.advanced.cats.functional.case_study.crdt.v2_generalization.BoundedSemiLattice._ //merge and summon
//    import cats.instances.int._ // for Monoid - merged --> wrong answer will sum not max values!
    import cats.instances.map._ // for Monoid - counter summoning
    import KeyValueStore._
    import GCounter._

    val g1 = Map("a" -> 7, "b" -> 3)
    val g2 = Map("a" -> 2, "b" -> 5)

    val counter = GCounter[Map, String, Int]

    val merged = counter.merge(g1, g2)
    // merged: Map[String,Int] = Map(a -> 7, b -> 5)
    println("merged = " + merged)

    val total  = counter.total(merged)
    // total: Int = 12
    println("total = " + total)
  }


}
