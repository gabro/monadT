package monadt

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Example4 {
  case class User(name: String)

  def checkUserExists(id: String): Future[Option[User]] =
    Future.successful(Some(User("gabro")))

  def checkCanBeUpdated(u: User): Future[Boolean] =
    Future.successful(true)

  def updateUserOnDb(u: User): Future[User] =
    Future.successful(u)

  def updateUser(u: User): Future[Option[User]] =
    checkUserExists("foo").flatMap { maybeUser =>
      maybeUser match {
        case Some(user) => checkCanBeUpdated(user).flatMap { canBeUpdated =>
          if (canBeUpdated) {
            updateUserOnDb(user).map(Some(_))
          } else {
            Future.successful(None)
          }
        }
        case None => Future.successful(None)
      }
    }
}
