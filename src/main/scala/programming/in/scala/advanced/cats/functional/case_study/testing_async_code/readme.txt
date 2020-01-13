We’ll start with a straightforward case study: how to simplify unit tests for asynchronous code by making them synchronous.

This case study provides an example of how Cats can help us abstract over different computational scenarios.
We used the Applicative type class to abstract over asynchronous and synchronous code.
Leaning on a functional abstraction allows us to specify the sequence of computations we want to perform without
worrying about the details of the implementation.

Type classes like Functor, Applicative, Monad, and Traverse provide abstract implementations of patterns
such as mapping, zipping, sequencing, and iteration. The mathematical laws on those types ensure that they
work together with a consistent set of semantics.

We used Applicative in this case study because it was the least powerful type class that did what we needed.
If we had required flatMap, we could have swapped out Applicative for Monad. If we had needed to abstract over
different sequence types, we could have used Traverse. There are also type classes
like ApplicativeError and MonadError that help model failures as well as successful computations.