package client;
import client.ClientThings.ClientManager;
import client.ClientThings.InputManager;
import client.ClientThings.OutputManager;
import coomon.exceptions.NotInDeclaredLimitsEX;
import coomon.exceptions.WrongAmountOfElementsEx;

import java.util.Scanner;

public class App {
    public static final String SYMBOL1 = "$ "; // не скрипт
    public static final String SYMBOL2 = "> "; // для скрипта
    //----------------------------------------------------------//
    private static final int RECONNECTION_TIMEOUT = 5 * 1000; // тайм-аут между соединениями 5 секунд
    private static final int MAX_RECONNECTION_ATTEMPTS = 5; // максимально кол-во попыток повторного соединения
    private static String host; // хост - это что-то на подобии компа на котором будет происходить работа клиента и сервера
    private static int port; // порт - это шлюз, благодаря которому клиенту и серверу можно будет обмениваться данными


    private static boolean initializeConnectionAddress(String[] hostAndPortArgs) {
        OutputManager outputManager1 = new OutputManager();
        try {
            if (hostAndPortArgs.length != 2) throw new WrongAmountOfElementsEx();
            host = hostAndPortArgs[0];
            port = Integer.parseInt(hostAndPortArgs[1]);
            if (port < 0) throw new NotInDeclaredLimitsEX();
            return true;
        } catch (WrongAmountOfElementsEx exception) {
            String jarName = new java.io.File(App.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            outputManager1.println("Введите команду: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            outputManager1.printlnError("Порт должен быть числом!");
        } catch (NotInDeclaredLimitsEX exception) {
            outputManager1.printlnError("Порт не может быть негативным!");
        }
        return false;
    }

    public static void main(String[] args) {
        if (!initializeConnectionAddress(args)) return;
        InputManager inputManager = new InputManager(new Scanner(System.in));
        OutputManager outputManager = new OutputManager();
        ClientManager userHandler = new ClientManager(inputManager, outputManager );
        Client client = new Client(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, userHandler, outputManager);
        client.run();
        inputManager.close();
    }
}