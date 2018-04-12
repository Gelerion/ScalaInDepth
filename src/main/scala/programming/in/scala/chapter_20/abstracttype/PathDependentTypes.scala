package programming.in.scala.chapter_20.abstracttype

/**
  * Created by denis.shuvalov on 10/04/2018.
  */
object PathDependentTypes {
  //Have a look at the last error message of AbstractTypes again. What's interesting about it is the type required by the eat
  //method: bessy.SuitableFood. This type consists of an object reference, bessy, followed by a type field,
  //SuitableFood, of the object. So this shows that objects in Scala can have types as members. The meaning of
  //bessy.SuitableFood is "the type SuitableFood that is a member of the object referenced from bessy" or,
  //alternatively, the type of food that's suitable for bessy.

  //A type like bessy.SuitableFood is called a path-dependent type. The word "path" here means a reference an object.
  //It could be a single name, such as bessy, or a longer access path, such as farm.barn.bessy, where each of farm,
  //barn, and bessy are variables (or singleton object names) that refer to objects.

  //------------------------------
  //If you attempted to feed a dog with food fit for a cow, your code would not compile:
  val bessy = new Cow
  val lassie = new Dog
  //lassie eat (new bessy.SuitableFood) - error: type mismatch;
  //The problem here is that the type of the SuitableFood object passed to the eat method, bessy.SuitableFood,
  //is incompatible with the parameter type of eat, lassie.SuitableFood.

  //The case would be different for two Dogs. Because Dog's SuitableFood type is defined to be an alias for class DogFood,
  //the SuitableFood types of two Dogs are in fact the same. As a result, the Dog instance named lassie could actually
  //eat the suitable food of a different Dog instance (which we'll name bootsie):
  val bootsie = new Dog
  lassie.eat(new bootsie.SuitableFood)
  //------------------------------

  //A path-dependent type resembles the syntax for an inner class type in Java, but there is a crucial difference:
  //a path-dependent type names an outer object, whereas an inner class type names an outer class. Java-style inner
  //class types can also be expressed in Scala, but they are written differently. Consider these two classes, Outer and Inner:
  class Outer {
    class Inner
  }
  //In Scala, the inner class is addressed using the expression Outer#Inner instead of Java's Outer.Inner.
  //The `.' syntax is reserved for objects. For example, imagine you instantiate two objects of type Outer, like this:
  val o1 = new Outer
  val o2 = new Outer
  //Here o1.Inner and o2.Inner are two path-dependent types (and they are different types)
  //Both of these types conform to (are subtypes of) the more general type Outer#Inner, which represents the Inner class
  //with an arbitrary outer object of type Outer. By contrast, type o1.Inner refers to the Inner class with a specific
  //outer object (the one referenced from o1). Likewise, type o2.Inner refers to the Inner class with a different,
  //specific outer object (the one referenced from o2).

  //In Scala, as in Java, inner class instances hold a reference to an enclosing outer class instance. This allows an
  //inner class, for example, to access members of its outer class. Thus you can't instantiate an inner class without
  //in some way specifying an outer class instance. One way to do this is to instantiate the inner class inside the
  //body of the outer class. In this case, the current outer class instance (referenced from this) will be used.
  //or
  val o1Inner = new o1.Inner
}

//As the term "path-dependent type" implies, the type depends on the path; in general,
//different paths give rise to different types. For instance, say you defined classes DogFood and Dog, like this:
class DogFood extends Food
class Dog extends Animal {
  override type SuitableFood = DogFood
  override def eat(food: DogFood): Unit = ???
}
