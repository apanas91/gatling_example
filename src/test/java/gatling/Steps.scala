import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Steps {
  //Описываем шаги (запросы)
  def getUserList = {
    exec(
      http("get user")
        .get("user")
        .check(status.is(200))
    )
  }

  def authentication = {
    exec(
      http("login")
        .post("authenticate")
        .body(
          StringBody(
            """
              |{
              |"login": "admin",
              |"password": "admin"
              |}
            """.stripMargin
          )
        )
        .header("Content-type", "application/json")
        .check(status.is(200))
        .check(jsonPath("$.token").notNull.saveAs("token"))
    )
  }

  def getAllMessages = {
    exec(
      http("get messages")
        .get("message")
        .header("Authorization", "Bearer ${token}")
        .check(status.is(200))
    )
  }

  def postMessage = {
    exec(
      http("post message")
        .post("message")
        .body(
          StringBody(
            """ {"text": "Hello, Gatling"}""".stripMargin
          )
        )
        .header("Authorization", "Bearer ${token}")
        .header("Content-type", "application/json")
        .check(status.is(200))
        .check(jsonPath("$.id").saveAs("messageId"))
    )
  }

  def putMessage = {
    exec(
      http("put message")
        .put("message/${messageId}")
        .body(
          StringBody(
            """ { "text": "edited text" } """.stripMargin
          )
        )
        .header("Authorization", "Bearer ${token}")
        .header("Content-type", "application/json")
        .check(status.is(200))
    )
  }

  def getMessage = {
    exec(
      http("get message by id")
        .get("message/${messageId}")
        .header("Authorization", "Bearer ${token}")
        .header("Content-type", "application/json")
        .check(status.is(200))
    )
  }

  def deleteMessage = {
    exec(
      http("delete message")
        .delete("message/${messageId}")
        .header("Authorization", "Bearer ${token}")
        .header("Content-type", "application/json")
        .check(status.is(200))
    )
  }
}
