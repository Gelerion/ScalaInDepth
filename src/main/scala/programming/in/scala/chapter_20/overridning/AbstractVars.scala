package programming.in.scala.chapter_20.overridning

/**
  * Created by denis.shuvalov on 10/04/2018.
  */
class AbstractVars {
  //Like an abstract val, an abstract var declares just a name and a type, but not an initial value
}

trait AbstractTime {
  //What is the meaning of abstract vars like hour and minute?
  //Vars declared as members of classes come equipped with getter and setter methods. This holds for abstract vars as well.
  //If you declare an abstract var named hour, for example, you implicitly declare an abstract getter method, hour,
  //and an abstract setter method, hour_=.
  var hour: Int
  var minute: Int

//  def hour: Int          // getter for `hour'
//  def hour_=(x: Int)     // setter for `hour'
//  def minute: Int        // getter for `minute'
//  def minute_=(x: Int)   // setter for `minute'
}


