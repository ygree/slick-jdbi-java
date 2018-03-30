package com.example.hello;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ContactsDao {

    /**
     * NOTE: @Bind parameter annotations can be avoided if javac -parameters compiler option is enabled.
     * In that case it will preserve arg names.
     */
    @SqlUpdate("insert into contacts(name) values(:name)")
    void insertContact(@Bind("name") String name);

}
