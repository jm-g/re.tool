enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)

name := "re.tool"
organization := "net.gaillourdet"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"

