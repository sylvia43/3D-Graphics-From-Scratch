enablePlugins(ScalaJSPlugin)

name := "Graphics"
version := "1.0"
scalaVersion := "2.11.8"
scalaJSUseRhino in Global := false
persistLauncher in Compile := true
persistLauncher in Test := false

libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.0"

libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % "test"

skip in packageJSDependencies := false
jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js"
jsDependencies += RuntimeDOM

testFrameworks += new TestFramework("utest.runner.Framework")
testOptions in Test += Tests.Argument("-SD")