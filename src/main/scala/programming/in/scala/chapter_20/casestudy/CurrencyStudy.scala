package programming.in.scala.chapter_20.casestudy

/**
  * Created by denis.shuvalov on 12/04/2018.
  */
object CurrencyStudy extends App {
  val yens: Japan.Yen = Japan.Yen from US.Dollar * 100
  println(yens)

  val euro = Europe.Euro from yens
  println(euro) //75.95

  val dollar = US.Dollar from euro
  println(dollar) //99.95

  println(US.Dollar + US.make(100)) //2

  //US.Dollar + Europe.Euro error: type mismatch;
}
