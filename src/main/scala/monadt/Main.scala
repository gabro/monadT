package monadt

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

object Main extends App {

  val results = Await.result(
    Future.sequence(
      List(
        Example1.getCity,
        Example2.getCityNoMonadT,
        Example3.getLameNickname,
        Example4.updateUser(Example4.User("gabro")),
        Example5.updateUser(Example5.User("gabro")),
        Example6.updateUser(Example6.User("gabro")),
      )),
    1.second
  )

  results.zipWithIndex.foreach {
    case (res, i) => println(s"Example ${i + 1}: $res")
  }

}
