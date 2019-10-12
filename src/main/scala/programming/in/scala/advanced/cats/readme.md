# Cats Book
https://books.underscore.io/scala-with-cats/scala-with-cats.html

### Type-safe equality with Eq
`Eq` is designed to support type-safe equality and address annoyances using Scala’s built-in == operator.
```scala
List(1, 2, 3).map(Option(_)).filter(item => item == 1)
// res0: List[Option[Int]] = List()
```
The predicate in the filter clause always returns false because it is comparing an Int to an Option[Int].
This is programmer error—we should have compared item to Some(1) instead of 1. However, it’s not technically 
a type error because == works for any pair of objects, no matter what types we compare. Eq is designed to 
add some type safety to equality checks and work around this problem.
  
We can use Eq to define type-safe equality between instances of any given type:
```scala
package cats

trait Eq[A] {
  def eqv(a: A, b: A): Boolean
  // other concrete methods based on eqv...
}
```
The interface syntax, defined in `cats.syntax.eq`, provides two methods for performing equality checks provided 
there is an instance Eq[A] in scope:
- === compares two objects for equality;
- =!= compares two objects for inequality.

### Controlling Instance Selection
- What is the relationship between an instance defined on a type and its subtypes?
- For example, if we define a JsonWriter[Option[Int]], will the expression 
  Json.toJson(Some(1)) select this instance? (Remember that Some is a subtype of Option).
- How do we choose between type class instances when there are many available?
- What if we define two JsonWriters for Person? When we write Json.toJson(aPerson), which instance is selected?

#### Variance
When we define type classes we can add variance annotations to the type parameter to affect the variance of 
the type class and the compiler’s ability to select instances during implicit resolution.
  
To recap Essential Scala, variance relates to subtypes. We say that B is a subtype of A if we can use a value 
of type B anywhere we expect a value of type A.
  
Co- and contravariance annotations arise when working with type constructors. For example, we denote covariance 
with a + symbol:
```scala
trait F[+A] // the "+" means "covariant"
```

#### Covariance
Covariance means that the type `F[B]` is a subtype of the type `F[A]` if `B` is a subtype of `A`. This is useful for modelling 
many types, including collections like List and Option:
```scala
trait List[+A]
trait Option[+A]
```
The covariance of Scala collections allows us to substitute collections of one type for another in our code. 
For example, we can use a `List[Circle]` anywhere we expect a `List[Shape]` because `Circle` is a subtype of `Shape`:
```scala
sealed trait Shape
case class Circle(radius: Double) extends Shape

val circles: List[Circle] = ???
val shapes: List[Shape] = circles
```

#### Contravariance
Confusingly, contravariance means that the type `F[B]` is a subtype of `F[A]` if `A` is a subtype of `B`. This is useful for 
modelling types that represent processes, like our JsonWriter type class above:
```scala
trait JsonWriter[-A] {
  def write(value: A): Json
}
// defined trait JsonWriter
```
Let’s unpack this a bit further. Remember that variance is all about the ability to substitute one value for another.
Consider a scenario where we have two values, one of type `Shape` and one of type `Circle`, and two `JsonWriters`,
one for `Shape` and one for `Circle`:
```scala
val shape: Shape = ???
val circle: Circle = ???

val shapeWriter: JsonWriter[Shape] = ???
val circleWriter: JsonWriter[Circle] = ???

def format[A](value: A, writer: JsonWriter[A]): Json =
  writer.write(value)
```
Now ask yourself the question: “Which combinations of value and writer can I pass to format?” 
We can combine circle with either writer because all `Circles` are `Shapes`. 
Conversely, we can’t combine shape with circleWriter because not all `Shapes` are `Circles`.
  
This relationship is what we formally model using contravariance. 
`JsonWriter[Shape]` is a subtype of `JsonWriter[Circle]` because `Circle` is a subtype of `Shape`.
 his means we can use `shapeWriter` anywhere we expect to see a `JsonWriter[Circle]`.

#### Invariance
Invariance is actually the easiest situation to describe. It’s what we get when we don’t write a `+` or `-` in a 
type constructor:
```scala
trait F[A]
```
This means the types `F[A]` and `F[B]` are never subtypes of one another, no matter what the relationship
between `A` and `B`. This is the default semantics for Scala type constructors.
  
When the compiler searches for an implicit it looks for one matching the type or subtype. 
Thus we can use variance annotations to control type class instance selection to some extent.

# Monoids and Semigroups
In this section we explore our first type classes, **monoid** and **semigroup**. These allow us to add or 
combine values. There are instances for `Ints, Strings, Lists, Options`, and many more. 
Let’s start by looking at a few simple types and operations to see what common principles we can extract.
  
**Integer addition**
```
2 + 1
//res = 3
```  
There is also the `identity` element `0` with the property that `a + 0 == 0 + a == a` for any `Int` `a`:
```
2 + 0 => 2
0 + 2 => 2
```
There are also other properties of addition. For instance, it doesn’t matter in what order we add elements because 
we always get the same result. This is a property known as `associativity`:
```
(1 + 2) + 3 => 6
1 + (2 + 3) => 6
```
  
**Integer multiplication**
The same properties for addition also apply for multiplication, provided we use `1` as the `identity` instead of `0`
```
1 * 3 => 3
3 * 1 => 3
```
Multiplication, like addition, is `associative`:
```
(1 * 2) * 3 => 6
1 * (2 * 3) => 6
```

#### Definition of a Monoid
  
Formally, a monoid for a type `A` is:
- n operation `combine` with type `(A, A) => A`
- an element `empty` of type `A`
```scala
trait Monoid[A] {
  def combine(x: A, y: A): A
  def empty: A
}
```
In addition to providing the combine and empty operations, monoids must formally obey several **laws**.
```scala
def associativeLaw[A](x: A, y: A, z: A)
      (implicit m: Monoid[A]): Boolean = {
  m.combine(x, m.combine(y, z)) == m.combine(m.combine(x, y), z)
}

def identityLaw[A](x: A)
      (implicit m: Monoid[A]): Boolean = {
  (m.combine(x, m.empty) == x) && (m.combine(m.empty, x) == x)
}
```

#### Definition of a Semigroup
A `semigroup` is just the combine part of a monoid. 
While many semigroups are also monoids, there are some data types for which we cannot define an `empty` element. 
For example, we have just seen that sequence concatenation and integer addition are monoids. 
However, if we restrict ourselves to non-empty sequences and positive integers, we are no longer able to 
define a sensible empty element. Cats has a `NonEmptyList` data type that has an implementation of `Semigroup`
but no implementation of `Monoid`.
```scala
trait Semigroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}
```
In a distributed system, different machines may end up with different views of data. For example, 
one machine may receive an update that other machines did not receive. We would like to reconcile these 
different views, so every machine has the same data if no more updates arrive. 
This is called eventual consistency.
  
A particular class of data types support this reconciliation. These data types are called commutative 
replicated data types (CRDTs). The key operation is the ability to merge two data instances, with a result 
that captures all the information in both instances. This operation relies on having a monoid instance





























