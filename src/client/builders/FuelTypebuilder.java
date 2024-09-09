package client.builders;
import client.App;
import client.ClientThings.InputManager;
import client.ClientThings.OutputManager;
import coomon.Models.FuelType;
import java.io.Serializable;
import java.util.NoSuchElementException;

// FuelTypeBuilder - генератор типа топлива с учетом валидации
public class FuelTypebuilder extends CollectionBuilder<FuelType> implements Serializable {
    private final OutputManager outputManager;
    private final InputManager inputManager;

    public FuelTypebuilder(OutputManager outputManager, InputManager inputManager) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
    }

    @Override
    public FuelType build() {
        String userInput;
        FuelType fuelType;
        while (true) {
            try {
                outputManager.println("У вашего транспорта есть топливо? (yes/no): ");
                outputManager.print(App.SYMBOL1);
                String userAnswer = inputManager.readLine().trim().toLowerCase();
                if (userAnswer.equals("yes")) {
                    outputManager.println("Выберите тип топлива транспорта: " + FuelType.names());
                    outputManager.print(App.SYMBOL1);
                    userInput = inputManager.readLine().trim();
                    fuelType = FuelType.valueOf(userInput.toUpperCase());
                    break;
                } else if (userAnswer.equals("no")) {
                    fuelType = null;
                    break;
                } else {
                    outputManager.printlnError("Пожалуйста, введите 'yes' или 'no'");
                }
            } catch (IllegalArgumentException exception) {
                outputManager.printlnError("Такого типа топлива не существует!");
            } catch (NoSuchElementException exception) {
                outputManager.printlnError("У каждого транспорта должно быть топливо!");
            } catch (IllegalStateException exception) {
                outputManager.printlnError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return fuelType;
    }
}