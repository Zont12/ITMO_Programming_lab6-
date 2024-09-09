package server.commands;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;


public class Clear extends AbstractCommand {
    private CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "", "Очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
            this.collectionManager.clearCollection();
            ResponseOutputer.appendln("Коллекция успешно очищена");
            return true;
    }
}