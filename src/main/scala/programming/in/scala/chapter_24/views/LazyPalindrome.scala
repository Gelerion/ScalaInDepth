package programming.in.scala.chapter_24.views

/**
  * Created by denis.shuvalov on 30/04/2018.
  *
  * There are two reasons why you might want to consider using views. The first is performance. You have seen that by
  * switching a collection to a view the construction of intermediate results can be avoided. These savings can be quite
  * important. As another example, consider the problem of finding the first palindrome in a list of words. A palindrome
  * is a word that reads backwards the same as forwards. Here are the necessary definitions:
  */
object LazyPalindrome {

  val words = Seq("a", "b", "c")

  def isPalindrome(x: String) = x == x.reverse
  def findPalindrome(xs: Seq[String]) = xs find isPalindrome

  //Now, assume you have a very long sequence words and you want to find a palindrome in the first million words of
  //that sequence. Can you re-use the definition of findPalindrome? Of course, you could write:
  findPalindrome(words take 1000000)

  //This nicely separates the two aspects of taking the first million words of a sequence and finding a palindrome in it.
  //But the downside is that it always constructs an intermediary sequence consisting of one million words, even if the
  //first word of that sequence is already a palindrome. So potentially, 999,999 words are copied into the intermediary
  //result without being inspected at all afterwards. Many programmers would give up here and write their own specialized
  //version of finding palindromes in some given prefix of an argument sequence. But with views, you don't have to.
  //Simply write:
  findPalindrome(words.view take 1000000)
}
