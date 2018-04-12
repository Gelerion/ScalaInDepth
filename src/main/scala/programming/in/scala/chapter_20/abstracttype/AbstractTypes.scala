package programming.in.scala.chapter_20.abstracttype

/**
  * Created by denis.shuvalov on 10/04/2018.
  */
object AbstractTypes extends App {
  //In the beginning of this chapter, you saw, "type T", an abstract type declaration. The rest of this chapter discusses
  //what such an abstract type declaration means and what it's good for. Like all other abstract declarations, an abstract
  //type declaration is a placeholder for something that will be defined concretely in subclasses. In this case, it is a
  //type that will be defined further down the class hierarchy. So T above refers to a type that is as yet unknown at the
  //point where it is declared. Different subclasses can provide different realizations of T.

  val bessy: Animal = new Cow
  //bessy eat(new Fish)
  //error: type mismatch; found : Fish required: bessy.SuitableFood
}

//Here is a well-known example where abstract types show up naturally.
//Suppose you are given the task of modeling the eating habits of animals.
class Food
abstract class AnimalV1 {
  def eat(food: Food)
}
//You might then attempt to specialize these two classes to a class of Cows that eat Grass:
class Grass extends Food
class Fish extends Food
//class Cow extends Animal {
//  override def eat(food: Grass) = {} // This won't compile
//}
/*
class Food
  abstract class Animal {
    def eat(food: Food)
  }
  class Grass extends Food
  class Cow extends Animal {
    override def eat(food: Grass) = {} // This won't compile,
  }                                    // but if it did,...
  class Fish extends Food
  val bessy: Animal = new Cow
  bessy eat (new Fish)     // ...you could feed fish to cows.
 */

//What you need to do instead is apply some more precise modeling. Animals do eat Food, but what kind of
//Food each Animal eats depends on the Animal.
abstract class Animal {
  type SuitableFood <: Food
  def eat(food: SuitableFood)
}
//With the new class definition, an Animal can eat only food that's suitable. What food is suitable cannot be determined
//at the level of the Animal class. That's why SuitableFood is modeled as an abstract type. The type has an upper bound,
//Food, which is expressed by the "<: Food" clause. This means that any concrete instantiation of SuitableFood
//(in a subclass of Animal) must be a subclass of Food. For example, you would not be able to instantiate
//SuitableFood with class IOException.
class Cow extends Animal {
  override type SuitableFood = Grass
  override def eat(food: Grass): Unit = ???
}
