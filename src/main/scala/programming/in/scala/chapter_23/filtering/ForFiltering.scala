package programming.in.scala.chapter_23.filtering

/**
  * Created by denis.shuvalov on 17/04/2018.
  */
object ForFiltering extends App {
  val lara = Person("Lara", isMale = false)
  val bob = Person("Bob", isMale = true)
  val julie = Person("Julie", isMale = false, lara, bob)
  val lena = Person("Lena", isMale = false)
  val persons = List(lara, bob, julie, lena)

  //Now, say you want to find out the names of all pairs of mothers and their children in that list.
  persons filterNot (p => p.isMale) /*filterNot (_.children.isEmpty)*/ flatMap (
    mother => mother.children map (child => (mother.name, child.name))) foreach (println(_))

  //You could optimize this example a bit by using a withFilter call instead of filter.
  //This would avoid the creation of an intermediate data structure for female persons:

  persons withFilter (p => !p.isMale) flatMap (p => p.children map (c => (p.name, c.name)))

  //These queries do their job, but they are not exactly trivial to write or understand.
  //Is there a simpler way? In fact, there is. Remember the for expressions
  for (p <- persons; if !p.isMale; c <- p.children)
    yield (p.name, c.name)

  //However, the last two queries are not as dissimilar as it might seem. In fact, it turns out that the Scala
  //compiler will translate the second query into the first one. More generally, all for expressions that yield a
  //result are translated by the compiler into combinations of invocations of the higher-order methods map, flatMap,
  //and withFilter. All for loops without yield are translated into a smaller set of higher-order functions:
  //just withFilter and foreach.
}

case class Person(name: String, isMale: Boolean, children: Person*)
