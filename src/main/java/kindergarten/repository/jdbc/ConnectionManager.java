package main.java.kindergarten.repository.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionManager {

    private final String url;
    private final String user;
    private final String password;

    public ConnectionManager() {
        Properties props = new Properties();
        try (InputStream in = ConnectionManager.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException("Файл db.properties не найден в classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить db.properties", e);
        }
        this.url = props.getProperty("db.url");
        this.user = props.getProperty("db.user");
        this.password = props.getProperty("db.password");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void initSchema() {
        try (InputStream in = ConnectionManager.class.getClassLoader()
                .getResourceAsStream("schema.sql")) {
            if (in == null) {
                throw new RuntimeException("Файл schema.sql не найден в classpath");
            }
            String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
                for (String statement : sql.split(";")) {
                    String trimmed = statement.trim();
                    if (!trimmed.isEmpty()) {
                        stmt.execute(trimmed);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Ошибка при инициализации схемы БД", e);
        }
    }
}
