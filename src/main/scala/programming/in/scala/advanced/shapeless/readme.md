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
# Shapeless
https://books.underscore.io/shapeless-guide/shapeless-guide.html