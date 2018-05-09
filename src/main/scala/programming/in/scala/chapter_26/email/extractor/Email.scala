package programming.in.scala.chapter_26.email.extractor

/**
  * Created by denis.shuvalov on 08/05/2018.
  *
  * An extractor in Scala is an object that has a method called unapply as one of its members. The purpose of that
  * unapply method is to match a value and take it apart. Often, the extractor object also defines a dual method
  * apply for building values, but this is not required.
  */
object Email {

  // The injection method (optional)
  def apply(user: String, domain: String) = user + "@" + domain

  def unapply(str: String): Option[(String, String)] = {
    val parts = str split "@"
    if(parts.length == 2) Some(parts(0), parts(1)) else None
  }

  /*
  This object defines both apply and unapply methods. The apply method has the same meaning as always: it turns EMail
  into an object that can be applied to arguments in parentheses in the same way a method is applied. So you can write
  EMail("John", "epfl.ch") to construct the string "John@epfl.ch". To make this more explicit, you could also let EMail
  inherit from Scala's function type, like this:

    object EMail extends ((String, String) => String) { ... }

    ---
    Note
    The "(String, String) => String" portion of the previous object declaration means the same as
    Function2[String, String, String], which declares an abstract apply method that EMail implements. As a result of
    this declaration, you could, for example, pass EMail to a method expecting a Function2[String, String, String].
    --

    The unapply method is what turns EMail into an extractor. In a sense, it reverses the construction process of apply.
    Where apply takes two strings and forms an email address string out of them, unapply takes an email address and returns
    potentially two strings: the user and the domain of the address. But unapply must also handle the case where the
    given string is not an email address. That's why unapply returns an Option-type over pairs of strings. Its result
    is either Some(user, domain) if the string str is an email address with the given user and domain parts, or None,
    if str is not an email address.

      unapply("John@epfl.ch")  equals  Some("John", "epfl.ch")
      unapply("John Doe")  equals  None

    Now, whenever pattern matching encounters a pattern referring to an extractor object, it invokes the extractor's
    unapply method on the selector expression. For instance, executing the code:
      selectorString match { case EMail(user, domain) => ... }
    would lead to the call:
      EMail.unapply(selectorString)
   */
}
