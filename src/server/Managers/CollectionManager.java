package server.Managers;
import coomon.Models.Vehicle;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    private PriorityQueue<Vehicle> collection;
    private final DumpManager dumpManager;
    private final LocalDateTime creationDate;

    public CollectionManager(DumpManager dumpManager) {
        this.creationDate = LocalDateTime.now();
        this.dumpManager = dumpManager;
        this.collection = new PriorityQueue<>(); // Инициализация коллекции по умолчанию

        // Попытка загрузить коллекцию из файла
        try {
            loadCollection();
        } catch (IOException e) {
            ResponseOutputer.appenderror("Ошибка при загрузке коллекции: " + e.getMessage());
            System.err.println("Ошибка при загрузки коллекции: " + e.getMessage());
        }
    }

    public PriorityQueue<Vehicle> getCollection() {
        return collection;
    }

    public void addElementToCollection(Vehicle vehicle) {
        vehicle.setId(generateUniqueId(vehicle.getId()));
        collection.add(vehicle);
        saveCollection(); // Сохранение коллекции после добавления нового элемента
    }

    private Integer generateUniqueId(Integer id) {
        Set<Integer> existingIds = collection.stream()
                .map(Vehicle::getId)
                .collect(Collectors.toSet());

        if (existingIds.contains(id)) {
            int maxId = existingIds.stream().max(Integer::compare).orElse(0);
            return maxId + 1;
        }
        return id;
    }



    public void clearCollection() {
        collection.clear();
        saveCollection(); // Сохранение коллекции после очистки
    }

    public Vehicle getMinByNumberOfWheels() {
        return this.collection.stream()
                .min(Comparator.comparing(Vehicle::getNumberOfWheels))
                .orElseThrow(NoSuchElementException::new);
    }

    public Set<Integer> getUniqueEnginePower() {
        return this.collection.stream()
                .map(Vehicle::getEnginePower)
                .collect(Collectors.toSet());
    }

    public LocalDateTime getInitializationDate() {
        return creationDate;
    }

    public String getElementsType() {
        if (!collection.isEmpty()) {
            String elementsType = collection.iterator().next().getClass().getName();
            System.out.println("Тип элементов коллекции: " + elementsType); // Отладочное сообщение
            return elementsType;
        }
        System.out.println("Коллекция пуста, тип элементов не определен."); // Отладочное сообщение
        return null;
    }

    public String showCollection() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        return collection.stream()
                .map(Vehicle::toString)
                .collect(Collectors.joining("\n"));
    }

    public String getCollectionType() {
        String collectionType = collection.getClass().getName();
        System.out.println("Тип коллекции: " + collectionType); // Отладочное сообщение
        return collectionType;
    }

    public int getCollectionSize() {
        int size = collection.size();
        System.out.println("Размер коллекции: " + size); // Отладочное сообщение
        return size;
    }

    public boolean containsId(Integer id) {
        if (this.collection.isEmpty()) return false;
        return this.collection.stream().anyMatch(vehicle -> vehicle.getId().equals(id));
    }

    public void removeById(Integer id) {
        this.collection.removeIf(vehicle -> vehicle.getId().equals(id));
        saveCollection(); // Сохранение коллекции после удаления элемента
    }

    public void removeFirstElemFromCollection() {
        collection = collection.stream()
                .skip(1)
                .collect(Collectors.toCollection(PriorityQueue::new));
        saveCollection(); // Сохранение коллекции после удаления первого элемента
    }

    private void loadCollection() throws IOException {
        try {
            PriorityQueue<Vehicle> loadedCollection = dumpManager.readJSON();
            if (loadedCollection != null) {
                collection = loadedCollection;
                System.out.println("Коллекция успешно загружена с файла JSON. Размер коллекции: " + collection.size()); // Отладочное сообщение
            } else {
                System.err.println("Загруженная коллекция пуста. Используется пустая коллекция.");
            }
        } catch (IOException e) {
            ResponseOutputer.appendln("Ошибка при чтении файла");
            throw e;
        }
    }

    private void saveCollection() {
        try {
            dumpManager.writeToJSON(collection);
            System.out.println("Коллекция успешно сохранена в файл JSON.");
        } catch (IOException e) {
            System.err.println("Ошибка в процессе сохранения файла: " + e.getMessage());
        }
    }

    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Тип коллекции: ").append(getCollectionType()).append("\n");
        info.append("Тип элементов коллекции: ").append(getElementsType()).append("\n");
        info.append("Дата инициализации: ").append(getInitializationDate()).append("\n");
        info.append("Количество элементов: ").append(getCollectionSize()).append("\n");
        return info.toString();
    }
}