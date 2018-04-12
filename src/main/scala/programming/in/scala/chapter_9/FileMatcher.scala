package programming.in.scala.chapter_9

import java.io.File

/**
  * Created by denis.shuvalov on 11/03/2018.
  */
object FileMatcher {
  private def filesHere = new File(".").listFiles

  private def filesMatching(matcher: String => Boolean) =
    for (file <- filesHere; if matcher(file.getName))
      yield file

  def filesEnding(query: String) = filesMatching(_.endsWith(query))
  def filesContaining(query: String) = filesMatching(_.contains(query))
  def filesRegex(query: String) = filesMatching(_.matches(query))

  //---------------------------------------------------------------------------------------

  //This code is already simplified, but it can actually be even shorter.
  //In this version of the method, the if clause now uses matcher to check the file name against the query.
  def filesMatchingV2(query: String, matcher: (String, String) => Boolean) = {
    for (file <- filesHere if matcher(file.getName, query))
      yield file
  }

  def filesEndingV2(query: String) = filesMatchingV2(query, _.endsWith(_)) //(fileName: String, query: String) => fileName.endsWith(query)
  def filesContainingV2(query: String) = filesMatchingV2(query, _.contains(_))
  def filesRegexV2(query: String) = filesMatchingV2(query, _.matches(_))

  /*
   * Experienced programmers will notice all of this repetition and wonder if it can be factored into a common
   * helper function. Doing it the obvious way does not work, however. You would like to be able to do the following:
   *
   *    def filesMatching(query: String, null) =
   *      for (file <- filesHere; if file.getName.null(query))
   *        yield file
   *
   *  This approach would work in some dynamic languages, but Scala does not allow pasting together code at runtime like this.
   */

  def filesEndingV1(query: String) = {
    for (file <- filesHere if file.getName.endsWith(query))
      yield file
  }

  def filesContainingV1(query: String) =
    for (file <- filesHere; if file.getName.contains(query))
      yield file

  def filesRegexV1(query: String) =
    for (file <- filesHere; if file.getName.matches(query))
      yield file


}
