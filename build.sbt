name := "dan"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.13.0" % "test",

  "org.apache.spark" % "spark-core" % "1.6.0"
)
