import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Steps {
  //Описываем шаги (запросы)
  def getUserList = {
    exec(
      http("get user")
        .get("api/v1/user")
        .check(status.is(200))
        .check(jsonPath("$[0].id").notNull.saveAs("recipientId"))
    )
  }

  def authentication = {
    exec(
      http("login")
        .post("api/v1/authenticate")
        .body(
          StringBody(
            """
              |{
              |"username": "admin",
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
        .get("api/v1/message")
        .header("Authorization", "Bearer ${token}")
        .check(status.is(200))
    )
  }

  def postMessage = {
    exec(
      http("post message")
        .post("api/v1/message")
        .body(
          StringBody(

            """ {
                  "text": "Hello, Gatling",
                  "recipient": ${recipientId}
                }""".stripMargin
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
        .put("api/v1/message/${messageId}")
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
        .get("api/v1/message/${messageId}")
        .header("Authorization", "Bearer ${token}")
        .header("Content-type", "application/json")
        .check(status.is(200))
    )
  }

  def deleteMessage = {
    exec(
      http("delete message")
        .delete("api/v1/message/${messageId}")
        .header("Authorization", "Bearer ${token}")
        .header("Content-type", "application/json")
        .check(status.is(200))
    )
  }
}
