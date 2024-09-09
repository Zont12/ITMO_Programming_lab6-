package server.commands;
import coomon.exceptions.CollectionisEmptyEX;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;

public class Print_unique_engine_power extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Print_unique_engine_power(CollectionManager collectionManager) {
        super("print_unique_engine_power", "", "Вывести уникальные значения поля enginePower всех элементов в коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (this.collectionManager.getCollection().isEmpty()) throw new CollectionisEmptyEX("Коллекция пуста!");
            String view = this.collectionManager.getUniqueEnginePower().toString();
            view = view.replace("[", "{").replace("]", "}");
            ResponseOutputer.appendln(view);
            ResponseOutputer.appendln("\nКоманда успешно выполнена!");
            return true;
        } catch (CollectionisEmptyEX exception) {
            ResponseOutputer.appenderror(exception.getMessage());
        }
        return false;
    }
}