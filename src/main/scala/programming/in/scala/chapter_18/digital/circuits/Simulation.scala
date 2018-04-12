package programming.in.scala.chapter_18.digital.circuits

/**
  * Created by denis.shuvalov on 03/04/2018.
  */
abstract class Simulation {

  type Action = () => Unit

  case class WorkItem(time: Int, action: Action)

  private var curtime = 0
  def currentTime: Int = curtime

  private var agenda: List[WorkItem] = List()

  private def insert(ag: List[WorkItem], item: WorkItem): List[WorkItem] = {
    if(ag.isEmpty || item.time < ag.head.time) item :: ag
    else ag.head :: insert(ag.tail, item)
  }

  def afterDelay(delay: Int)(block: => Unit) = {
    val item = WorkItem(currentTime + delay, () => block)
    agenda = insert(agenda, item)
  }

  private def next() = {
    (agenda: @unchecked) match {
      //next method decomposes the current agenda with a pattern match into a front item, item, and a remaining list of work items, rest
      case item :: rest =>
        agenda = rest
        curtime = item.time
        item.action()
    }
  }

  def run() = {
    afterDelay(0) {
      println("*** simulation started, time = " + currentTime + " ***")
    }
    while (agenda.nonEmpty) next()
  }

  /*
  You might wonder how this framework could possibly support interesting simulations, if all it does is execute a
  list of work items? In fact the power of the simulation framework comes from the fact that actions stored in work
  items can themselves install further work items into the agenda when they are executed. That makes it possible to
  have long-running simulations evolve from simple beginnings.

  A discrete event simulation performs user-defined actions at specified times. The actions, which are defined
  by concrete simulation subclasses, all share a common type:

       type Action = () => Unit

  This statement defines Action to be an alias of the type of procedure that takes an empty parameter list and returns
  Unit. Action is a type member of class Simulation. You can think of it as a more readable name for type () => Unit.

  The time at which an action is performed is simulated time; it has nothing to do with the actual "wall clock" time.
  Simulated times are represented simply as integers. The current simulated time is kept in a private variable:

      private var curtime: Int = 0

  The variable has a public accessor method, which retrieves the current time:

      def currentTime: Int = curtime

  This combination of private variable with public accessor is used to make sure that the current time cannot be
  modified outside the Simulation class. After all, you don't usually want your simulation objects to manipulate the
  current time, except possibly if your simulation models time travel.

      case class WorkItem(time: Int, action: Action)

  We made the WorkItem class a case class because of the syntactic conveniences this entails: You can use the factory
  method, WorkItem, to create instances of the class, and you get accessors for the constructor parameters time and
  action for free. Note also that class WorkItem is nested inside class Simulation. Nested classes in Scala are
  treated similarly to Java.

  The Simulation class keeps an agenda of all remaining work items that have not yet been executed. The work items
  are sorted by the simulated time at which they have to be run:

      private var agenda: List[WorkItem] = List()

  The agenda list will be kept in the proper sorted order by the insert method, which updates it. You can see insert
  being called from afterDelay, which is the only way to add a work item to the agenda:

      def afterDelay(delay: Int)(block: => Unit) = {
        val item = WorkItem(currentTime + delay, () => block)
        agenda = insert(agenda, item)
      }
   */

}
