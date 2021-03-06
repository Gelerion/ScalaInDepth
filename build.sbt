name := "ScalaInDepth"

version := "0.1"
scalaVersion := "2.12.10"
libraryDependencies  += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0" withSources() withJavadoc()
libraryDependencies  += "org.scala-lang.modules" %% "scala-swing" % "2.0.3"
libraryDependencies  += "com.chuusai" %% "shapeless" % "2.3.3" withSources() withJavadoc()
libraryDependencies  += "org.typelevel" %% "cats-core" % "2.0.0" withSources() withJavadoc()

scalacOptions ++= Seq(
  "-encoding", "utf8", // Option and arguments on same line
  "-Ypartial-unification" //map over functions
//  "-Xfatal-warnings",  // New lines for each options
//  "-deprecation",
//  "-unchecked",
//  "-language:implicitConversions",
//  "-language:higherKinds",
//  "-language:existentials",
//  "-language:postfixOps"
)