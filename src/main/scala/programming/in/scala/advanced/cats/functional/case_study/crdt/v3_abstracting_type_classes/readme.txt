We’ve created a generic GCounter that works with any value that has instances of BoundedSemiLattice and CommutativeMonoid.
However we’re still tied to a particular representation of the map from machine IDs to values. There is no need to
have this restriction, and indeed it can be useful to abstract away from it. There are many key-value stores
that we want to work with, from a simple Map to a relational database.

If we define a GCounter type class we can abstract over different concrete implementations. This allows us to,
for example, seamlessly substitute an in-memory store for a persistent store when we want to change performance and durability tradeoffs.

There are a number of ways we can implement this. One approach is to define a GCounter type class with dependencies on
CommutativeMonoid and BoundedSemiLattice. We define this as a type class that takes a type constructor with two
type parameters represent the key and value types of the map abstraction.