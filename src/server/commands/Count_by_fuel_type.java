package server.commands;
import coomon.Models.FuelType;
import coomon.exceptions.CollectionisEmptyEX;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;

public class Count_by_fuel_type extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Count_by_fuel_type(CollectionManager collectionManager) {
        super("count_by_fuel_type", "fuelType", "вывести количество элементов, значение поля fuelType которых равно заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (this.collectionManager.getCollection().isEmpty()) {
                throw new CollectionisEmptyEX("Коллекция пуста!");
            }

            FuelType fuelType = FuelType.fromString(stringArgument);
            long count = this.collectionManager.getCollection().stream()
                    .filter(vehicle -> vehicle.getFuelType() == fuelType)
                    .count();

            ResponseOutputer.appendln("Количество транспортов с типом топлива " + fuelType + " = " + count);
            return true;
        } catch (CollectionisEmptyEX exception) {
            ResponseOutputer.appenderror(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            ResponseOutputer.appenderror("Некорректный тип топлива: " + stringArgument);
            ResponseOutputer.appenderror("Доступные типы топлива: " + FuelType.names());
        }
        return false;
    }
}