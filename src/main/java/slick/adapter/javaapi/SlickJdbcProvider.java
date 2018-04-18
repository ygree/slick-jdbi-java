package slick.adapter.javaapi;


import java.sql.SQLException;
import java.util.concurrent.CompletionStage;

import com.typesafe.config.Config;
import slick.adapter.JdbcSession;
import slick.jdbc.JdbcBackend;
import slick.jdbc.JdbcBackend$;
import slick.jdbc.SimpleJdbcAction;

import scala.compat.java8.FutureConverters;

public class SlickJdbcProvider implements JdbcSession, AutoCloseable {

    private final Config config;

    JdbcBackend.DatabaseDef db;

    public SlickJdbcProvider(Config config) {
        this.config = config;

        this.db = JdbcBackend$.MODULE$.createDatabase(config, "");
    }

    @Override
    public void close() {
        db.close();
    }

    @Override
    public <T> CompletionStage<T> withConnection(ConnectionFunction<T> block) {
        SimpleJdbcAction simpleDBIO = new SimpleJdbcAction<>(
                (JdbcBackend.JdbcActionContext ctx) -> {
                        try {
                            return block.apply(ctx.connection());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                }
        );
        return FutureConverters.toJava(db.run(simpleDBIO));
    }


}
