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


# Functor
Functors on their own aren’t so useful, but special cases of functors such as monads 
and applicative functors are some of the most commonly used abstractions in Cats.
  
Informally, a functor is anything with a `map` method. You probably know lots of types that have this: 
`Option, List, and Either`, to name a few.
  
We typically first encounter map when iterating over `Lists`. However, to understand functors we need to think of 
the method in another way. Rather than traversing the list, we should think of it as transforming all of 
the values inside in `one go`. We specify the function to apply, and map ensures it is applied to every item. 
The values change but the structure of the list remains the same:
```scala
List(1, 2, 3).map(n => n + 1)
```
Similarly, when we map over an `Option`, we transform the contents but leave the `Some` or `None` context unchanged. 
The same principle applies to `Either` with its `Left` and `Right` contexts.
  
Because `map` leaves the structure of the context unchanged, we can call it repeatedly to sequence multiple 
computations on the contents of an initial data structure:
```scala
List(1, 2, 3).
  map(n => n + 1).
  map(n => n * 2).
  map(n => n + "!")
```

#### Functions 
It turns out that single argument functions are also functors. To see this we have to tweak the types a little. 
A function `A => B` has two type parameters: the parameter type `A` and the result type `B`.
“mapping” over a Function1 is function composition:
```scala
import cats.instances.function._ // for Functor
import cats.syntax.functor._     // for map

val func1: Int => Double = (x: Int) => x.toDouble
val func2: Double => Double = (y: Double) => y * 2

(func1 map func2)(1)     // composition using map
// res7: Double = 2.0

(func1 andThen func2)(1) // composition using andThen
// res8: Double = 2.0

func2(func1(1))          // composition written out by hand
// res9: Double = 2.0
```

#### Definition of a Functor
Every example we’ve looked at so far is a functor: a class that encapsulates sequencing computations. 
Formally, a functor is a type `F[A]` with an operation map with type `(A => B) => F[B]`

Cats encodes `Functor` as a type class, `cats.Functor`, so the method looks a little different. 
It accepts the initial `F[A]` as a parameter alongside the transformation function.
```scala
import scala.language.higherKinds

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}
```

#### Functor Laws
Functors guarantee the same semantics whether we sequence many small operations one by one, or combine them 
into a larger function before `mapping`. To ensure this is the case the following laws must hold:
- **Identity**: calling `map` with the identity function is the same as doing nothing:
```
fa.map(a => a) == fa
```
- **Composition**: `mapping` with two functions `f` and `g` is the same as mapping with `f` and then `mapping` with `g`:
```
fa.map(g(f(_))) == fa.map(f).map(g)
```

#### Functor Syntax
We’ll abstract over functors so we’re not working with any particular concrete type. We can write 
a method that applies an equation to a number no matter what functor context it’s in:
```scala
def doMath[F[_]](start: F[Int])
    (implicit functor: Functor[F]): F[Int] =
  start.map(n => n + 1 * 2)

import cats.instances.option._ // for Functor
import cats.instances.list._   // for Functor

doMath(Option(20))
// res3: Option[Int] = Some(22)

doMath(List(1, 2, 3))
// res4: List[Int] = List(3, 4, 5)
```

### Contravariant and Invariant Functors
As we have seen, we can think of Functor's map method as “appending” a transformation to a chain. 
We’re now going to look at two other type classes, one representing prepending operations to a 
chain, and one representing building a bidirectional chain of operations. These are called 
contravariant and invariant functors respectively.
```scala
trait Printable[A] {
  self =>

  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] =
    new Printable[B] {
      def format(value: B): String =
        self.format(func(value))
    }
}

def format[A](value: A)(implicit p: Printable[A]): String =
  p.format(value)
```
Now define an instance of Printable for the following Box case class.

To make the instance generic across all types of Box, we base it on the Printable for the type
inside the Box. We can either write out the complete definition by hand:
```scala
implicit def boxPrintable[A](implicit p: Printable[A]) =
  new Printable[Box[A]] {
    def format(box: Box[A]): String =
      p.format(box.value)
  }
```
or use contramap to base the new instance on the implicit parameter:
```scala
implicit def boxPrintable[A](implicit p: Printable[A]) =
  p.contramap[Box[A]](_.value)
```

### Invariant functors and the imap method
Invariant functors implement a method called `imap` that is informally equivalent to a combination of 
`map` and `contramap`. If map generates new type class instances by appending a function to a chain, and 
`contramap` generates them by prepending an operation to a chain, `imap` generates them via a pair of 
bidirectional transformations.
```scala
trait Codec[A] {
  def encode(value: A): String
  def decode(value: String): A

  def imap[B](dec: A => B, enc: B => A): Codec[B] = {
    val self = this
    new Codec[B] {
      def encode(value: B): String =
        self.encode(enc(value))

      def decode(value: String): B =
        dec(self.decode(value))
    }
  }
}
```

# Monads
Monads are one of the most common abstractions in Scala. Many Scala programmers quickly become intuitively familiar 
with monads, even if we don’t know them by name.

> A monad is a mechanism for sequencing computations.

#### Definition of a Monad

- `pure`, of type `A => F[A]`;
- `flatMap`, of type `(F[A], A => F[B]) => F[B]`.

`pure` abstracts over constructors, providing a way to create a new monadic context from a plain value. 
`flatMap` provides the sequencing step we have already discussed, extracting the value from a context 
and generating the next context in the sequence. Here is a simplified version of the Monad type 
class in Cats:
```scala
import scala.language.higherKinds

trait Monad[F[_]] {
  def pure[A](value: A): F[A]
  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]
}
```

#### Monad Laws
`pure` and `flatMap` must obey a set of laws that allow us to sequence operations freely without 
unintended glitches and side-effects:
 - **Left identity**: calling `pure` and transforming the result with `func` is the same as calling `func`:
 ```pure(a).flatMap(func) == func(a)```
 
 - **Right identity**: passing pure to `flatMap` is the same as doing nothing:
```m.flatMap(pure) == m```

 - **Associativity**: flatMapping over two functions f and g is the same as flatMapping over f and 
 then flatMapping over g:
 ```m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))```

#### Identity Monad
It’s difficult to demonstrate the `flatMap` and `map` methods directly on Scala monads like 
`Option` and `List`, because they define their own explicit versions of those methods. 
Instead we’ll write a generic function that performs a calculation on parameters that come wrapped in a monad of the user’s choice:
```scala
def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
  a.flatMap(x => b.map(y => x*x + y*y))
```
With proper imports:
```scala
sumSquare(Option(3), Option(4))
sumSquare(List(1, 2, 3), List(4, 5))
```
This method works well on `Option`s and `List`s but we can’t call it passing in plain values:
```
sumSquare(3, 4) 
//error: no type parameters for method sumSquare
```
It would be incredibly useful if we could use `sumSquare` with parameters that were either 
in a monad or not in a monad at all. This would allow us to abstract over monadic and 
non-monadic code. Fortunately, Cats provides the `Id` type to bridge the gap:
```scala
import cats.Id
sumSquare(3 : Id[Int], 4 : Id[Int])
```
`Id` allows us to call our monadic method using plain values. 
What’s going on? Here is the definition of `Id` to explain:
```scala
type Id[A] = A
```
`Id` is actually a type alias that turns an atomic type into a single-parameter type constructor.

#### Either Monad
Let’s look at another useful monad: the `Either` type from the Scala standard library. 
In Scala 2.11 and earlier, many people didn’t consider Either a monad because it didn’t 
have `map` and `flatMap` methods. In Scala 2.12, however, Either became right biased.
```scala
val either1: Either[String, Int] = Right(10)
val either2: Either[String, Int] = Right(32)

for {
  a <- either1.right //just either1 (scala 2.12)
  b <- either2.right //just either2 (scala 2.12)
} yield a + b
```
Cats back-ports this behaviour to Scala 2.11 via the cats.syntax.either import, allowing us 
to use right-biased `Either` in all supported versions of Scala.

#### The Eval Monad
`cats.Eval` is a monad that allows us to abstract over different **models of evaluation**. 
We typically hear of two such models: **eager** and **lazy**. `Eval` throws in a further 
distinction of whether or not a result is *memoized*.
  
- Eager computations happen immediately whereas 
- Lazy computations happen on access. 
- Memoized computations are run once on first access, after which the results are cached.
  
Scala `vals` are eager and memoized
```scala
val x = {
  println("Computing X")
  math.random
}
//Computing X
// x: Double = 0.052234880483295054
x // first access
// res0: Double = 0.052234880483295054
x // second access
// res1: Double = 0.052234880483295054
```
By contrast, `defs` are lazy and not memoized.
```scala
def y = {
  println("Computing Y")
  math.random
}
// y: Double

y // first access
// Computing Y
// res2: Double = 0.39447833704011936

y // second access
// Computing Y
// res3: Double = 0.7866745482741204
```
  
**Eval’s Models of Evaluation**
```scala
val now = Eval.now(math.random + 1000) //Now(1000.0817186632896)
val later = Eval.later(math.random + 2000) //cats.Later@32220dad
val always = Eval.always(math.random + 3000) //cats.Always@7553edc9
```
**Note!**
[Trampolining and stack safety in Scala](https://medium.com/@olxc/trampolining-and-stack-safety-in-scala-d8e86474ddfa)
Trampolining and `Eval.defer`


# Useful `Monad`s 
 
### The Writer Monad
`cats.data.Writer` is a monad that lets us carry a log along with a computation.
We can use it to record messages, errors, or additional data about a computation, 
and extract the log alongside the final result.
  
One common use for Writers is recording sequences of steps in multi-threaded 
computations where standard imperative logging techniques can result in interleaved 
messages from different contexts. With Writer the log for the computation is tied to 
the result, so we can run concurrent computations without mixing logs.  

For convenience, Cats provides a way of creating Writers specifying only the log or the result. 
If we only have a result we can use the standard pure syntax. 
```scala
import cats.instances.vector._   // for Monoid
import cats.syntax.applicative._ // for pure

type Logged[A] = Writer[Vector[String], A]
123.pure[Logged]
```

### The Reader Monad
`cats.data.Reader` is a monad that allows us to sequence operations that depend on some input. 
Instances of `Reader` wrap up functions of one argument, providing us with useful methods for 
composing them.
  
One common use for `Readers` is dependency injection. If we have a number of operations 
that all depend on some external configuration, we can chain them together using a `Reader`
to produce one large operation that accepts the configuration as a parameter and runs 
our program in the order specified.

When to Use Readers?
`Readers` provide a tool for doing dependency injection. We write steps of our program 
as instances of `Reader`, chain them together with `map` and `flatMap`, and build a function 
that accepts the dependency as input.

There are many ways of implementing dependency injection in Scala, from simple techniques 
like methods with multiple parameter lists, through implicit parameters and type classes, 
to complex techniques like the cake pattern and DI frameworks.

`Readers` are most useful in situations where:
 - we are constructing a batch program that can easily be represented by a function;
 - we need to defer injection of a known parameter or set of parameters;
 - we want to be able to test parts of the program in isolation.

By representing the steps of our program as `Readers` we can test them as easily as pure 
functions, plus we gain access to the `map` and `flatMap` combinators.

For more advanced problems where we have lots of dependencies, or where a program 
isn’t easily represented as a pure function, other dependency injection techniques 
tend to be more appropriate.

### The State Monad
`cats.data.State` allows us to pass additional state around as part of a computation. 
We define `State` instances representing atomic state operations and thread them together 
using `map` and `flatMap`. In this way we can model mutable state in a purely functional way, 
without using mutation.










