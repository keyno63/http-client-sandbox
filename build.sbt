val springVersion = "2.4.0"

lazy val base = (project in file("."))
  .settings(
    name := "http-client-sandbox",
    version := "0.1",
    scalaVersion := "2.13.2"
  )

lazy val `spring-web-flux` = (project in file("./java/spring-web-flux"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      "org.springframework.boot" % "spring-boot-starter-webflux"
    ).map(_                         % springVersion) ++
      Seq(
        "org.springframework.boot"  % "spring-boot-starter-test"       % springVersion   % Test,
        "org.springframework.cloud" % "spring-cloud-contract-wiremock" % "2.2.5.RELEASE" % Test
          excludeAll "org.junit.vintage:junit-vintage-engine"
      )
  )

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)
