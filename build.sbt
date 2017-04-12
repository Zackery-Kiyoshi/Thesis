sbtPlugin := true
name := "ZackThesis"

fork := true
fork in test := true
//fork in (Test,run) := true

val buildSettings = Defaults.defaultSettings ++ Seq(
   javaOptions += "-Xmx15G"
)

javaOptions += "-Xmx15G"
javaOptions in test += "-Xmx8G"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.7"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.4.17"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.17"
libraryDependencies += "org.apache.commons" % "commons-math3" % "3.0"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
logBuffered := false
parallelExecution in Test := false



/*
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies += "com.storm-enroute" %% "scalameter-core" % "0.7"
*/


