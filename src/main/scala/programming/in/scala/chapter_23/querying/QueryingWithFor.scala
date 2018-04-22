package programming.in.scala.chapter_23.querying

/**
  * Created by denis.shuvalov on 22/04/2018.
  *
  * The for notation is essentially equivalent to common operations of database query languages.
  */
object QueryingWithFor extends App {
  val books: List[Book] =
    List(
      Book(
        "Structure and Interpretation of Computer Programs",
        "Abelson, Harold", "Sussman, Gerald J."
      ),
      Book(
        "Principles of Compiler Design",
        "Aho, Alfred", "Ullman, Jeffrey"
      ),
      Book(
        "Programming in Modula-2",
        "Wirth, Niklaus"
      ),
      Book(
        "Elements of ML Programming",
        "Ullman, Jeffrey"
      ),
      Book(
        "The Java Language Specification", "Gosling, James",
        "Joy, Bill", "Steele, Guy", "Bracha, Gilad"
      )
    )

  println("""find the titles of all books whose author's last name is "Gosling"""")
  println(
    for (book <- books;
         author <- book.authors if author.startsWith("Gosling"))
      yield book.title
  )

  println()

  println("""find the titles of all books that have the string "Program" in their title""")
  println(
    for (b <- books if (b.title indexOf "Program") >= 0)
      yield b.title
  )

  println()

  println("""find the names of all authors who have written at least two books in the database""")
  println(
    for (b1 <- books; b2 <- books if b1 != b2;
         a1 <- b1.authors; a2 <- b2.authors if a1 == a2)
      yield a1 //List(Ullman, Jeffrey, Ullman, Jeffrey) with duplicates
  )

  println()

  def removeDuplicates[A](xs: List[A]): List[A] = {
    if(xs.isEmpty) xs
    else
      xs.head :: removeDuplicates(
        xs.tail.filter(x => x != xs.head)
      )

    //or with for
    /*
      xs.head :: removeDuplicates(
       for (x <- xs.tail if x != xs.head) yield x
      )
     */
  }

}


case class Book(title: String, authors: String*)