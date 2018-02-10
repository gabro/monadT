lazy val root = project.in(file("."))
  .settings(
    scalaVersion := "2.12.4",
    scalacOptions ++= List(
      "-Ywarn-unused:imports",
      "-Ypartial-unification",
      "-language:higherKinds"
    ),
    libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1"
  )
