package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для работы с конфигурационными свойствами, который
 * автоматически удаляет лишние пробелы из значений
 */
public class PropertiesLoader {
    
    /**
     * Загружает свойства из указанного файла и удаляет лишние пробелы из значений
     * 
     * @param resourcePath путь к файлу свойств в ресурсах
     * @return объект Properties с загруженными свойствами
     * @throws IOException если файл не найден или произошла ошибка при чтении
     */
    public static Properties loadProperties(String resourcePath) throws IOException {
        Properties properties = new Properties();
        
        try (InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Файл ресурсов не найден: " + resourcePath);
            }
            
            // Загружаем свойства из файла
            properties.load(inputStream);
            
            // Создаем новый объект Properties для хранения свойств с удаленными пробелами
            Properties trimmedProperties = new Properties();
            
            // Перебираем все свойства и удаляем лишние пробелы
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                trimmedProperties.setProperty(key.trim(), value.trim());
            }
            
            return trimmedProperties;
        }
    }
} 