enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)

name := "re.tool"
organization := "net.gaillourdet"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"

proguardSettings

// to configure proguard for scala, see
// http://proguard.sourceforge.net/manual/examples.html#scala
//ProguardKeys.options in Proguard ++= Seq(
//  "--dontwarn scala.**",
//  "--dontwarn ch.qos.**"
//  // ...
//)

// specify the entry point for a standalone app
ProguardKeys.options in Proguard += ProguardOptions.keepMain("net.gaillourdet.re_tool.Main")

// Java 8 requires a newer version of proguard than sbt-proguard's default
ProguardKeys.proguardVersion in Proguard := "5.2.1"

// filter out jar files from the list of generated files, while
// keeping non-jar output such as generated launch scripts
mappings in Universal := (mappings in Universal).value.
  filter {
    case (file, name) =>  ! name.endsWith(".jar")
  }

// ... and then append the jar file emitted from the proguard task to
// the file list
mappings in Universal ++= (ProguardKeys.proguard in Proguard).
  value.map(jar => jar -> ("lib/" +jar.getName))

// point the classpath to the output from the proguard task
scriptClasspath := (ProguardKeys.proguard in Proguard).value.map(jar => jar.getName)
