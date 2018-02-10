package monadt

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Example1 {
  case class User(name: String)
  case class Address(city: String)

  def getUser(name: String): Future[User] = Future.successful(User("gabro"))
  def getAddress(user: User): Future[Address] = Future.successful(Address("Milan"))

  def getCity: Future[String] =
    for {
      gab <- getUser("gabro")
      address <- getAddress(gab)
    } yield address.city
}
