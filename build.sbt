name := "bounding-box"
version := "0.1"
scalaVersion := "2.13.5"
enablePlugins(JavaAppPackaging)

val verScalaTest = "3.+"
lazy val libScalaTest = Seq(
  "org.scalatest" %% "scalatest" % verScalaTest % Test,
  "org.scalatest" %% "scalatest-funsuite" % verScalaTest % Test
)

libraryDependencies in ThisBuild ++= (
    libScalaTest
  )
