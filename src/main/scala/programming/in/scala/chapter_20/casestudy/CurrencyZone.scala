package programming.in.scala.chapter_20.casestudy

/**
  * Created by denis.shuvalov on 12/04/2018.
  */
abstract class CurrencyZone {
  type Currency <: AbstractCurrency

  def make(x: Long): Currency
  val CurrencyUnit: Currency

  abstract class AbstractCurrency {
    val amount: Long
    def designation: String

    def + (that: Currency): Currency = make(this.amount + that.amount)
    def * (x: Long): Currency = make(this.amount * x)

    //The toString method also needs to be adapted to take subunits into account.
    //For instance, the sum of ten dollars and twenty three cents should print as a decimal number: 10.23 USD
    override def toString =
      (amount.toDouble / CurrencyUnit.amount.toDouble) formatted ("%." + decimals(CurrencyUnit.amount) + "f") + " " + designation

    //This method returns the number of decimal digits of a decimal power minus one. For instance, decimals(10) is 1, decimals(100) is 2
    private def decimals(n: Long): Int =
      if (n == 1) 0 else 1 + decimals(n / 10)

    //The from method takes an arbitrary currency as argument. This is expressed by its formal parameter type,
    //CurrencyZone#AbstractCurrency, which indicates that the argument passed as other must be an AbstractCurrency
    //type in some arbitrary and unknown CurrencyZone.
    def from(other: CurrencyZone#AbstractCurrency): Currency =
      make(math.round(other.amount.toDouble * Converter.exchangeRate(other.designation)(this.designation)))
  }

}


