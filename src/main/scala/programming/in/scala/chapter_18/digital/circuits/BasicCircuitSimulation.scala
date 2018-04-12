package programming.in.scala.chapter_18.digital.circuits

/**
  * Created by denis.shuvalov on 03/04/2018.
  */
abstract class BasicCircuitSimulation extends Simulation {

  //three abstract methods that represent the delays of the basic gates
  def InverterDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int

  class Wire {
    //sigVal represents the current signal
    private var sigVal = false
    //actions represents the action procedures currently attached to the wire
    private var actions: List[Action] = List()

    //returns the current signal on the wire
    def getSignal = sigVal

    def setSignal(s: Boolean) =
      //When the signal of a wire changes, the new value is stored in the variable sigVal
      if(s != sigVal) {
        sigVal = s
        //Furthermore, all actions attached to a wire are executed
        actions foreach (_ ())
        // Note the shorthand syntax for doing this: "actions foreach (_ ())" applies the function, "_ ()",
        // to each element in the actions list. As described in Chapter 8, the function "_ ()" is a shorthand
        // for "f => f ()"—i.e., it takes a function (we'll call it f) and applies it to the empty parameter list.
      }

    //attaches the specified procedure p to the actions of the wire.
    //The idea is that all action procedures attached to some wire will be executed every time the signal of the wire changes.
    //Typically actions are added to a wire by components connected to the wire. An attached action is executed once at
    //the time it is added to a wire, and after that, every time the signal of the wire changes
    def addAction(a: Action) = {
      actions = a :: actions
      a()
    }

  }

  //This action is invoked once at the time the action is installed, and thereafter every time the signal on the input changes.
  def inverter(input: Wire, output: Wire) = {
    def invertAction() = {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) {
        output setSignal !inputSig
      }
    }
    input addAction invertAction
  }

  //The implementation of and-gates is analogous to the implementation of inverters. The purpose of an and-gate is to
  //output the conjunction of its input signals. This should happen at AndGateDelay simulated time units after any
  //one of its two inputs changes
  def andGate(a1: Wire, a2: Wire, output: Wire) = {
    def andAction() = {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      afterDelay(AndGateDelay) {
        output setSignal(a1Sig & a2Sig)
      }
    }
    a1 addAction andAction
    a2 addAction andAction
  }

  def orGate(o1: Wire, o2: Wire, output: Wire) = {
    def orAction() = {
      val o1Sig = o1.getSignal
      val o2Sig = o2.getSignal
      afterDelay(OrGateDelay) {
        output setSignal (o1Sig | o2Sig)
      }
    }
    o1 addAction orAction
    o2 addAction orAction
  }

  //To run the simulator, you need a way to inspect changes of signals on wires. To accomplish this,
  //you can simulate the action of putting a probe on a wire
  def probe(name: String, wire: Wire) = {
    def probeAction() = {
      println(name + " " + currentTime + " new-value = " + wire.getSignal)
    }
    wire addAction probeAction
  }
}
