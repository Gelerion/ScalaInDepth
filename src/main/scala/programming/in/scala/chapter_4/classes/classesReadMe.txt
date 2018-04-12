Scala implicitly imports members of packages java.lang and scala, as well as the members of a singleton
object named Predef, into every Scala source file. Predef, which resides in package scala, contains many
useful methods. For example, when you say println in a Scala source file, you're actually invoking println on
Predef. (Predef.println turns around and invokes Console.println, which does the real work.) When you say
assert, you're invoking Predef.assert.

One difference between Scala and Java is that whereas Java requires you to put a public class in a file named after
the class—for example, you'd put class SpeedRacer in file SpeedRacer.java—in Scala, you can name .scala files anything
you want, no matter what Scala classes or code you put in them. In general in the case of non-scripts, however, it is
recommended style to name files after the classes they contain as is done in Java, so that programmers can more easily
 locate classes by looking at file names.