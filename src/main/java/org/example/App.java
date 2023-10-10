package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

public class App {
    public App() throws IOException {
    }

    public static void main(String[] args) throws IOException {

        String jsonFilePath = "D:\\Data\\JSON\\imports_8550000.json";
        String csvFilePath = "D:\\Data\\CSV\\imports_8550000.csv";


        /* Формат без заголовков */

        /*try {
            // Создание CSV файла и запись заголовков
            FileWriter csvWriter = new FileWriter(csvFilePath);
            PrintWriter printWriter = new PrintWriter(csvWriter);

            // Чтение JSON из файла построчно
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Парсинг JSON строки
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(line);

                    // Запись заголовков, если это первая строка
                    if (printWriter.checkError()) {
                        printWriter.write(getCsvHeaders(jsonObject) + "\n");
                    }

                    // Запись данных в CSV файл
                    csvWriter.write(getCsvRow(jsonObject) + "\n");
                }
            }

            csvWriter.flush();
            csvWriter.close();

            System.out.println("Конвертация завершена. Результат сохранен в " + csvFilePath);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static String getCsvHeaders(JSONObject jsonObject) {
        StringBuilder headers = new StringBuilder();
        for (Object key : jsonObject.keySet()) {
            headers.append(key).append(",");
        }
        return headers.substring(0, headers.length() - 1);
    }

    private static String getCsvRow(JSONObject jsonObject) {
        StringBuilder row = new StringBuilder();
        for (Object value : jsonObject.values()) {
            row.append(value).append(",");
        }
        return row.substring(0, row.length() - 1);
    }*/


        //Формат где заголовки в каждой строке.

        try {
            // Создание CSV файла и запись заголовков
            FileWriter csvWriter = new FileWriter(csvFilePath);
            PrintWriter printWriter = new PrintWriter(csvWriter);

            // Чтение JSON из файла построчно
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath))) {
                String line;
                JSONParser parser = new JSONParser();
                while ((line = reader.readLine()) != null) {
                    // Проверка наличия JSON строки
                    if (line.isEmpty()) {
                        continue; // Пропустить пустую строку
                    }

                    // Парсинг JSON строки
                    JSONObject jsonObject;
                    try {
                        jsonObject = (JSONObject) parser.parse(line);
                    } catch (ParseException e) {
                        System.out.println("Ошибка парсинга JSON строки: " + line);
                        continue; // Пропустить некорректную JSON строку
                    }

                    // Запись заголовков, если это первая строка
                    if (printWriter.checkError()) {
                        printWriter.write(getCsvHeaders(jsonObject) + "\n");
                    }

                    // Запись данных в CSV файл
                    csvWriter.write(getCsvRow(jsonObject) + "\n");
                }
            }

            csvWriter.flush();
            csvWriter.close();

            System.out.println("Конвертация завершена. Результат сохранен в " + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getCsvHeaders(JSONObject jsonObject) {
        StringBuilder headers = new StringBuilder();
        for (Object key : jsonObject.keySet()) {
            headers.append(key).append(",");
        }
        return headers.substring(0, headers.length() - 1);
    }

    private static String getCsvRow(JSONObject jsonObject) {
        StringBuilder row = new StringBuilder();
        for (Object key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof Double || value instanceof Integer || value instanceof Character) {
                value = value.toString();
            }
            row.append(key).append(": ").append(value).append(", ");
        }
        if (row.length() > 2) {
            return row.substring(0, row.length() - 2);
        } else {
            return row.toString();
        }
    }
}

