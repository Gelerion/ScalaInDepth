package programming.in.scala.chapter_21.typical.example

import java.awt.event.{ActionEvent, ActionListener}

import javax.swing.JButton

/**
  * Created by denis.shuvalov on 15/04/2018.
  */
object SwingActionListener extends App {

  val button = new JButton()
  //This code has a lot of information-free boilerplate. The fact that this listener is an ActionListener, the fact
  //that the callback method is named actionPerformed, and the fact that the argument is an ActionEvent are all implied
  //for any argument to addActionListener. The only new information here is the code to be performed, namely the call to println
  button.addActionListener(new ActionListener {
    override def actionPerformed(e: ActionEvent): Unit = {
      println("pressed!")
    }
  })

  //since 2.12
  button.addActionListener(_ => println("pressed!"))

  //for 2.11-

  //The first step is to write an implicit conversion between the two types.
  implicit def function2ActionListener(f: ActionEvent => Unit) =
    new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = f(e)
    }

  //This is a one-argument method that takes a function and returns an action listener. Like any other one-argument method,
  //it can be called directly and have its result passed on to another expression:
  button.addActionListener(
    function2ActionListener(
      (_: ActionEvent) => println("pressed!")
    )
  )

  //Because function2ActionListener is marked as implicit, it can be left out and the compiler will insert it automatically.
  //Here is the result:
  button.addActionListener(
    (_: ActionEvent) => println("pressed!")
  )

  //The way this code works is that the compiler first tries to compile it as is, but it sees a type error. Before giving
  //up, it looks for an implicit conversion that can repair the problem. In this case, it finds function2ActionListener.
  //It tries that conversion method, sees that it works, and moves on. The compiler works hard here so that the developer
  //can ignore one more fiddly detail. Action listener? Action event function? Either one will work—use the one that's
  //more convenient.
}
