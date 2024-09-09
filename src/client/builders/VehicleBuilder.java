package client.builders;
import client.App;
import client.ClientThings.InputManager;
import client.ClientThings.OutputManager;
import coomon.Models.Coordinates;
import coomon.Models.FuelType;
import coomon.Models.Vehicle;
import coomon.exceptions.GreaterThanZeroEX;
import coomon.exceptions.IncorrectInputEX;
import coomon.exceptions.MustBeNotEmptyEX;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

// VehicleBuilder - окончательная генерация объектов - тип Vehicle
public class VehicleBuilder extends CollectionBuilder<Vehicle> implements Serializable {
    private final OutputManager outputManager;
    private final InputManager inputManager;
    private static Integer id;

    public VehicleBuilder(Integer id, OutputManager outputManager, InputManager inputManager) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.id = id;
    }

    @Override
    public Vehicle build() throws IncorrectInputEX {
        Vehicle vehicle = new Vehicle(
                id,
                requestName(),
                requestCoordinates(),
                LocalDateTime.now(),
                requestEnginePower(),
                requestNumberOfWheels(),
                requestCapacity(),
                requestFuelType());
        if (!vehicle.validate()) {
            throw new IllegalAccessError("Ошибка валидации транспорта. Транспорт: " + vehicle);
        }
        return vehicle;
    }

    private String requestName() {
        String name;
        while (true) {
            try {
                outputManager.println("Введите имя транспорта: ");
                outputManager.print(App.SYMBOL1);
                name = inputManager.readLine().trim();
                if (name.isEmpty()) throw new MustBeNotEmptyEX("Поле не может быть пустым");
                break;
            } catch (MustBeNotEmptyEX e) {
                outputManager.printlnError(e.getMessage());
            } catch (NoSuchElementException e) {
                outputManager.printlnError("Имя не распознано! Введите имя транспорта снова:");
            } catch (IllegalStateException e) {
                outputManager.printlnError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return name;
    }

    private Coordinates requestCoordinates() throws IncorrectInputEX {
        return new CoordinatesBuilder(outputManager, inputManager).build();
    }

    private Integer requestEnginePower() {
        Integer enginePower;
        while (true) {
            try {
                outputManager.println("Введите мощность двигателя: ");
                outputManager.print(App.SYMBOL1);
                String userInput = inputManager.readLine().trim();
                if (userInput.isEmpty()) throw new MustBeNotEmptyEX("Поле не может быть пустым");
                enginePower = Integer.parseInt(userInput);
                if (enginePower <= 0)
                    throw new GreaterThanZeroEX("Мощность двигателя должна быть больше 0. Введите мощность двигателя заново: ");
                break;
            } catch (MustBeNotEmptyEX e) {
                outputManager.printlnError(e.getMessage());
            } catch (NumberFormatException e) {
                outputManager.printlnError("Некорректный формат мощности. Введите мощность двигателя снова:");
            } catch (NoSuchElementException e) {
                outputManager.printlnError("Мощность двигателя не распознана! Введите мощность двигателя снова:");
            } catch (GreaterThanZeroEX e) {
                outputManager.printlnError(e.getMessage());
            } catch (IllegalStateException e) {
                outputManager.printlnError("Произошла непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return enginePower;
    }

    private Integer requestNumberOfWheels() {
        Integer numberOfWheels;
        while (true) {
            try {
                outputManager.println("Введите количество колес транспорта: ");
                outputManager.print(App.SYMBOL1);
                String userInput = inputManager.readLine().trim();
                if (userInput.isEmpty()) throw new MustBeNotEmptyEX("Поле не может быть пустым");
                numberOfWheels = Integer.parseInt(userInput);
                if (numberOfWheels <= 0)
                    throw new GreaterThanZeroEX("Количество колес транспорта должно быть больше 0. Введите количество колес транспорта заново: ");
                break;
            } catch (MustBeNotEmptyEX e) {
                outputManager.printlnError(e.getMessage());
            } catch (NumberFormatException e) {
                outputManager.printlnError("Некорректный формат количества колес транспорта. Введите количество колес транспорта заново:");
            } catch (NoSuchElementException e) {
                outputManager.printlnError("Количество колес транспорта не распознано! Введите количество колес транспорта снова:");
            } catch (GreaterThanZeroEX e) {
                outputManager.printlnError(e.getMessage());
            } catch (IllegalStateException e) {
                outputManager.printlnError("Произошла непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return numberOfWheels;
    }

    private long requestCapacity() {
        long capacity;
        while (true) {
            try {
                outputManager.println("Введите количество мест транспорта: ");
                outputManager.print(App.SYMBOL1);
                String userInput = inputManager.readLine().trim();
                capacity = Long.parseLong(userInput);
                if (capacity <= 0)
                    throw new GreaterThanZeroEX("Вместимость транспорта должна быть больше 0. Введите вместимость транспорта заново: ");
                break;
            } catch (NumberFormatException e) {
                outputManager.printlnError("Некорректный формат вместимости транспорта. Введите вместимость транспорта заново:");
            } catch (NoSuchElementException e) {
                outputManager.printlnError("Вместимость транспорта не распознана! Введите вместимость транспорта заново:");
            } catch (GreaterThanZeroEX e) {
                outputManager.printlnError(e.getMessage());
            } catch (IllegalStateException e) {
                outputManager.printlnError("Произошла непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return capacity;
    }

    private FuelType requestFuelType() {
        return new FuelTypebuilder(outputManager, inputManager).build();
    }
}