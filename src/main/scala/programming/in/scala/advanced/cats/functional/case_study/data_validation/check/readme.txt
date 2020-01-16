//Here we’ve represented the error message as a String. This is probably not the best representation.
type Check[A] = A => Either[String, A]

//We could attempt to build some kind of ErrorMessage type that holds all the information we can think of. However, we can’t predict the user’s requirements.
type Check[E, A] = A => Either[E, A]

//We will probably want to add custom methods to Check so let’s declare it as a trait instead of a type alias
trait Check[E, A] {
  def apply(value: A): Either[E, A]

  //Let’s add some combinator methods to Check, starting with and.
  //This method combines two checks into one, succeeding only if both checks succeed.
  //{ x => g(apply(x)) }
  def and(that: Check[E, A]): Check[E, A]
}

1. CheckF
In the first we represent checks as functions. The Check data type becomes a simple wrapper for a function
that provides our library of combinator methods. For the sake of disambiguation, we’ll call this implementation CheckF

2. CheckADT
Another implementation strategy. In this approach we model checks as an algebraic data type, with an explicit
data type for each combinator.

While the ADT implementation is more verbose than the function wrapper implementation, it has the advantage of cleanly
separating the structure of the computation (the ADT instance we create) from the process that gives it meaning
(the apply method). From here we have a number of options:
 - inspect and refactor checks after they are created;
 - move the apply “interpreter” out into its own module;
 - implement alternative interpreters providing other functionality (for example visualizing checks).
Because of its flexibility, we will use the ADT implementation for the rest of this case study.