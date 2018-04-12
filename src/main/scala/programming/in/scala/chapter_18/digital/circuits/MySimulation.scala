package programming.in.scala.chapter_18.digital.circuits

/**
  * Created by denis.shuvalov on 04/04/2018.
  */
object MySimulation extends CircuitSimulation {
  def InverterDelay = 1
  def AndGateDelay = 3
  def OrGateDelay = 5

  val input1, input2, sum, carry = new Wire

  def main(args: Array[String]): Unit = {
    probe("sum", sum)
    probe("carry", carry)

    halfAdder(input1, input2, sum, carry)

    input1 setSignal true

    run()

    input2 setSignal true

    run()
  }
}
