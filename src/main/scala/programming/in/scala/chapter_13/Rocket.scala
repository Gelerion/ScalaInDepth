package programming.in.scala.chapter_13

/**
  * Created by denis.shuvalov on 18/03/2018.
  *
  * Scala's access rules privilege companion objects and classes when it comes to private or protected accesses.
  * A class shares all its access rights with its companion object and vice versa.
  */
class Rocket {
  import Rocket.fuel
  private def canGoHomeAgain = fuel > 20
}

object Rocket {
  private def fuel = 10
  def chooseStrategy(rocket: Rocket) = {
    if (rocket.canGoHomeAgain)
      goHome()
    else
      pickAStar()
  }
  def goHome() = {}
  def pickAStar() = {}
}