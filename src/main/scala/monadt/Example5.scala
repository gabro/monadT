package monadt

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Example5 {
  case class User(name: String)
  case class MyError(msg: String)

  def checkUserExists(id: String): Future[Option[User]] =
    Future.successful(Some(User("gabro")))

  def checkCanBeUpdated(u: User): Future[Boolean] =
    Future.successful(true)

  def updateUserOnDb(u: User): Future[User] =
    Future.successful(u)

  def updateUser(u: User): Future[Either[MyError, User]] =
    checkUserExists("foo").flatMap { maybeUser =>
      maybeUser match {
        case Some(user) => checkCanBeUpdated(user).flatMap { canBeUpdated =>
          if (canBeUpdated) {
            updateUserOnDb(user).map(Right(_))
          } else {
            Future.successful(Left(MyError("user cannot be updated")))
          }
        }
        case None => Future.successful(Left(MyError("user does not exist")))
      }
    }
}
