package server;

import server.Managers.*;
import server.commands.*;

public class App {
    public static final int PORT = 1346;
    public static final int CONNECTION_TIMEOUT = 80 * 1000;
    public static final String ENV_VARIABLE = System.getenv("LAB6_FILE"); // получаем переменную окружения

    public static void main(String[] args) {
        if (ENV_VARIABLE == null || ENV_VARIABLE.isEmpty()) {
            System.err.println("Переменная окружения не определена!");
            System.exit(0);
        }

        DumpManager dumpManager = new DumpManager(new java.io.File(ENV_VARIABLE));

        CollectionManager collectionManager = new CollectionManager(dumpManager);

        CommandManager commandManager = new CommandManager(
                new Show(collectionManager),
                new Add(collectionManager),
                new Clear(collectionManager),
                new Print_unique_engine_power(collectionManager),
                new Add_if_min(collectionManager),
                new Count_by_fuel_type(collectionManager),
                new RemoveById(collectionManager),
                new RemoveFirst(collectionManager),
                new Execute_add_if_max(collectionManager),
                new Min_By_Number_Of_Wheels(collectionManager),
                new UpdateID(collectionManager),
                new Info(collectionManager),
                new Help(),
                new ExecuteScript(),
                new Exit(),
                new ServerExit()
        );

        ServerManager requestHandler = new ServerManager(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}