package server.commands;
import coomon.Models.Vehicle;
import coomon.exceptions.CollectionisEmptyEX;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;

public class Min_By_Number_Of_Wheels extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Min_By_Number_Of_Wheels(CollectionManager collectionManager) {
        super("min_by_number_of_wheels", "", "вывести любой объект из коллекции, значение поля numberOfWheels которого является минимальным");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (this.collectionManager.getCollection().isEmpty()) throw new CollectionisEmptyEX("Коллекция пуста!");

            Vehicle minVehicle = this.collectionManager.getMinByNumberOfWheels();

            ResponseOutputer.appendln(minVehicle.toString());
            return true;
        } catch (CollectionisEmptyEX exception) {
            ResponseOutputer.appenderror(exception.getMessage());
        }
        return false;
    }
}