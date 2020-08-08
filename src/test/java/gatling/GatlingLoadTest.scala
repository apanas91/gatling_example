import Scenario._
import io.gatling.http.Predef.http
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class GatlingLoadTest extends Simulation {
  //Грузим конфиги из application.cinf
  val config = ConfigFactory.load("application.conf")
  //Определяем базовый URL
  val httpConf = http
    .baseUrl(config.getString("conf.baseUrl"))
  setUp(
    //Стратегия нагрузки
    getUsers.
      inject(
        constantUsersPerSec(
          //Количество пользователей в секунду
          config.getInt("conf.userPerSec"))
          //Продолжительность теста (в секундах)
          during (config.getInt("conf.during") seconds)
      )
      .protocols(httpConf)
  )
}

