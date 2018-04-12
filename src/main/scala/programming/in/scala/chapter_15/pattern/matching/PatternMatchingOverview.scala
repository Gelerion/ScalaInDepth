package programming.in.scala.chapter_15.pattern.matching

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
class PatternMatchingOverview {
  //If you have programmed in a functional language before, then you will probably recognize pattern matching.
  //But case classes will be new to you. Case classes are Scala's way to allow pattern matching on objects without
  //requiring a large amount of boilerplate. Generally, all you need to do is add a single case keyword to
  //each class that you want to be pattern matchable.

  //This chapter starts with a simple example of case classes and pattern matching. It then goes through all of the
  //kinds of patterns that are supported, talks about the role of sealed classes, discusses the Option type, and shows
  //some non-obvious places in the language where pattern matching is used. Finally, a larger, more realistic
  //example of pattern matching is shown.

  def echoWhatYouGaveMe(x: Any): String = x match {

    // constant patterns
    case 0 => "zero"
    case true => "true"
    case "hello" => "you said 'hello'"
    case Nil => "an empty List"

    // sequence patterns
    case List(0, _, _) => "a three-element list with 0 as the first element"
    case List(1, _*) => "a list beginning with 1, having any number of elements"
    case Vector(1, _*) => "a vector starting with 1, having any number of elements"

    // tuples
    case (a, b) => s"got $a and $b"
    case (a, b, c) => s"got $a, $b, and $c"

    // constructor patterns
    //case Person(first, "Alexander") => s"found an Alexander, first name = $first"
    //case Dog("Suka") => "found a dog named Suka"

    // typed patterns
    case s: String => s"you gave me this string: $s"
    case i: Int => s"thanks for the int: $i"
    case f: Float => s"thanks for the float: $f"
    case a: Array[Int] => s"an array of int: ${a.mkString(",")}"
    case as: Array[String] => s"an array of strings: ${as.mkString(",")}"
    //case d: Dog => s"dog: ${d.name}"
    case list: List[_] => s"thanks for the List: $list"
    case m: Map[_, _] => m.toString

    // the default wildcard pattern
    case _ => "Unknown"
  }
}
