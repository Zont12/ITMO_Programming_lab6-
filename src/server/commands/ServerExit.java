package server.commands;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.ResponseOutputer;

public class ServerExit extends AbstractCommand {

    public ServerExit() {
        super("server_exit", "", "Отключение сервера");
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        return true;

    }
}
