First, you'll see a little language for digital circuits. The definition of this language will highlight a general
method for embedding domain-specific languages (DSL) in a host language like Scala. Second, we'll present a simple
but general framework for discrete event simulation. Its main task will be to keep track of actions that are performed
in simulated time. Finally, we'll show how discrete simulation programs can be structured and built. The idea of such
simulations is to model physical objects by simulated objects, and use the simulation framework to model physical time.

The example is taken from the classic textbook Structure and Interpretation of Computer Programs by Abelson and Sussman [Abe96].
What's different here is that the implementation language is Scala instead of Scheme, and that the various aspects of the
example are structured into four software layers: one for the simulation framework, another for the basic circuit
simulation package, a third for a library of user-defined circuits, and the last layer for each simulated circuit
itself. Each layer is expressed as a class, and more specific layers inherit from more general ones


A LANGUAGE FOR DIGITAL CIRCUITS
A digital circuit is built from wires and function boxes.
Wires carry signals, which are transformed by function boxes.
Signals are represented by booleans: true for signal-on and false for signal-off.

Three basic function boxes (or gates):
 * An inverter, which negates its signal.
 * An and-gate, which sets its output to the conjunction of its inputs.
 * An or-gate, which sets its output to the disjunction of its inputs.

These gates are sufficient to build all other function boxes. Gates have delays, so an output of a gate will
change only some time after its inputs change.

  val a = new Wire
  val b = new Wire
  val c = new Wire

or, equivalent but shorter, like this:

  val a, b, c = new Wire

Second, there are three procedures which "make" the basic gates we need:

  def inverter(input: Wire, output: Wire)
  def andGate(a1: Wire, a2: Wire, output: Wire)
  def orGate(o1: Wire, o2: Wire, output: Wire)

What's unusual, given the functional emphasis of Scala, is that these procedures construct the gates as a side effect,
instead of returning the constructed gates as a result. For instance, an invocation of inverter(a, b) places an inverter
between the wires a and b. It turns out that this side-effecting construction makes it easier to construct complicated
circuits gradually. Also, although methods most often have verb names, these have noun names that indicate which gate
they are making. This reflects the declarative nature of the DSL: it should describe a circuit, not the actions of making one.

More complicated function boxes can be built from the basic gates. For instance, the method constructs a half-adder.
The halfAdder method takes two inputs, a and b, and produces a sum, s, defined by "s = (a + b) % 2" and a carry, c,
defined by "c = (a + b) / 2". A diagram of the half-adder is shown in Figure 18.2.


    def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire) = {
      val d, e = new Wire
      orGate(a, b, d)
      andGate(a, b, c)
      inverter(c, e)
      andGate(d, e, s)
    }

Note that halfAdder is a parameterized function box just like the three methods that construct the primitive gates.
You can use the halfAdder method to construct more complicated circuits.

    def fullAdder(a: Wire, b: Wire, cin: Wire,
        sum: Wire, cout: Wire) = {

      val s, c1, c2 = new Wire
      halfAdder(a, cin, s, c1)
      halfAdder(b, s, sum, c2)
      orGate(c1, c2, cout)
    }