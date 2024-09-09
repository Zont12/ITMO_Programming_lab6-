package server.commands;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;


public class Show extends AbstractCommand {
    private CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "", "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        ResponseOutputer.appendln(collectionManager.showCollection());
        return true;
    }
}