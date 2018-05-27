package programming.in.scala.chapter_35

import swing._

/**
  * Created by denis.shuvalov on 24/05/2018.
  */
object Main extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "ScalaSheet"
    contents = new Spreadsheet(100, 26)
  }
}
