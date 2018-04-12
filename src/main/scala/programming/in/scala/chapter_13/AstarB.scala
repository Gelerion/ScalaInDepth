package programming.in.scala.chapter_13

import java.util.regex
/**
  * Created by denis.shuvalov on 18/03/2018.
  *
  * To access the Pattern singleton object from the java.util.regex package, you can just say, regex.Pattern
  */
class AstarB {

  // Accesses java.util.regex.Pattern
  val pat = regex.Pattern.compile("a*b")

  //Imports in Scala can also rename or hide members. This is done with an import selector clause enclosed in
  //braces, which follows the object from which members are imported. Here are some examples:
  //  import Fruits.{Apple, Orange}

  //This imports just members Apple and Orange from object Fruits.
  //  import Fruits.{Apple => McIntosh, Orange}
  //A renaming clause is always of the form "<original-name> => <new-name>".

  //  import java.sql.{Date => SDate}
  //This imports the SQL date class as SDate, so that you can simultaneously import the normal Java date class as simply Date.

  //  import java.{sql => S}
  //This imports the java.sql package as S, so that you can write things like S.Date.

  //  import Fruits.{_}
  //This imports all members from object Fruits. It means the same thing as import Fruits._.

  //  import Fruits.{Apple => McIntosh, _}
  //This imports all members from object Fruits but renames Apple to McIntosh.

  //  import Fruits.{Pear => _, _}
  //This imports all members of Fruits except Pear. A clause of the form "<original-name> => _" excludes <original-name>
  //from the names that are imported. In a sense, renaming something to `_' means hiding it altogether. This is useful
  //to avoid ambiguities. Say you have two packages, Fruits and Notebooks, which both define a class Apple. If you
  //want to get just the notebook named Apple, and not the fruit, you could still use two imports on demand like this:
  //  import Notebooks._
  //  import Fruits.{Apple => _, _}
  //This would import all Notebooks and all Fruits, except for Apple.

}
