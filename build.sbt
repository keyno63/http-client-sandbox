
lazy val base = (project in file("."))
  .settings(
    name := "http-client-sandbox",
    version := "0.1",
    scalaVersion := "2.13.2"
  )

lazy val `spring-web-flux` = (project in file("./spring-web-flux"))
  .settings(
    libraryDependencies ++= Seq(
      "org.springframework.boot" % "spring-boot-starter-webflux" % "2.4.0"
    )
  )

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)
