import io.gatling.core.Predef.scenario
import Steps._

object Scenario {
  //Добавляем в сценарий нужные шаги
  def getUsers = scenario ("first scenario")
    .exec(authentication)
    .exec(postMessage)
    .exec(getMessage)
    .exec(putMessage)
    .exec(getAllMessages)
    .exec(deleteMessage)
}