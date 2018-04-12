package programming.in.scala.chapter_13

/**
  * Created by denis.shuvalov on 18/03/2018.
  */
class ImplicitImports {

  //Scala adds some imports implicitly to every program. In essence, it is as if the following three import
  //clauses had been added to the top of every source file with extension ".scala":
  //  import java.lang._ // everything in the java.lang package
  //  import scala._     // everything in the scala package
  //  import Predef._    // everything in the Predef object

  //These three import clauses are treated a bit specially in that later imports overshadow earlier ones. For instance,
  //the StringBuilder class is defined both in package scala and, from Java version 1.5 on, also in package java.lang.
  //Because the scala import overshadows the java.lang import, the simple name StringBuilder will refer
  //to scala.StringBuilder, not java.lang.StringBuilder.

}
