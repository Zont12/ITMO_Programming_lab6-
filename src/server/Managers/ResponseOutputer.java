package server.Managers;

public class ResponseOutputer { // класс для генерации ответов клиенту
    private static StringBuilder stringBuilder = new StringBuilder(); // используется для накопления строк

    public static void appendln(Object toOut) { // строка + следующая строка
        stringBuilder.append(toOut);
    }



    public static void appenderror(Object toOut) { // обозночение ошибки
        stringBuilder.append("\u001B[31m"+ toOut + "\u001B[0m");
    }


    public static String getString() { // вывести буфер
        return stringBuilder.toString();
    }


    public static String getAndClear() {
        String toReturn = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length()); // вывести и очистить буфер
        return toReturn;
    }
}