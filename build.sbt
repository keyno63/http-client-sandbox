val springVersion = "2.4.0"
val okhttpVersion = "4.9.0"

val skinnyVersion = "3.1.0"
val log4jVersion  = "1.7.30"

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
    ).map(_ % springVersion) ++
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
    ).map(_ % okhttpVersion) ++
      Seq(
        "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.0"
      ) ++
      Seq(
        "org.junit.jupiter" % "junit-jupiter-api" % "5.7.0",
        "org.assertj"       % "assertj-core"      % "3.18.1"
      ).map(_ % Test)
  )

lazy val `skinny` = (project in file("./scala/skinny"))
  .settings(config)
  .settings(
    libraryDependencies ++= Seq(
      "org.skinny-framework" %% "skinny-http-client"
    ).map(_ % skinnyVersion) ++ Seq(
      // log
      "org.slf4j" % "slf4j-log4j12" % log4jVersion % Test,
      "log4j"     % "log4j"         % "1.2.17"
    )
  )

lazy val `scalaj` = (project in file("./scala/scalaj"))
  .settings(config)
  .settings(
    libraryDependencies ++= Seq(
      "org.scalaj" %% "scalaj-http" % "2.4.2"
    )
  )

// config library.
lazy val `config` = Seq(
  libraryDependencies ++= Seq(
    "com.typesafe" % "config" % "1.4.1"
  )
)

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)
