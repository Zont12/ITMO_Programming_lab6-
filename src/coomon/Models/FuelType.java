package coomon.Models;
import java.io.Serializable;

public enum FuelType implements Serializable {
    GASOLINE, // бензин
    ELECTRICITY,
    PLASMA; // ядерное вещество


    public static FuelType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        switch (value.toUpperCase()) {
            case "GASOLINE":
                return GASOLINE;
            case "ELECTRICITY":
                return ELECTRICITY;
            case "PLASMA":
                return PLASMA;
            default:
                throw new IllegalArgumentException("Типа топлива" + " " + value + " не существует!");
        }
    }

    public static String names() {
        StringBuilder FuelTypeList = new StringBuilder();
        for (var FuelType : values()) {
            FuelTypeList.append(FuelType.name()).append(", ");
        }
        return FuelTypeList.substring(0, FuelTypeList.length() - 2); // для вывода типов топлива
    }


}
