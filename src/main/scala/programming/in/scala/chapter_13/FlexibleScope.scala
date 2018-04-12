package programming.in.scala.chapter_13

/**
  * Created by denis.shuvalov on 18/03/2018.
  */
class FlexibleScope {
  //Scope of protection
  //Access modifiers in Scala can be augmented with qualifiers. A modifier of the form private[X] or protected[X]
  //means that access is private or protected "up to" X, where X designates some enclosing package, class or singleton object.

  //Qualified access modifiers give you very fine-grained control over visibility. In particular they enable you to
  //express Java's accessibility notions, such as package private, package protected, or private up to outermost class,
  //which are not directly expressible with simple modifiers in Scala. But they also let you express accessibility
  //rules that cannot be expressed in Java.

}

/*package bobsrockets

package navigation {
  private[bobsrockets] class Navigator {
    protected[navigation] def useStarChart() = {}
    class LegOfJourney {
      private[Navigator] val distance = 100
    }
    private[this] var speed = 200
  }
}
package launch {
  import navigation._
  object Vehicle {
    private[launch] val guide = new Navigator
  }
}*/

//private[bobsrockets] - This means that this class is visible in all classes and objects that are contained in package bobsrockets.
//In particular, the access to Navigator in object Vehicle is permitted because Vehicle is contained in package launch,
//which is contained in bobsrockets. On the other hand, all code outside the package bobsrockets cannot access class Navigator.

//Effects of private qualifiers on LegOfJourney.distance
//  no access modifier	  public access
//  private[bobsrockets]	access within outer package
//  private[navigation]	  same as package visibility in Java
//  private[Navigator]	  same as private in Java
//  private[LegOfJourney]	same as private in Scala
//  private[this]	access  only from same object

//Finally, Scala also has an access modifier that is even more restrictive than private. A definition labeled
//private[this] is accessible only from within the same object that contains the definition. Such a definition
//is called object-private. For instance, the definition of speed in class Navigator is object-private.
//This means that any access must not only be within class Navigator, it must also be made from the very same instance of
//Navigator. Thus the accesses "speed" and "this.speed" would be legal from within Navigator.

//The following access, though, would not be allowed, even if it appeared inside class Navigator:
//  val other = new Navigator
//  other.speed // this line would not compile

//Marking a member private[this] is a guarantee that it will not be seen from other objects of the same class. This can
//be useful for documentation. It also sometimes lets you write more general variance annotations