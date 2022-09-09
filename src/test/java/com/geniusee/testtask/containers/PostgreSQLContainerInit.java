package com.geniusee.testtask.containers;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class PostgreSQLContainerInit {

    public static void sampleInitFunction(Connection connection) throws LiquibaseException, SQLException {
        log.info("Container postgres:latest started on: {}",connection.getMetaData().getURL());
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase l = new Liquibase("classpath:db/changelog/db.changelog-master.xml", new ClassLoaderResourceAccessor(), database);
        l.update(new Contexts(), new LabelExpression());
    }
}
