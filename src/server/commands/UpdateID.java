package server.commands;
import coomon.Models.Vehicle;
import coomon.exceptions.CollectionisEmptyEX;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;

import java.util.NoSuchElementException;

public class UpdateID extends AbstractCommand {
    private final CollectionManager collectionManager;

    public UpdateID(CollectionManager collectionManager) {
        super("update_id", "{element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            int id;
            try {
                id = Integer.parseInt(stringArgument);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Неправильный id. Постарайтесь ввести id снова");
            }

            if (this.collectionManager.getCollection().isEmpty()) throw new CollectionisEmptyEX("Коллекция пуста!");
            if (!this.collectionManager.containsId(id)) {
                throw new NoSuchElementException("Транспорта с таким id не существует!");
            }

            this.collectionManager.removeById(id);
            Vehicle vehicle = (Vehicle) objectArgument;
            vehicle.setId(id);
            this.collectionManager.addElementToCollection(vehicle);

            ResponseOutputer.appendln("Данные транспорта успешно обновлены!");
            return true;
        } catch (NumberFormatException | CollectionisEmptyEX | NoSuchElementException exception) {
            ResponseOutputer.appenderror(exception.getMessage());
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Неправильный тип аргумента!");
        }
        return false;
    }
}