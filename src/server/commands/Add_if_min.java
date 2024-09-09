package server.commands;
import coomon.Models.Vehicle;
import coomon.exceptions.CollectionisEmptyEX;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Add_if_min extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Add_if_min(CollectionManager collectionManager) {
        super("add_if_min", "{element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            Vehicle vehicle = (Vehicle) objectArgument;
            if (this.collectionManager.getCollection().isEmpty()) throw new CollectionisEmptyEX("Коллекция пуста!");

            Vehicle minVehicle = this.collectionManager.getCollection().stream()
                    .min(Comparator.comparing(Vehicle::getEnginePower))
                    .orElseThrow(NoSuchElementException::new);

            if (vehicle.getEnginePower() < minVehicle.getEnginePower()) {
                this.collectionManager.addElementToCollection(vehicle);
                ResponseOutputer.appendln("Транспорт успешно добавлен!");
                return true;
            } else {
                ResponseOutputer.appenderror("Новый транспорт имеет слишком мощный двигатель!");
            }
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Неправильный тип аргумента!");
        } catch (CollectionisEmptyEX exception) {
            ResponseOutputer.appenderror(exception.getMessage());
        } catch (NoSuchElementException exception) {
            ResponseOutputer.appenderror("Не удалось найти элемент в коллекции для сравнения");
        }
        return false;
    }
}