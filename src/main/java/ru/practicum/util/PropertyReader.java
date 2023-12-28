package ru.practicum.util;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Singleton
public class PropertyReader {

    private final Properties properties;

    public PropertyReader(String filePath) {
        properties = new Properties();
        loadProperties(filePath);
    }

    private void loadProperties(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибок, если не удалось загрузить свойства
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        // Пример использования класса PropertyReader
        String filePath = "path/to/your/config.properties"; // Укажите путь к вашему файлу свойств
        PropertyReader propertyReader = new PropertyReader(filePath);

        // Получение значения по ключу
        String apiKey = propertyReader.getProperty("api.key");
        System.out.println("API Key: " + apiKey);
    }
}