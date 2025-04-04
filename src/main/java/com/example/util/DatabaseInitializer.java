package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class DatabaseInitializer {
    
    // PostgreSQL адрес сервера по умолчанию
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "5432";
    private static final String DEFAULT_DB = "employee";
    private static boolean initialized = false;
    
    public static void initializeDatabase() {
        if (initialized) {
            System.out.println("База данных уже инициализирована. Пропускаем...");
            return; // Уже инициализировано, избегаем повторных вызовов
        }
        
        Properties properties = new Properties();
        try {
            // Загружаем настройки
            properties.load(DatabaseInitializer.class.getClassLoader().getResourceAsStream("database.properties"));
            String dbUrl = properties.getProperty("db.url", "jdbc:postgresql://localhost:5432/employee");
            String username = properties.getProperty("db.username", "postgres");
            String password = properties.getProperty("db.password", "postgres");
            
            System.out.println("Загружены параметры из файла database.properties:");
            System.out.println("- URL: " + dbUrl);
            System.out.println("- Пользователь: " + username);
            System.out.println("- Пароль: ****** (скрыт)");
            
            // Извлекаем хост, порт и имя базы данных из URL
            String host = DEFAULT_HOST;
            String port = DEFAULT_PORT;
            String dbName = DEFAULT_DB;
            
            if (dbUrl.contains("://")) {
                try {
                    String urlWithoutPrefix = dbUrl.split("://")[1];
                    String[] parts = urlWithoutPrefix.split("/");
                    
                    // Извлекаем хост и порт
                    String hostPart = parts[0];
                    if (hostPart.contains(":")) {
                        String[] hostPortParts = hostPart.split(":");
                        host = hostPortParts[0];
                        port = hostPortParts[1];
                    } else {
                        host = hostPart;
                    }
                    
                    // Извлекаем имя базы данных
                    if (parts.length > 1) {
                        // Удаляем параметры после '?', если есть
                        String dbPart = parts[1];
                        if (dbPart.contains("?")) {
                            dbName = dbPart.split("\\?")[0];
                        } else {
                            dbName = dbPart;
                        }
                    }
                    
                    System.out.println("Извлечено из URL:");
                    System.out.println("- Хост: " + host);
                    System.out.println("- Порт: " + port);
                    System.out.println("- База данных: " + dbName);
                } catch (Exception e) {
                    System.err.println("Ошибка при разборе URL: " + e.getMessage());
                    System.err.println("Используем значения по умолчанию.");
                    host = DEFAULT_HOST;
                    port = DEFAULT_PORT;
                    dbName = DEFAULT_DB;
                }
            } else {
                System.out.println("URL не содержит '://', используем значения по умолчанию.");
            }
            
            // Проверяем и создаем базу данных
            System.out.println("Начинаем создание базы данных, если необходимо...");
            createDatabaseIfNotExists(host, port, dbName, username, password);
            
            // Загружаем подготовленную схему и данные
            System.out.println("Начинаем загрузку схемы и данных, если необходимо...");
            loadSchema(dbUrl, username, password);
            
            initialized = true;
            System.out.println("Инициализация базы данных завершена успешно.");
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createDatabaseIfNotExists(String host, String port, String dbName, String username, String password) throws SQLException {
        // Подключаемся к PostgreSQL без указания базы данных
        String defaultUrl = "jdbc:postgresql://" + host + ":" + port + "/postgres";
        System.out.println("Подключение к PostgreSQL для проверки базы данных: " + defaultUrl);
        
        try {
            // Загружаем драйвер явно для уверенности
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке драйвера PostgreSQL: " + e.getMessage());
            throw new SQLException("Не удалось загрузить драйвер PostgreSQL", e);
        }
        
        try (Connection connection = DriverManager.getConnection(defaultUrl, username, password)) {
            // Проверяем существование базы данных
            boolean databaseExists = false;
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'");
                databaseExists = rs.next();
            }
            
            // Создаем базу данных, если она не существует
            if (!databaseExists) {
                System.out.println("База данных '" + dbName + "' не существует. Создаем...");
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate("CREATE DATABASE " + dbName);
                    System.out.println("База данных '" + dbName + "' успешно создана");
                }
            } else {
                System.out.println("База данных '" + dbName + "' уже существует");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к PostgreSQL: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Проверьте, что PostgreSQL запущен и доступен по адресу " + host + ":" + port);
            throw e;
        }
    }
    
    private static void loadSchema(String dbUrl, String username, String password) throws SQLException, IOException {
        System.out.println("Подключаемся к базе данных: " + dbUrl);
        // Подключаемся к созданной базе данных напрямую, без использования DatabaseConnection
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            System.out.println("Подключились к базе данных. Проверяем схему...");
            
            // Проверяем существование схемы public
            boolean schemaExists = false;
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_namespace WHERE nspname = 'public'");
                schemaExists = rs.next();
            }
            
            // Создаем схему, если она не существует
            if (!schemaExists) {
                System.out.println("Схема 'public' не существует. Создаем...");
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate("CREATE SCHEMA public");
                    System.out.println("Схема 'public' успешно создана");
                }
            } else {
                System.out.println("Схема 'public' уже существует");
            }
            
            // Устанавливаем права на схему
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("GRANT ALL ON SCHEMA public TO postgres");
                stmt.executeUpdate("GRANT ALL ON SCHEMA public TO public");
            }
            
            // Проверяем существование таблиц
            boolean tablesExist = false;
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'region'");
                tablesExist = rs.next();
            }
            
            // Загружаем схему и данные, если таблицы не существуют
            if (!tablesExist) {
                System.out.println("Таблицы не существуют. Загружаем схему и данные...");
                
                // Считываем SQL-скрипт из ресурсов
                StringBuilder sqlScript = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        DatabaseInitializer.class.getClassLoader().getResourceAsStream("database.sql")))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sqlScript.append(line).append("\n");
                    }
                }
                
                // Разделяем скрипт на отдельные команды и выполняем их
                String[] sqlCommands = sqlScript.toString().split(";");
                int commandCount = 0;
                try (Statement stmt = connection.createStatement()) {
                    for (String sql : sqlCommands) {
                        if (!sql.trim().isEmpty()) {
                            try {
                                stmt.executeUpdate(sql);
                                commandCount++;
                            } catch (SQLException e) {
                                System.err.println("Ошибка при выполнении SQL: " + sql);
                                System.err.println("Ошибка: " + e.getMessage());
                                // Продолжаем выполнение, несмотря на ошибки
                            }
                        }
                    }
                }
                System.out.println("Схема и данные успешно загружены. Выполнено " + commandCount + " команд SQL.");
            } else {
                System.out.println("Таблицы уже существуют");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к базе данных: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            throw e;
        }
    }
    
    public static void main(String[] args) {
        // Эту функцию можно вызвать для инициализации базы данных из консоли
        System.out.println("Запускаем инициализацию базы данных из main метода...");
        initializeDatabase();
        System.out.println("Завершено!");
    }
} 