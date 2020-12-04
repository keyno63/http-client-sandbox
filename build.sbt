
lazy val base = (project in file("."))
  .settings(
    name := "http-client-sandbox",
    version := "0.1",
    scalaVersion := "2.13.2"
  )

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)
