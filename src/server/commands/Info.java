package server.commands;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;

public class Info extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        String info = collectionManager.getInfo();
        System.out.println("Получена информация о коллекции: " + info); // Отладочное сообщение
        ResponseOutputer.appendln(info);
        return true;
    }
}