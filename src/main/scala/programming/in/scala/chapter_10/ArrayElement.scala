package programming.in.scala.chapter_10

/**
  * Created by denis.shuvalov on 12/03/2018.
  *
  * Such an extends clause has two effects: It makes class ArrayElement inherit all non-private members from class
  * Element, and it makes the type ArrayElement a subtype of the type Element. Given ArrayElement extends Element,
  * class ArrayElement is called a subclass of class Element. Conversely, Element is a superclass of ArrayElement.
  * If you leave out an extends clause, the Scala compiler implicitly assumes your class extends from scala.AnyRef,
  * which on the Java platform is the same as class java.lang.Object. Thus, class Element implicitly extends class AnyRef
  *
  * AnyRef <- Element <- ArrayElement
  */
//Consider again the definition of class ArrayElement shown in the previous section. It has a parameter conts whose
//sole purpose is to be copied into the contents field. The name conts of the parameter was chosen just so that it
//would look similar to the field name contents without actually clashing with it. This is a "code smell," a sign
//that there may be some unnecessary redundancy and repetition in your code.
//class ArrayElement(conts: Array[String]) extends Element {

//You can avoid the code smell by combining the parameter and the field in a single parametric field definition
class ArrayElement(override val contents: Array[String]
                   /*, private var age: Int*/) extends Element {

  //uniform access principle is just one aspect where Scala treats fields and methods more uniformly than Java.
  //Another difference is that in Scala, fields and methods belong to the same namespace.

  //Overriding a parameterless method with a field:
  //val contents: Array[String] = conts

  //in Scala it is forbidden to define a field and method with the same name in the same class
  //private var f = 0 // Won't compile, because a field
  //def f = 1         // and method have the same name

  //Generally, Scala has just two namespaces for definitions in place of Java's four. Java's four namespaces
  //are fields, methods, types, and packages. By contrast, Scala's two namespaces are:
  //  values (fields, methods, packages, and singleton objects)
  //  types (class and trait names)
}
