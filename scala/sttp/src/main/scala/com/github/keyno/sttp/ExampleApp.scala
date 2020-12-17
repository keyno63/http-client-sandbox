package com.github.keyno.sttp

import com.typesafe.config.ConfigFactory
import sttp.client._
import sttp.model.MediaType.ApplicationJson
import sttp.model.{Header, Method}

object ExampleApp extends scala.App {

  // setting config.
  val config            = ConfigFactory.load()
  val url               = config.getString("app.url")
  val connectionTimeout = config.getInt("app.connectionTimeOut")
  val readTimeout       = config.getInt("app.readTimeOut")

  // 自前の mock server 用の body.
  val body =
    """
      |{
      |  "v": "value",
      |  "c": [
      |    {
      |      "key1": "value_1",
      |      "key2": "value_2"
      |    }
      |  ]
      |}
      |""".stripMargin

  val request = basicRequest
    .post(uri"$url")
    .contentType(ApplicationJson)
    .body(body)
    //.header("User-Agent", "override ua header")
    .header("User-Agent", "override ua header")

  implicit val backend: SttpBackend[Identity, Nothing, NothingT] = HttpURLConnectionBackend()
  val response = request.send()

  println(response.body)
}
