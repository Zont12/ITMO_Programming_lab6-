package server.commands;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.ResponseOutputer;

public class Exit extends AbstractCommand {

    public Exit() {
        super("exit", "", "Отключение клиента");
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        ResponseOutputer.appendln("Клиент успешно отключился от сервера!");
        return true;
    }
}
