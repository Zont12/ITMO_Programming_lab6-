package client.builders;
import client.App;
import client.ClientThings.InputManager;
import client.ClientThings.OutputManager;
import coomon.Models.Coordinates;
import coomon.exceptions.IncorrectInputEX;
import coomon.exceptions.MustBeNotEmptyEX;
import java.io.Serializable;
import java.util.NoSuchElementException;

// CoordinatesBuilder - генератор координат с учетом валидации
public class CoordinatesBuilder extends CollectionBuilder<Coordinates> implements Serializable {
    private final OutputManager outputManager;
    private final InputManager inputManager;

    public CoordinatesBuilder(OutputManager outputManager, InputManager inputManager) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
    }

    @Override
    public Coordinates build() throws IncorrectInputEX {
        Coordinates coordinates = new Coordinates(requestX(), requestY());
        if (!coordinates.validate()) {
            throw new IncorrectInputEX("Число должно быть больше -296");
        }
        return coordinates;
    }

    public int requestX() {
        String userInput;
        int x;
        while (true) {
            try {
                outputManager.println("Введите координату X: ");
                outputManager.print(App.SYMBOL1);
                userInput = inputManager.readLine().trim();
                if (userInput.isEmpty()) throw new MustBeNotEmptyEX("Поле не может быть пустым");
                x = Integer.parseInt(userInput);
                if (x <= -296) throw new IncorrectInputEX("Число должно быть больше -296");
                return x;
            } catch (IncorrectInputEX e) {
                outputManager.printlnError(e.getMessage());
            } catch (MustBeNotEmptyEX e) {
                outputManager.printlnError(e.getMessage());
            } catch (NumberFormatException e) {
                outputManager.printlnError("Некорректный формат координаты. Введите число: ");
            } catch (NoSuchElementException e) {
                outputManager.printlnError("Координата не распознана");
            } catch (IllegalStateException e) {
                outputManager.printlnError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
    }

    public float requestY() {
        String userInput;
        while (true) {
            try {
                outputManager.println("Введите координату Y: ");
                outputManager.print(App.SYMBOL1);
                userInput = inputManager.readLine().trim();
                if (userInput.isEmpty()) throw new MustBeNotEmptyEX("Поле не может быть пустым");
                return Float.parseFloat(userInput);
            } catch (MustBeNotEmptyEX e) {
                outputManager.printlnError(e.getMessage());
            } catch (NumberFormatException e) {
                outputManager.printlnError("Некорректный формат координаты. Введите число: ");
            } catch (NoSuchElementException e) {
                outputManager.printlnError("Координата не распознана");
            } catch (IllegalStateException e) {
                outputManager.printlnError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
    }
}