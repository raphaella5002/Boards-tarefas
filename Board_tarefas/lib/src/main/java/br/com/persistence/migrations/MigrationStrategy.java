package br.com.persistence.migrations;

import com.mysql.cj.jdbc.JdbcConnection;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import static br.com.persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class MigrationStrategy {

    private final Connection connection;

    public MigrationStrategy(Connection connection) {
        this.connection = connection;
    }

    public void executeMigrations(){

        var originalOut = System.out;
        var originalErr = System.err;

        try(var fos = new FileOutputStream("liquibase.log")){

            System.setOut(new PrintStream(fos));
            System.setErr(new PrintStream(fos));
            try(var connection = getConnection();
                var database = DatabaseFactory.getInstance()
                        .findCorrectDatabaseImplementation(new JdbcConnection(connection));
                ){
                var liquibase = new Liquibase(
                        "/db/changelog/db.changelog-master.yml",
                        new ClassLoaderResourceAccessor(),
                        database);
                liquibase.update();

            } catch (SQLException | LiquibaseException e) {
                e.printStackTrace();
                System.setErr(originalErr);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }
}
