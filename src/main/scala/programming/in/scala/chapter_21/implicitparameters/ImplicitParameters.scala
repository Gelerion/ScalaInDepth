package programming.in.scala.chapter_21.implicitparameters

/**
  * Created by denis.shuvalov on 15/04/2018.
  *
  * The remaining place the compiler inserts implicits is within argument lists. The compiler will sometimes replace
  * someCall(a) with someCall(a)(b), or new SomeClass(a) with new SomeClass(a)(b), thereby adding a missing parameter
  * list to complete a function call. It is the entire last curried parameter list that's supplied, not just the last
  * parameter. For example, if someCall's missing last parameter list takes three parameters, the compiler might replace
  * someCall(a) with someCall(a)(b, c, d). For this usage, not only must the inserted identifiers, such as b, c, and d
  * in (b, c, d), be marked implicit where they are defined, but also the last parameter list in someCall's or someClass's
  * definition must be marked implicit.
  */
object ImplicitParameters extends App {

  //provide the prompt explicitly
  private val bobsPrompt = new PreferredPrompt("relax>")
  Greeter.greet("Bob")(bobsPrompt)
  //Welcome, Bob. The system is ready.
  //relax>

  //Once you bring it into scope via an import, however, it will be used to supply the missing parameter list:

  //To let the compiler supply the parameter implicitly, you must first define a variable of the expected type,
  //which in this case is PreferredPrompt.
  object JoesPrefs {
    //Note that the val itself is marked implicit. If it wasn't, the compiler would not use it to supply the missing parameter list.
    implicit val propmt = new PreferredPrompt("Yes, master>")
    implicit val drink = new PreferredDrink("tea")
  }

  import JoesPrefs._
  Greeter.greet("Joe")
  //Welcome, Joe. The system is ready.
  //Yes, master>
  Greeter.greetV2("Joe")
  //One thing to note about the previous examples is that we didn't use String as the type of prompt or drink, even
  //though ultimately it was a String that each of them provided through their preference fields. Because the compiler
  //selects implicit parameters by matching types of parameters against types of values in scope, implicit parameters
  //usually have "rare" or "special" enough types that accidental matches are unlikely. For example, the types
  //PreferredPrompt and PreferredDrink were defined solely to serve as implicit parameter types. As a result,
  //it is unlikely that implicit variables of these types will be in scope if they aren't intended to be used as
  //implicit parameters to Greeter.greet.


  //-----------------------------------------------------------------------------------------------------------------

  //This additional argument specifies which ordering to use when comparing elements of type T. As such, this version
  //can be used for types that don't have a built-in ordering. Additionally, this version can be used for types that
  //do have a built-in ordering, but for which you occasionally want to use some other ordering.
  def maxListOrdering[T](elements: List[T])(ordering: Ordering[T]) :T = {
    elements match {
      case List() => throw new IllegalArgumentException("empty list!")
      case List(x) => x
      case x :: rest =>
        val maxRest = maxListOrdering(rest)(ordering)
        if(ordering.gt(x, maxRest)) x
        else maxRest
    }
  }

  //The maxListImpParm function is an example of an implicit parameter used to provide more information about a type
  //mentioned explicitly in an earlier parameter list. To be specific, the implicit parameter ordering, of type
  //Ordering[T], provides more information about type T—in this case, how to order Ts. Type T is mentioned in List[T],
  //the type of parameter elements, which appears in the earlier parameter list. Because elements must always be provided
  //explicitly in any invocation of maxListImpParam, the compiler will know T at compile time and can therefore determine
  //whether an implicit definition of type Ordering[T] is available. If so, it can pass in the second parameter list,
  //ordering, implicitly
  def maxListImpParam[T](elements: List[T])(implicit ordering: Ordering[T]) :T = {
    elements match {
      case List() => throw new IllegalArgumentException("empty list!")
      case List(x) => x
      case x :: rest =>
        val maxRest = maxListImpParam(rest)(ordering)
        if(ordering.gt(x, maxRest)) x
        else maxRest
    }
  }

  println(maxListImpParam(List(1, 5, 10, 3))) //10
}

class PreferredPrompt(val preference: String)
class PreferredDrink(val preference: String)

object Greeter {
  //The last parameter list is marked implicit, which means it can be supplied implicitly.
  def greet(name: String)(implicit prompt: PreferredPrompt) = {
    println("Welcome, " + name + ". The system is ready.")
    println(prompt.preference)
  }

  //multiple params
  def greetV2(name: String)(implicit prompt: PreferredPrompt, drink: PreferredDrink) = {
    println("Welcome, " + name + ". The system is ready.")
    print("But while you work, ")
    println("why not enjoy a cup of " + drink.preference + "?")
    println(prompt.preference)
  }
}

