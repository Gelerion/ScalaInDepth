package programming.in.scala.chapter_20.casestudy

/**
  * Created by denis.shuvalov on 12/04/2018.
  */
object Japan extends CurrencyZone {

  abstract class Yen extends AbstractCurrency {
    def designation = "JPY"
  }

  type Currency = Yen

  def make(yen: Long) = new Yen {
    val amount = yen
  }

  val Yen = make(1)
  val CurrencyUnit = Yen
}
