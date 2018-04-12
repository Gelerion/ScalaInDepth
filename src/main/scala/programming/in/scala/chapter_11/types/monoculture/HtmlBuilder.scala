package programming.in.scala.chapter_11.types.monoculture

/**
  * Created by denis.shuvalov on 13/03/2018.
  */
object HtmlBuilder {

  //That type signature has four strings in it! Such stringly typed code is technically strongly typed,
  //but since everything in sight is of type String, the compiler cannot help you detect the use of one when you meant to write the other.

  //  def title(text: String, anchor: String, style: String): String =
//    s"<a id='$anchor'><h1 class='$style'>$text</h1></a>"

  def title(text: Text, anchor: Anchor, style: Style): Html =
    new Html(
      s"<a id='${anchor.value}'>" +
        s"<h1 class='${style.value}'>" +
        text.value +
        "</h1></a>"
    )
}

class Anchor(val value: String) extends AnyVal
class Style(val value: String) extends AnyVal
class Text(val value: String) extends AnyVal
class Html(val value: String) extends AnyVal
