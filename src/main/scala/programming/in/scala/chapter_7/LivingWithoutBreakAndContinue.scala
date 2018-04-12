package programming.in.scala.chapter_7

/**
  * Created by denis.shuvalov on 07/03/2018.
  */
object LivingWithoutBreakAndContinue extends App {

  // JAVA ftw!
/*  int i = 0;
  boolean foundIt = false;
  while (i < args.length) {
    if (args[i].startsWith("-")) {
      i = i + 1;
      continue;
    }
    if (args[i].endsWith(".scala")) {
      foundIt = true;
      break;
    }
    i = i + 1;
  }*/

  //Simple scala code:
  var i = 0
  var foundIt = false
  while (i < args.length && !foundIt) {
    if(!args(i).startsWith("-")) {
      if(args(i).endsWith(".scala")) foundIt = true
    }
    i = i + 1
  }

  //If you wanted to get rid of the vars one approach you could try is to rewrite the loop as a recursive function.
  def searchFrom(i: Int): Int =
    if(i >= args.length) -1
    else if(args(i).startsWith("-")) searchFrom(i + 1)
    else if(args(i).endsWith(".scala")) i
    else searchFrom(i + 1)

  //The version gives a human-meaningful name to what the function does, and it uses recursion to substitute for looping
  val x = searchFrom(0)

  //Note
  //The Scala compiler will not actually emit a recursive function for the code. Because all of the recursive calls
  //are in tail-call position, the compiler will generate code similar to a while loop. Each recursive call will be
  //implemented as a jump back to the beginning of the function

  // --------------------------------------------
  //Class Breaks in package scala.util.control offers a break method, which can be used to exit an
  //enclosing block that's marked with breakable.

  import scala.util.control.Breaks._
  import java.io._

  val in = new BufferedReader(new InputStreamReader(System.in))

  breakable {
    while(true) {
      println("? ")
      if(in.readLine() == "") break
    }
  }

}
