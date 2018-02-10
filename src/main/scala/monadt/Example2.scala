package monadt

import cats.data.OptionT
import cats.instances.future._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Example2 {
  case class User(name: String)
  case class Address(city: String)

  def getUser(name: String): Future[Option[User]] =
    Future.successful(Some(User("gabro")))

  def getAddress(user: User): Future[Option[Address]] =
    Future.successful(Some(Address("Milan")))

  def getCityNoMonadT: Future[Option[String]] =
    for {
      maybeUser <- getUser("gabro")
      maybeCity <- maybeUser match {
        case Some(user) => getAddress(user).map(_.map(_.city))
        case None       => Future.successful(None)
      }
    } yield maybeCity

  def getCityWithMonadT: Future[Option[String]] =
    (for {
      user <- OptionT(getUser("gabro"))
      address <- OptionT(getAddress(user))
    } yield address.city).value
}
