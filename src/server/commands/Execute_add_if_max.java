package server.commands;
import coomon.Models.Vehicle;
import coomon.exceptions.CollectionisEmptyEX;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class Execute_add_if_max extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Execute_add_if_max(CollectionManager collectionManager) {
        super("execute_add_if_max", "{element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            Vehicle vehicle = (Vehicle) objectArgument;

            if (this.collectionManager.getCollection().isEmpty()) throw new CollectionisEmptyEX("Коллекция пуста!");

            Vehicle maxVehicle = this.collectionManager.getCollection().stream()
                    .max(Comparator.comparing(Vehicle::getEnginePower))
                    .orElseThrow(NoSuchElementException::new);

            if (vehicle.getEnginePower() > maxVehicle.getEnginePower()) {
                this.collectionManager.addElementToCollection(vehicle);
                ResponseOutputer.appendln("Транспорт успешно добавлен!");
                return true;
            } else {
                ResponseOutputer.appenderror("Новый транспорт имеет недостаточно мощный двигатель!");
            }
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror(exception.getMessage());
        } catch (CollectionisEmptyEX exception) {
            ResponseOutputer.appenderror(exception.getMessage());
        } catch (NoSuchElementException exception) {
            ResponseOutputer.appenderror("Не удалось найти элемент в коллекции для сравнения");
        }
        return false;
    }
}