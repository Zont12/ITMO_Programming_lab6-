package client.ClientThings;
import client.App;
import client.builders.VehicleBuilder;
import coomon.Interaction.Request;
import coomon.Interaction.ResponseCode;
import coomon.Models.Vehicle;
import coomon.exceptions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

// ClientManager - класс, который занимается обработкой запросов клиента
public class ClientManager {
    private InputManager inputManager;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<InputManager> scannerStack = new Stack<>();
    private final OutputManager outputManager;
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    boolean filemode = InputManager.isFileMode();

    public ClientManager(InputManager inputManager, OutputManager outputManager) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    public Request handle(ResponseCode serverResponseCode) {
        String userInput;
        String[] userCommand;
        ProcessingCode processingCode;
        do {
            if (!FileMode()) {
                outputManager.print(App.SYMBOL1);
                userInput = inputManager.readLine();
            } else {
                userInput = inputManager.readLine();
                if (!userInput.isEmpty()) {
                    outputManager.print(App.SYMBOL2);
                    outputManager.println(userInput);
                }
            }
            userCommand = (userInput.trim() + " ").split(" ", 2); // разделяет ввод на два элемента
            userCommand[1] = userCommand[1].trim(); //удаляет пробелы у второго элемента
            processingCode = processCommand(userCommand[0], userCommand[1]); // обрабатывает команду и аргумент
        } while (processingCode == ProcessingCode.ERROR && !FileMode() || userCommand[0].isEmpty());
        try {
            if (FileMode() && (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR)) {
                throw new IncorrectInputInScriptEX("Во время исполнения скрипта возникла ошибка!");
            }
            switch (processingCode) {
                case OBJECT:
                    try {
                        // Запросите ID у сервера
                        Integer id = requestId();
                        VehicleBuilder vehicleBuilder = new VehicleBuilder(id, outputManager, inputManager);
                        Vehicle vehiclebuild = vehicleBuilder.build();
                        return new Request(userCommand[0], userCommand[1], vehiclebuild);
                    } catch (IncorrectInputEX e) {
                        outputManager.printError(e.getMessage());
                        return new Request();
                    }
                case SCRIPT:
                    File scriptFile = new File(userCommand[1]); // Используем введенный пользователем путь к файлу
                    if (!scriptFile.exists()) throw new FileNotFoundException(); // проверка на наличие файла
                    if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1) // проверка рекурсии
                        throw new ScriptRecursionEX("Скрипт рекурсивен!");
                    scannerStack.push(inputManager); // ввод сохраняется в стек
                    scriptStack.push(scriptFile); // файл добавляется в стек скриптов
                    inputManager = new InputManager(new Scanner(scriptFile));
                    outputManager.print("Скрипт исполняется '" + scriptFile.getName() + "'...");
                    break;
            }
        } catch (IncorrectInputInScriptEX exception) {
            outputManager.print( RED + "Во время исполнения скрипта возникла ошибка!" + RESET);
            while (!scannerStack.isEmpty()) {
                inputManager.close();
                inputManager = scannerStack.pop(); // возвращаем inputManager
                outputManager.println("");
            }
            scriptStack.clear();
            return new Request();
        } catch (FileNotFoundException e) {
            outputManager.printError("Файл скрипта не был найден!");
        } catch (ScriptRecursionEX e) {
            outputManager.print( RED + e.getMessage() + RESET);
            while (!scannerStack.isEmpty()) {
                inputManager.close();
                inputManager = scannerStack.pop(); // возвращаем inputManager
                outputManager.println("");
            }
            scriptStack.clear();
            return new Request();
        }

        if (FileMode() && !inputManager.HasNextLine()) {
            inputManager.close();
            inputManager = scannerStack.pop();
            scriptStack.pop();
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    public boolean FileMode() { // проверка на наличие скриптов, если скрипты есть то включен режим скрипта
        if (!scriptStack.isEmpty()) {
            InputManager.fileMode = true;
            filemode = true;
        } else {
            InputManager.fileMode = false;
            filemode = false;
        }
        return filemode;
    }

    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return ProcessingCode.ERROR;
                case "help":
                case "remove_first":
                case "server_exit":
                case "info":
                case "show":
                case "min_by_number_of_wheels":
                case "clear":
                case "exit":
                case "print_unique_engine_power":
                    if (!commandArgument.isEmpty()) throw new CommandUsageEX();
                    break;
                case "add":
                case "add_if_min":
                case "execute_add_if_max":
                    if (!commandArgument.isEmpty()) throw new CommandUsageEX("{element}");
                    return ProcessingCode.OBJECT;
                case "update_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageEX("<ID> {element}");
                    return ProcessingCode.OBJECT;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageEX("<ID>");
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageEX("<file_name>");
                    return ProcessingCode.SCRIPT;
                case "count_by_fuel_type":
                    if (commandArgument.isEmpty()) throw new CommandUsageEX("<fuelType>");
                    break;

                default:
                    outputManager.println( RED + "Команда '" + command + "' не найдена. Напишите 'help' для вывода справки команд." + RESET);
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageEX exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            outputManager.println( RED + "Некорректный формат ввода команды: '" + command + "'" + RESET);
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }

    private Integer requestId() {
        return 1;
    }
}