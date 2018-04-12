package programming.in.scala.chapter_20.enumiration

/**
  * Created by denis.shuvalov on 12/04/2018.
  *
  * An interesting application of path-dependent types is found in Scala's support for enumerations.
  * Some other languages, including Java and C#, have enumerations as a built-in language construct to define new types.
  * Scala does not need special syntax for enumerations. Instead, there's a class in its standard library, scala.Enumeration.
  */
object Color extends Enumeration {
  //val Red, Green, Blue = Value

  val Red = Value
  val Green = Value
  val Blue = Value

  //This object definition provides three values: Color.Red, Color.Green, and Color.Blue. You could also import everything in Color with:
  //  import Color._

  //But what is the type of these values?
  //Enumeration defines an inner class named Value, and the same-named parameterless Value method returns a fresh
  //instance of that class. In other words, a value such as Color.Red is of type Color.Value; Color.Value is the
  //type of all enumeration values defined in object Color. It's a path-dependent type, with Color being the path
  //and Value being the dependent type. What's significant about this is that it is a completely new type, different
  //from all other types.
}

//In particular, if you define another enumeration, such as:
object Direction extends Enumeration {
  val North, East, South, West = Value
}
//then Direction.Value would be different from Color.Value because the path parts of the two types differ.

object TestingEnums extends App {
  for (d <- Direction.values) print(d + " ")
  println(Direction.East.id) //1
  println(Direction(1)) //Direction.Value = East
}
