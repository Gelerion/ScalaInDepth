package programming.in.scala.chapter_9

/**
  * Created by denis.shuvalov on 11/03/2018.
  */
object ByNameParameters {

  var assertionsEnabled = true

  def myAssert(predicate: () => Boolean) =
    if (assertionsEnabled && !predicate())
      throw new AssertionError


  //The definition is fine, but using it is a little bit awkward:
  myAssert(() => 5 > 3)

  //You would really prefer to leave out the empty parameter list and => symbol in the function literal and write the code like this:
  //myAssert(5 > 3) // Won't work, because missing () =>

  //---------------------------------------------------
  //By-name parameters exist precisely so that you can do this. To make a by-name parameter, you give
  //the parameter a type starting with => instead of () =>

  def byNameAssert(predicate: => Boolean) =
    if (assertionsEnabled && !predicate)
      throw new AssertionError

  byNameAssert(5 > 3)

  //---------------------------------------------------
  //Now, you may be wondering why you couldn't simply write myAssert using a plain old Boolean for the type of
  //its parameter, like this:
  def boolAssert(predicate: Boolean) = if(assertionsEnabled && !predicate) throw new AssertionError

  //Nevertheless, one difference exists between these two approaches that is important to note. Because the type of
  //boolAssert's parameter is Boolean, the expression inside the parentheses in boolAssert(5 > 3) is evaluated !!!before
  //the call to boolAssert. The expression 5 > 3 yields true, which is passed to boolAssert. By contrast, because the
  //type of byNameAssert's predicate parameter is => Boolean, the expression inside the parentheses in byNameAssert(5 > 3)
  //is not evaluated before the call to byNameAssert. Instead a function value will be created whose apply method will
  //evaluate 5 > 3, and this function value will be passed to byNameAssert.

}
