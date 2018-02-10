package monadt

import cats.data.OptionT
import cats.instances.future._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Example3 {
  case class User(name: String)
  case class Address(city: String)

  def getUser(id: String): Future[Option[User]] =
    Future.successful(Some(User("gabro")))

  def getAge(user: User): Future[Int] =
    Future.successful(27)

  def getNickname(user: User): Option[String] =
    Some("gabro")

  def getLameNickname: Future[Option[String]] =
    (for {
      user <- OptionT(getUser("123"))
      age <- OptionT.liftF(getAge(user))
      nickname <- OptionT.fromOption(getNickname(user))
    } yield s"$nickname$age").value

}
