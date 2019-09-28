# Type-based dependency injection 
In [this](https://kubuszok.com/2018/implicits-type-classes-and-extension-methods-part-1/) post, we will learn a bit about 
the first usage of implicits - type-based dependency injection as well as the biggest reason behind introducing them - the type classes

## Implicits
There are some rules regarding implicits:  
 - implicits cannot be ambiguous. You cannot define 2 implicits with the same type in the same scope,
 - however, class and its subclass are slightly different scopes. The rule of thumb is that in such cases,
   the closer scope would win. However, as always it is a bit more complex  
 - companion objects are also checked for implicits. If you have type X, then X companion will be checked for X implicits,
   but also for `F[X]` or `X[A]`  
 - a method can have one parameter list, which will be marked as implicit. As of now, it will always be the last
   parameter list. Each of params will become implicit value (so you cannot get params from the implicit scope,
   but not make them implicits themselves).  

## Polymorphism
 - **Subtyping** aka **subtype polymorphism**. Within one type, you have a subset of values making another type.   
OO languages usually implement it with interface inheritance. Because the subtype must fulfill all requirements and 
assertions of the supertype, the supertype can be treated as a generalization and common denominator of all its subtypes.  
(That is the ideal situation - OO programmers need to be actively reminded about Liskov substitution principle. 
A lot of bugs were born when someone overrode a method and created a class that shared an interface with the superclass 
but couldn’t be used as a drop-in replacement).
 - **Parametric polymorphism**, `List` of `String`s should behave the same as List of Ints. So why not let them have 
the same implementation? Of course, they have to have different types, so that we don’t put integers into a list of 
strings and vice versa. Type parameters let us define a whole implementation where instead of a type we put a placeholder,
and when we supplement it with a concrete type, the implementation is materialized. There is also another way to look 
at it - mathematical one. Functions, in general, are sets of arguments-values pairs. 
Types are also sets of values. And set might be both an argument and a value of a function. 
So it is possible to create a function taking a type as an argument and returning another type as a value. 
An example of such function (called `type constructor`) would be `List[_]`, which takes a parameter 
e.g. `String` and then returns a concrete type e.g. `List[String]`.
 - **Ad-hoc polymorphism** aka **operator overloading*. You have `*` (or `+`) operator and you want to be able to use 
if for multiplying: ints, longs, doubles, floats, complex numbers, vectors, matrices… For each type it would have a 
slightly different implementation, so you cannot just create one function that rules them all, and simply adjust 
the type (as is the case with parametric polymorphism), nor extract common behavior (what matrices multiplication 
has in common with multiplying scalars?).
As a matter of the fact, each overloaded function is actually a set of different functions, sharing the same name, 
where one that will be used is based on a type of the argument(s) (and their number aka arity).

# Type classes
The solution originally described in the paper How to make **ad-hoc polymorphism** less ad hoc works as follows:
- let us define a named contract. This contract would require, that for a type certain functions have to be 
defined and meet certain assertions
- when type declared itself as following the said contract, then somewhere in the scope of program required functions 
have to be defined and explicitly pointed to (and have to follow assertions)
- operators like `*`, `+` etc have assigned contracts, and are used as syntactic sugar for functions mentioned in their contracts
- one contract can inherit obligations from another contract and add to them  

Such contracts would be named **type classes**.    
Let us see this idea by the example of a monoid
```
A monoid is an algebra of elements that could be combined together.
```
We could define it formally as a triple `(A,⊕,0)`, where:
- we would require `⊕` to be binary operator over `A`:
```⊕: A × A -> A ```
- which is also associative:
`∀a,b,c∈A (a ⊕ b) ⊕ c = a ⊕ (b ⊕ c)`
- and has a neutral element 00
`∀a∈A a ⊕ 0 = 0 ⊕ a = a`  
  
Examples of such monoids would be: numbers with addition and 0, numbers with multiplication and 1, lists of the same 
type with list concatenation and an empty list, matrices of the same size with matrix addition with a matrix of zeros, 
square matrices of the same size with matrix multiplication and an identity matrix.

## Type classes in Scala
```scala
trait Monoid[A] {
  def empty: A
  def append(a1: A, a2: A): A
  def concat(list: List[A]): A = list.foldRight(empty)(append)
}

val intMonoid = new Monoid[Int] {
  def empty: Int = 0
  def append(a1: Int, a2: Int): Int = a1 + a2
}

def combineValues[T](
  map: Map[String, List[T]]
)(monoid: Monoid[T]): Map[String, T] =
  map.mapValues(monoid.concat)

combineValues(Map(
  "test1" -> List(1, 2, 3, 4, 5),
  "test2" -> List(6, 7, 8, 9, 0)
))(intMonoid)
```
Passing type classes could be made simpler even further. Since the most common use case is a type class with one parameter, 
you can declare them as context bound
```scala
def needMonoid[T](ts: List[T])(implicit monoid: Monoid[T]): T =
  monoid.concat(ts)
// could be written with context bound
def needMonoid[T: Monoid](ts: List[T]): T =
  implicitly[Monoid[T]].concat(ts)

def severalBoundArePossible[T : Bound1 : Bound2]: Unit = {
  implicitly[Bound1[T]]
  implicitly[Bound2[T]]
}
```
Importantly, `Shape2` is a more generic encoding than `Shape`. Any code that operates on a pair of `Double`s 
will be able to operate on a `Rectangle2` and vice versa. As Scala developers we tend to prefer semantic 
types like `Rectangle` and `Circle` to generic ones like `Rectangle2` and `Circle2` precisely because of their specialised nature.
However, in some cases generality is desirable. For example, if we’re serializing data to disk, we don’t care about 
the difference between a pair of `Double`s and a `Rectangle2`. We just write two numbers and we’re done.
