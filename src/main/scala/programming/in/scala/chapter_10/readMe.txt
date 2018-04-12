A TWO-DIMENSIONAL LAYOUT LIBRARY

As a running example in this chapter, we'll create a library for building and rendering two-dimensional layout elements.
Each element will represent a rectangle filled with text. For convenience, the library will provide factory methods
named "elem" that construct new elements from passed data. For example, you'll be able to create a layout element
containing a string using a factory method with the following signature:

  elem(s: String): Element

As you can see, elements will be modeled with a type named Element. You'll be able to call above or beside on an element,
passing in a second element, to get a new element that combines the two. For example, the following expression would
construct a larger element consisting of two columns, each with a height of two:

  val column1 = elem("hello") above elem("***")
  val column2 = elem("***") above elem("world")
  column1 beside column2

Printing the result of this expression would give you:

  hello ***
   *** world