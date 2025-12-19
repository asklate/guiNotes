package net.askearly.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.askearly.utils.PropertyUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class SQLiteConnectionPoolManager {

    private static final HikariDataSource dataSource;

    static {
        Properties properties = PropertyUtils.getProperties("db.properties");
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(properties.getProperty(properties.getProperty("datasource.url")));

        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(30000); // 30 seconds
        config.setIdleTimeout(60000); // 1 minute
        config.setMaxLifetime(1800000); // 30 minutes

        config.addDataSourceProperty("PRAGMA journal_mode", "WAL");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        // The pool manages getting/returning the single underlying connection
        return dataSource.getConnection();
    }

    public static void closeConnection() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}

