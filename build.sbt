val springVersion = "2.4.0"
val okhttpVersion = "4.9.0"

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

lazy val `okhttp` = (project in file("./java/okhttp"))
  .settings(
    libraryDependencies ++= Seq(
      "com.squareup.okhttp3" % "okhttp"
    ).map(_                          % okhttpVersion) ++
      Seq(
        "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.0"
      ) ++
      Seq(
        "org.junit.jupiter" % "junit-jupiter-api" % "5.7.0",
        "org.assertj"       % "assertj-core"      % "3.18.1"
      ).map(_ % Test)
  )

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)
