package programming.in.scala.chapter_20.casestudy

/**
  * Created by denis.shuvalov on 12/04/2018.
  *
  * The US object now defines the name Dollar in two ways. The type Dollar (defined by the abstract inner class named
  * Dollar) represents the generic name of the Currency valid in the US currency zone. By contrast, the value Dollar
  * (referenced from the val field named Dollar) represents a single US dollar, analogous to a one-dollar bill.
  */
object US extends CurrencyZone {
  abstract class Dollar extends AbstractCurrency {
    def designation = "USD"
  }

  override type Currency = Dollar

  override def make(cents: Long): Dollar = new Dollar { val amount: Long = cents }

  //The field Cent represents an amount of 1 US.Currency. It's an object analogous to a one-cent coin
  val Cent = make(1)
  //The field Dollar represents an amount of 100 US.Currency
  val Dollar = make(100)
  //The third field definition of CurrencyUnit specifies that the standard currency unit in the US zone is the Dollar
  val CurrencyUnit = Dollar
}
