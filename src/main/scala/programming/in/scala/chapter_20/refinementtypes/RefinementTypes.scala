package programming.in.scala.chapter_20.refinementtypes

import programming.in.scala.chapter_20.abstracttype.{Animal, Grass}

/**
  * Created by denis.shuvalov on 10/04/2018.
  */
object RefinementTypes {
  //When a class inherits from another, the first class is said to be a nominal subtype of the other one. It's a
  //nominal subtype because each type has a name, and the names are explicitly declared to have a subtyping relationship.
  //Scala additionally supports structural subtyping, where you get a subtyping relationship simply because two types
  //have compatible members. To get structural subtyping in Scala, use Scala's refinement types.

  //Nonetheless, structural subtyping has its own advantages. One is that sometimes there really is no more to a type
  //than its members. For example, suppose you want to define a Pasture class that can contain animals that eat grass.
  //One option would be to define a trait AnimalThatEatsGrass and mix it into every class where it applies. It would be
  //verbose, however. Class Cow has already declared that it's an animal and that it eats grass, and now it would
  //have to declare that it is also an animal-that-eats-grass.

  //Instead of defining AnimalThatEatsGrass, you can use a refinement type. Simply write the base type, Animal, followed
  //by a sequence of members listed in curly braces. The members in the curly braces further specify—or refine,
  //if you will—the types of members from the base class.

  //Here is how you write the type, "animal that eats grass":
  //    Animal { type SuitableFood = Grass }
}

// "animal that eats grass"
class Pasture {
  var animal: List[Animal { type SuitableFood = Grass }] = Nil
}