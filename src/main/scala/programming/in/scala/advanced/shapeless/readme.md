# Algebraic Data Types
`Algebraic data types` (ADTs) are a functional programming concept with a fancy name but a very simple meaning. 
They are an idiomatic way of representing data using “ands” and “ors”
- a shape is a rectangle `or` a circle
- a rectangle has a width `and` a height
- a circle has a radius  

In ADT terminology, “and” types such as rectangle and circle are called `products` and “or” types 
such as shape are called `coproducts`. In Scala we typically represent `products` using case
classes and `coproducts` using sealed traits
> see AdtTypes for example
 
 #### Alternative encodings
Sealed traits and case classes are undoubtedly the most convenient encoding of ADTs in Scala. 
However, they aren’t the only encoding. For example, the Scala standard library provides generic 
products in the form of `Tuples` and a generic coproduct in the form of `Either`

```scala
type Rectangle = (Double, Double)
type Circle    = Double
type Shape     = Either[Rectangle, Circle]

val rect2: Shape = Left((3.0, 4.0))
val circ2: Shape = Right(1.0)
```

#### Generic product encodings
Product could be represented as heterogeneous list
```scala
import shapeless.{HList, ::, HNil}

val product: String :: Int :: Boolean :: HNil = "Sunday" :: 1 :: false :: HNil
```
Shapeless provides a type class called Generic that allows us to switch back and forth between a concrete ADT and 
its generic representation.
```scala
import shapeless.Generic

case class IceCream(name: String, numCherries: Int, inCone: Boolean)
val iceCreamGen = Generic[IceCream]

val instance = IceCream("Sundae", 1, false)
val repr = iceCreamGen.to(instance) //iceCreamGen.Repr = Sundae :: 1 :: false :: HNil
val iceCream = iceCreamGen.from(repr) //IceCream = IceCream(Sundae,1,false)
```

## Type Classes
A type class is a parameterised trait representing some sort of general functionality that we would like to apply 
to a wide range of types:
```
trait CsvEncoder[A] {
  def encode(value: A): List[String]
}
```

#### Deriving instances for products
1. If we have type class instances for the head and tail of an `HList`, we can derive an instance for the whole HList.
2. If we have a case class `A`, a `Generic[A]`, and a type class instance for the generic’s `Repr`, 
we can combine them to create an instance for A.

Take `CsvEncoder` and `IceCream` as examples:
1. `IceCream` has a generic `Repr` of type `String :: Int :: Boolean :: HNil`.
2. The `Repr` is made up of a `String`, an `Int`, a `Boolean`, and an `HNil`. 
If we have `CsvEncoders` for these types, we can create an encoder for the whole thing.
3. If we can derive a `CsvEncoder` for the `Repr`, we can create one for `IceCream`.

We can combine our derivation rules for HLists with an instance of Generic to produce a CsvEncoder for IceCream:
```scala
import shapeless.Generic

implicit val iceCreamEncoder: CsvEncoder[IceCream] = {
  val gen = Generic[IceCream]
  val enc = CsvEncoder[gen.Repr] //<-- problem, illegal dependent method type
  createEncoder(iceCream => enc.encode(gen.to(iceCream)))
}

implicit def genericEncoder[A, R](
  implicit 
  gen: Generic[A] { type Repr = R },
  enc: CsvEncoder[R]
): CsvEncoder[A] = createEncoder(a => enc.encode(gen.to(a)))
```
and use it as follows:
```scala
writeCsv(iceCreams)
```

## Literal types
A Scala value may have multiple types. 
For example, the string `"hello"` has at least three types: `String`, `AnyRef`, and `Any`
```
"hello" : String
"hello" : AnyRef
"hello" : Any
```
Interestingly, "hello" also has another type: a “singleton type” that belongs exclusively to that one value. 
This is similar to the singleton type we get when we define a companion object:
```
object Foo
Foo.type = Foo$@5c32f469
```
The type `Foo.type` is the type of `Foo`, and `Foo` is the only value with that type.
Singleton types applied to literal values are called **literal types**.
  
`Shapeless` provides a few tools for working with literal types. First, there is a narrow macro that converts 
a literal expression to a singleton-typed literal expression:
```scala
import shapeless.syntax.singleton._
var x = 42.narrow //Int(42) = 42
```
Note the type of x here: `Int(42)` is a **literal type**. It is a subtype of `Int` that only contains the value `42`. 
If we attempt to assign a different number to x, we get a compile error:
```
x = 43 //error: type mismatch - Int(43)
```

## Polymorphic Functions
“Regular” Scala programs make heavy use of functional operations like map and flatMap. A question arises: can we perform 
similar operations on HLists? The answer is “yes”, although we have to do things a little differently than in regular Scala. 
Unsurprisingly the mechanisms we use are type class based and there are a suite of ops type classes to help us out.
  
The heterogeneous element types in an HList cause this model to break down. Scala functions have fixed input and output types, 
so the result of our map will have to have the same element type in every position.
```scala
trait Case[P, A] {
  type Result
  def apply(a: A): Result
}

trait Poly {
  def apply[A](arg: A)(implicit cse: Case[this.type, A]): cse.Result =
    cse.apply(arg)
}
```
`Shapeless` Poly syntax
```scala
import shapeless._

object myPoly extends Poly1 {
  implicit val intCase: Case.Aux[Int, Double] =
    at(num => num / 2.0)

  implicit val stringCase: Case.Aux[String, Int] =
    at(str => str.length)
}

myPoly.apply(123)
```

#### Mapping and flatMapping using Poly
```scalaArbitrary 
(10 :: "hello" :: true :: HNil).map(sizeOf)
(10 :: "hello" :: true :: HNil).flatMap(valueAndSizeOf)
```

#### Folding using Poly
```scala
(10 :: "hello" :: 100 :: HNil).foldLeft(0)(sum)
```

#### Defining type classes using Poly
```scala
object conversions extends Poly1 {
  implicit val intCase:  Case.Aux[Int, Boolean]   = at(_ > 0)
  implicit val boolCase: Case.Aux[Boolean, Int]   = at(if(_) 1 else 0)
  implicit val strCase:  Case.Aux[String, String] = at(identity)
}

case class IceCream1(name: String, numCherries: Int, inCone: Boolean)
case class IceCream2(name: String, hasCherries: Boolean, numCones: Int)

IceCream1("Sundae", 1, false).mapTo[IceCream2](conversions)
```

# Case study: random value generator
Property-based testing libraries like ScalaCheck use type classes to generate random data for unit tests. 
For example, ScalaCheck provides the Arbitrary type class that we can use as follows:
```scala
import org.scalacheck._

for(i <- 1 to 3) println(Arbitrary.arbitrary[Int].sample)
// Some(1)
// Some(1813066787)
// Some(1637191929)

for(i <- 1 to 3) println(Arbitrary.arbitrary[(Boolean, Byte)].sample)
// Some((true,127))
// Some((false,83))
// Some((false,-128))
```

# Shapeless
https://books.underscore.io/shapeless-guide/shapeless-guide.html
https://kubuszok.com/2018/implicits-type-classes-and-extension-methods-part-2/