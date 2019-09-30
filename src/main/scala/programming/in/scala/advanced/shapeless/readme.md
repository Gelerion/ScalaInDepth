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


# Shapeless
https://books.underscore.io/shapeless-guide/shapeless-guide.html
https://kubuszok.com/2018/implicits-type-classes-and-extension-methods-part-2/