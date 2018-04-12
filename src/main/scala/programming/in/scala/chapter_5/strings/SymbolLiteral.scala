package programming.in.scala.chapter_5.strings

/**
  * Created by denis.shuvalov on 05/03/2018.
  */
object SymbolLiteral extends App {

  //A symbol literal is written 'ident, where ident can be any alphanumeric identifier. Such literals are mapped
  //to instances of the predefined class scala.Symbol. Specifically, the literal 'cymbal will be expanded by the
  //compiler to a factory method invocation: Symbol("cymbal"). Symbol literals are typically used in situations
  //where you would use just an identifier in a dynamically typed language.


  /**
    * The method takes as parameters a symbol indicating the name of a record field and a value with which
    * the field should be updated in the record.
    */
  def updateRecordByName(r: Symbol, value: Any) = {
    //In a dynamically typed language, you could invoke this operation passing an undeclared field
    //identifier to the method, but in Scala this would not compile: updateRecordByName(favoriteAlbum, "OK Computer")
  }

  updateRecordByName('favoriteAlbum, "Ok Computer")

}
