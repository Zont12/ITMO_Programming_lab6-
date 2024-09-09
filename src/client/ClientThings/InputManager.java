package client.ClientThings;
import java.util.Scanner;

// InputManger - класс благодаря которому будет обрабатываться любой ввод
public class InputManager {
    private final Scanner scanner;
    public InputManager(Scanner scanner){
        this.scanner = scanner;
    }
    public static boolean fileMode = false;
    public static boolean isFileMode() {
        return fileMode;
    } // проверка на скрипт

    public String readLine(){
        if (scanner.hasNextLine()){ // проверка на ввод пользователя
            return scanner.nextLine(); // если пользователь что-то ввел, считывает ввод пользователя
        } else { // если пользователь ничего не ввел возвращаем пустую строку
            return "";
        }
    }

    public boolean HasNextLine(){
        if (scanner.hasNextLine()){
            return true;
        } else {
            return false;
        }
    }

    public void close(){
        scanner.close();
    } // закрытие пользовательского ввода, например при возникновении ошибки
}