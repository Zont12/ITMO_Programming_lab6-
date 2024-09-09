package client.UI;

// Console - это интерфейс для моего будущего OutputManager
public interface Console {

    void print(Object object); // вывод строки без \n

    void println(Object object); // вывод строки с \n


    void printlnError(Object obj); // метод для возвращения ошибок

    void printError(Object object);

    void Flush();
}
