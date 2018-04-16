package programming.in.scala.chapter_21

/**
  * Created by denis.shuvalov on 16/04/2018.
  */
class DebugImplicit {
 //----------------------------------------------------------------------------------------------------------------------
  //For instance, assume that you mistakenly took wrapString to be a conversion from Strings to Lists, instead of IndexedSeqs. You would wonder why the following code does not work:
  //
  //  scala> val chars: List[Char] = "xyz"
  //  <console>:24: error: type mismatch;
  //   found   : String("xyz")
  //   required: List[Char]
  //         val chars: List[Char] = "xyz"
  //                                 ^
  //Again, it helps to write the wrapString conversion explicitly to find out what went wrong:
  //
  //  scala> val chars: List[Char] = wrapString("xyz")
 //----------------------------------------------------------------------------------------------------------------------
  //When you are debugging a program, it can sometimes help to see what implicit conversions the compiler is inserting.
  //The -Xprint:typer option to the compiler is useful for this. If you run scalac with this option, the compiler will
  //show you what your code looks like after all implicit conversions have been added by the type checker.
 //----------------------------------------------------------------------------------------------------------------------
}
