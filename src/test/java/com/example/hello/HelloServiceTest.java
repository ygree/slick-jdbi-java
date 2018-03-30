package com.example.hello;

import com.typesafe.config.ConfigFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import slick.adapter.JdbiSession;
import slick.adapter.SlickJdbcProvider;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelloServiceTest {

    static SlickJdbcProvider db;

    @BeforeClass
    public static void setUp() {
        db = new SlickJdbcProvider(ConfigFactory.load().getConfig("h2mem1"));
    }

    @AfterClass
    public static void tearDown() {
        if (db != null) {
            db.close();
        }
    }

    @Test
    public void testHello() throws Exception {

        JdbiSession jdbiSession = new JdbiSession(db);

        jdbiSession.useHandle(h ->
            h.execute("create table IF NOT EXISTS contacts (name varchar(100))")
        ).toCompletableFuture().get(); // NOTE: blocking call for tests only

        jdbiSession.useHandle(h -> {
            h.attach(ContactsDao.class).insertContact("alpha");
            h.attach(ContactsDao.class).insertContact("beta");
            h.attach(ContactsDao.class).insertContact("gamma");
        }).toCompletableFuture().get(); // NOTE: blocking call for tests only

        List<Contact> result = jdbiSession.withHandle(h -> {
            List<Contact> list = h
                    .createQuery("select name from contacts")
                    .map((rs, ctx) -> new Contact(rs.getString("name")))
                    .list();

            return list;
        }).toCompletableFuture().get(); // NOTE: blocking call for tests only


        assertEquals(3, result.size());
    }




}

