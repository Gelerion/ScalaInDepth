package programming.in.scala.chapter_32.async.programming.adt

//Defines the state machine
sealed trait State

//Initial state
case object Start extends State

// We got a B, waiting for an A
final case class WaitForA(b: Int) extends State
// We got an A, waiting for a B
final case class WaitForB(a: Int) extends State


