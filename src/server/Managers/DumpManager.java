package server.Managers;

import coomon.Models.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import server.Adapters.LocalDateTimeAdapter;
import server.Adapters.NumberDeserializer;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class DumpManager {

        private final File dataFile; // инициализация этой переменной - это путь к файлу

        private final Gson gson; // переменная, которая преобразует файл в json и из jsona


        public DumpManager(File dataFile) {
            this.dataFile = dataFile; // этот файл будет использоваться для чтения или записи данных

            GsonBuilder gsonBuilder = new GsonBuilder(); // настраиват экземпляры Json
            gsonBuilder.registerTypeAdapter(Number.class, new NumberDeserializer());
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()); // сереализирует и десереализирует LocalDateTime
            // это пользовательский сериализатор и десиализатор
            this.gson = gsonBuilder.serializeNulls().setPrettyPrinting().create();
        }

        // метод для записи файла в jSON
        public void writeToJSON(PriorityQueue<Vehicle> collection) throws IOException {
            // FileOutputStream запись байтов в файл
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(dataFile));
            // OutputStreamWriter используется для записи символов в байты
            Type collectionType = new TypeToken<PriorityQueue<Vehicle>>() {
            }.getType();
            // TypeToken сохраняет Vehicle в типе коллекции
            String output = this.gson.toJson(collection, collectionType);
            // указывает в каком типе обработать колекцию
            outputStreamWriter.write(output); // запись в файл
            outputStreamWriter.flush(); // увернность что все передалось
            outputStreamWriter.close(); // закрытие записи
        }

        // метод для чтения файла jSON
        public PriorityQueue<Vehicle> readJSON() throws IOException, JsonParseException {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(dataFile));
            Type dataType = new TypeToken<PriorityQueue<Vehicle>>() {
            }.getType();
            return this.gson.fromJson(new JsonReader(inputStreamReader), dataType);
        }
    }