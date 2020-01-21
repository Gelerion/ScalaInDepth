Generalisation
We’ve now created a distributed, eventually consistent, increment-only counter. This is a useful achievement but we
don’t want to stop here. In this section we will attempt to abstract the operations in the GCounter so it will work
with more data types than just natural numbers.

The GCounter uses the following operations on natural numbers:

 - addition (in increment and total);
 - maximum (in merge);
 - and the identity element 0 (in increment and merge).

You can probably guess that there’s a monoid in here somewhere, but let’s look in more detail at the properties we’re relying on.
 - associative
the binary operation + must be associative: (a + b) + c == a + (b + c)
 - identity
the empty element must be an identity: 0 + a == a + 0 == a
 - commutativity
a + b = b + a e.g. 3 − 5 ≠ 5 − 3 is not
 - idempotent

We need an identity in increment to initialise the counter. We also rely on associativity to ensure the specific
sequence of merges gives the correct value.

In total we implicitly rely on associativity and commutativity to ensure we get the correct value no matter what
arbitrary order we choose to sum the per-machine counters. We also implicitly assume an identity, which allows us
to skip machines for which we do not store a counter.

The properties of merge are a bit more interesting. We rely on commutativity to ensure that machine A merging with
machine B yields the same result as machine B merging with machine A. We need associativity to ensure we obtain
the correct result when three or more machines are merging data. We need an identity element to initialise empty counters.
Finally, we need an additional property, called idempotency, to ensure that if two machines hold the same data in a
per-machine counter, merging data will not lead to an incorrect result. Idempotent operations are ones that return
the same result again and again if they are executed multiple times. Formally, a binary operation max is idempotent
if the following relationship holds: a max a = a

Method	    Identity	Commutative	Associative	Idempotent
increment	Y	        N	        Y	        N
merge	    Y	        Y	        Y	        Y
total    	Y	        Y	        Y	        N

 - increment requires a monoid;
 - total requires a commutative monoid;
 - merge required an idempotent commutative monoid, also called a bounded semilattice