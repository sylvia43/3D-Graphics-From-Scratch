import sbt.Keys._

name := "Graphics"
scalaVersion := "2.11.8"

lazy val root = project.in(file("."))
  .aggregate(frontend, modelJvm, modelJs)
  .dependsOn(frontend, modelJvm, modelJs)
  .settings(publish := {}, publishLocal := {})

lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

lazy val frontend = project.in(file("module_frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "Graphics-frontend",
    scalaJSUseRhino in Global := false,
    persistLauncher in Compile := true,
    persistLauncher in Test := false,
    skip in packageJSDependencies := false,
    libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % "test",
    jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
    jsDependencies += RuntimeDOM
  )
  .dependsOn(modelJs)

lazy val modelProject = crossProject.in(file("module_model"))
  .settings(commonSettings: _*)
  .settings(
    name := "Graphics-model",
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % "test",
    testFrameworks += new TestFramework("utest.runner.Framework"),
    testOptions in Test += Tests.Argument("-SD")
  )
  .jsSettings(
  )

lazy val modelJvm = modelProject.jvm
lazy val modelJs = modelProject.js

testFrameworks += new TestFramework("utest.runner.Framework")
testOptions in Test += Tests.Argument("-SD")