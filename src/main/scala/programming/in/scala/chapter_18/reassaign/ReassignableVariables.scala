package programming.in.scala.chapter_18.reassaign

/**
  * Created by denis.shuvalov on 03/04/2018.
  */
class ReassignableVariables {
  //In Scala, every var that is a non-private member of some object implicitly defines a getter and a setter
  //method with it. These getters and setters are named differently from the Java convention, however.
  //The getter of a var x is just named "x", while its setter is named "x_="

  var hour = 12 //generates a getter, "hour", and setter, "hour_="
  //The field is always marked private[this], which means it can be accessed only from the object that contains it
  //The getter and setter, on the other hand, get the same visibility as the original var. If the var definition is
  //public, so are its getter and setter. If it is protected, they are also protected, and so on
}

class Time {
  var hour = 12
  var minute = 0
}

//This implementation is exactly equivalent to the class definition shown:
class TimeExplicit {
  //the names of the local fields h and m are arbitrarily chosen so as not to
  //clash with any names already in use
  private[this] var h = 12
  private[this] var m = 0

  def hour: Int = h
  def hour_=(x: Int) = { h = x }

  def minute: Int = m
  def minute_=(x: Int) = { m = x }

  //By defining these access methods directly you can interpret the operations of variable
  //access and variable assignment as you like
//  def minute_= (x: Int) = {
//    require(0 <= x && x < 60)
//    m = x
//  }
}

//It's also possible, and sometimes useful, to define a getter and a setter without an associated field.
class Thermometer {

  //The celsius variable is initially set to a default value by specifying `_' as the "initializing value" of the variable.
  //More precisely, an initializer "= _" of a field assigns a zero value to that field. The zero value depends on the
  //field's type. It is 0 for numeric types, false for booleans, and null for reference types. This is the same as if
  //the same variable was defined in Java without an initializer.
  var celsius: Float = _

  //!Note that you cannot simply leave off the "= _" initializer in Scala. If you had written:
  // var celsius: Float
  //this would declare an abstract variable, not an uninitialized one

  def fahrenheit = celsius * 9 / 5 + 32
  def fahrenheit_= (f: Float) = {
    celsius = (f - 32) * 5 / 9
  }
  override def toString = fahrenheit + "F/" + celsius + "C"
}