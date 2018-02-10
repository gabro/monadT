package monadt

import cats.data.EitherT
import cats.instances.future._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Example6 {
  case class User(name: String)
  case class MyError(msg: String)

  type ResultT[F[_], A] = EitherT[F, MyError, A]
  type FutureResult[A] = ResultT[Future, A]

  def checkUserExists(name: String): FutureResult[User] = EitherT.fromEither {
    if (name == "gabro") Right(User("gabro"))
    else Left(MyError("user does not exists"))
  }

  def checkCanBeUpdated(u: User): FutureResult[User] = EitherT.fromEither {
    if (u.name == "gabro") Right(u)
    else Left(MyError("only gabro can be updated"))
  }

  def updateUserOnDb(u: User): FutureResult[User] = EitherT {
    if (u.name == "gabro") Future.successful(Right(u))
    else Future.successful(Left(MyError("only gabro can be updated")))
  }

  def updateUser(u: User): Future[Either[MyError, User]] =
    (for {
      user <- checkUserExists(u.name)
      _ <- checkCanBeUpdated(user)
      updatedUser <- updateUserOnDb(user)
    } yield updatedUser).value
}
