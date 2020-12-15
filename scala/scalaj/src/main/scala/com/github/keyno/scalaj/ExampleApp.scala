package com.github.keyno.scalaj

import com.typesafe.config._
import scalaj.http.{Http, HttpRequest}

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

  val request = createRequest(url, body, connectionTimeout, readTimeout)

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

  def createRequest(url: String, body: String = "", connectionTimeout: Int = 5000, readTimeout: Int = 5000): HttpRequest = {
    val baseRequest = Http(url)
      //.headers(headers)
      .timeout(connTimeoutMs = connectionTimeout, readTimeoutMs = readTimeout)

    if (body.isEmpty) baseRequest.method("GET")
    else baseRequest
      .postData(body)

      //.header("User-Agent", "") // remove UA header challenge
      .headers(Seq("User-Agent" -> "", "content-type" -> "application/json"))
  }

  case class Response(statusCode: Int, status: String, body: String)
}
