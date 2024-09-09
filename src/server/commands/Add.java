package server.commands;
import coomon.Models.Vehicle;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;

public class Add extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add", "{element}", "Добавить элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            Vehicle vehicle = (Vehicle) objectArgument;
            collectionManager.addElementToCollection(vehicle);
            ResponseOutputer.appendln("Vehicle успешно добавлен!");
            return true;
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Неправильный тип аргумента!");
        }
        return false;
    }
}