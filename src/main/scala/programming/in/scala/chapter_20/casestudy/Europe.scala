package programming.in.scala.chapter_20.casestudy

/**
  * Created by denis.shuvalov on 12/04/2018.
  */
object Europe extends CurrencyZone {
  abstract class Euro extends AbstractCurrency {
    def designation = "EUR"
  }

  override type Currency = Euro

  override def make(x: Long): Euro = new Euro { val amount: Long = x }

  val Cent = make(1)
  val Euro = make(100)
  val CurrencyUnit = Euro
}
