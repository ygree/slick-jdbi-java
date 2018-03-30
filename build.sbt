organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

lazy val root = (project in file("."))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= lombok ++ jdbi ++ h2 ++ junit ++ slick ++ scalaJava8Compat
  )

val lombok = Seq("org.projectlombok" % "lombok" % "1.16.18")

val jdbiVersion = "3.1.0"
val jdbi = Seq(
  "org.jdbi" % "jdbi3-sqlobject" % jdbiVersion,
  "org.jdbi" % "jdbi3-core" % jdbiVersion
)

val junit = Seq(
  "junit" % "junit" % "4.12" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test
)

val slick = Seq(
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3"
)

val scalaJava8Compat = Seq(

  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0"
)

val h2 = Seq("com.h2database" % "h2" % "1.4.196")

def common = Seq(
  // this setting required if use Sql Objects and bind query parameters to method parameters by their names
  javacOptions in compile += "-parameters"
)

