package com.github.keyno.scalaj

import com.typesafe.config._
import scalaj.http.Http

object ExampleApp extends scala.App {

  // setting config.
  val config            = ConfigFactory.load()
  val url               = config.getString("app.url")
  val connectionTimeout = config.getInt("app.connectionTimeOut")
  val readTimeout       = config.getInt("app.readTimeOut")

  var request = Http(url)
    //.headers(headers)
    .timeout(connTimeoutMs = connectionTimeout, readTimeoutMs = readTimeout)

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

  request = if (body.isEmpty) request.method("GET")
    else request.postData(body).method("POST")

  val response = request.execute()

  val statusCode = response.code
  val httpStatus = statusCode match {
    case 200 => "OK"
    case 201 => "CREATED"
    case 204 => "NO_CONTENT"
    case 401 => "UNAUTHORIZED"
    case 400 => "BAD_REQUEST"
    case 404 => "NOT_FOUND"
    case 500 => "INTERNAL_SERVER_ERROR"
    case _ => "OTHER_STATUS_CODE"
  }
  val bodyValue = response.body

  print(Response(statusCode, httpStatus, bodyValue))

  case class Response(statusCode: Int, status: String, body: String)
}
