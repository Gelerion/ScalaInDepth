package programming.in.scala.chapter_15.pattern.matching

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
class VariablePatterns {
  //A variable pattern matches any object, just like a wildcard. But unlike a wildcard, Scala binds the variable to
  //whatever the object is. You can then use this variable to act on the object further. For example show a pattern match
  //that has a special case for zero, and a default case for all other values. The default case uses a variable pattern
  //so that it has a name for the value, no matter what it is.
/*  expr match {
    case 0 => "zero"
    case somethingElse => "not zero: " + somethingElse
  }*/

  //Constant
  //Here is a related example, where a pattern match involves the constants E (2.71828...) and Pi (3.14159...)
  import math.{E, Pi}
  E match {
    case Pi => "strange math? Pi = " + Pi
    case _ => "OK"
  }
  //String = OK

  //How does the Scala compiler know that Pi is a constant imported from scala.math, and not a variable that stands
  //for the selector value itself? Scala uses a simple lexical rule for disambiguation: a simple name starting
  //with a lowercase letter is taken to be a pattern variable; all other references are taken to be
  //constants. To see the difference, create a lowercase alias for pi and try with that:

  E match {
    case pi => "strange math? Pi = " + pi
    //case _ => "OK" //warning: unreachable code case _ => "OK"
  }
  //String = strange math? Pi = 2.718281828459045

  //You can still use a lowercase name for a pattern constant, if you need to, by using one of two tricks. First,
  //if the constant is a field of some object, you can prefix it with a qualifier. For instance, pi is a variable
  //pattern, but this.pi or obj.pi are constants even though they start with lowercase letters. If that does not
  //work (because pi is a local variable, say), you can alternatively enclose the variable name in back ticks.
  //For instance, `pi` would again be interpreted as a constant, not as a variable:

/*  E match {
    case `pi` => "strange math? Pi = " + pi
    case _ => "OK"
  }*/
}
