package br.com;

import br.com.persistence.migrations.MigrationStrategy;
import br.com.ui.MainMenu;

import java.sql.SQLException;

import static br.com.persistence.config.ConnectionConfig.getConnection;

public class main {
    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigrations();
        }
        new MainMenu().execute();
    }
}
