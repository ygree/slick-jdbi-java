package slick.adapter

import java.util.concurrent.CompletionStage

import com.typesafe.config.Config
import slick.adapter.JdbcSession.ConnectionFunction
import slick.jdbc.{JdbcBackend, SimpleJdbcAction}

import scala.compat.java8.FutureConverters._

/**
  * This class provides access to Slick managed DB connection pool.
  * It's an adaptor to the JdbcSession Java interface.
  */
class SlickJdbcProvider(config: Config) extends JdbcSession with AutoCloseable {

  private val db = JdbcBackend.createDatabase(config, "")

  private val SimpleDBIO = SimpleJdbcAction

  override def withConnection[T](block: ConnectionFunction[T]): CompletionStage[T] = {
    db.run {
      SimpleDBIO { ctx =>
        block(ctx.connection)
      }
    }.toJava
  }

  override def close(): Unit = {
    db.close()
  }
}
