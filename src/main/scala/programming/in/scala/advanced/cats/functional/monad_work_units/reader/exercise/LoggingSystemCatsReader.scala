package programming.in.scala.advanced.cats.functional.monad_work_units.reader.exercise

import cats.data.Reader
import cats.syntax.applicative._ // for pure

/**
  * The classic use of Readers is to build programs that accept a configuration as a parameter.
  * Let’s ground this with a complete example of a simple login system.
  * Our configuration will consist of two databases: a list of valid users and a list of their passwords:
  */
case class Db(usernames: Map[Int, String], passwords: Map[String, String])

object LoggingSystemCatsReader {
  //Start by creating a type alias DbReader for a Reader that consumes a Db as input.
  //This will make the rest of our code shorter.
  type DbReader[A] = Reader[Db, A]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(db => db.usernames.get(userId))

  def checkPassword(username: String, password: String): DbReader[Boolean] =
    Reader(db => db.passwords.get(username).contains(password))

  def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    for {
      username <- findUsername(userId)
      passwordOk <- username.map(checkPassword(_, password)).getOrElse(false.pure[DbReader])
    } yield passwordOk


  def main(args: Array[String]): Unit = {
    val users = Map(
      1 -> "dade",
      2 -> "kate",
      3 -> "margo"
    )

    val passwords = Map(
      "dade"  -> "zerocool",
      "kate"  -> "acidburn",
      "margo" -> "secret"
    )

    val db = Db(users, passwords)

    println(checkLogin(1, "zerocool").run(db))
    // res10: cats.Id[Boolean] = true

    println(checkLogin(4, "davinci").run(db))
    // res11: cats.Id[Boolean] = false
  }


}
