package client.ClientThings;
import client.UI.Console;

// OutputManager - класс, для вывода объектов
public class OutputManager implements Console {

    @Override
    public void println(Object object) {
        System.out.println(object);
    }

    @Override
    public void print(Object object) {
        System.out.print(object);
    }

    @Override
    public void printlnError(Object object) {
        System.err.println(object);
    }

    @Override
    public void printError(Object object) {
        System.err.print(object);
    }

    @Override
    public void Flush() {
        System.out.flush();
    }
}
