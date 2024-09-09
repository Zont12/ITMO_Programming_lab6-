package coomon.Models;
import client.UI.Validatable;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Vehicle implements Comparable<Vehicle>, Validatable, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer enginePower; //Поле не может быть null, Значение поля должно быть больше 0 - мощность двигателя
    private Integer numberOfWheels; //Поле не может быть null, Значение поля должно быть больше 0 - количества колес
    private long capacity; //Значение поля должно быть больше 0 -вместимость
    private FuelType fuelType; //Поле может быть null - тип топлива


    public Vehicle(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, Integer enginePower, Integer numberOfWheels, long capacity, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.capacity = capacity;
        this.fuelType = fuelType;
    }

    public Vehicle(Integer id, String name, Coordinates coordinates, Integer enginePower, Integer numberOfWheels, long capacity, FuelType fuelType) {
        this(id, name, coordinates, LocalDateTime.now(), enginePower, numberOfWheels, capacity, fuelType);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public Integer getNumberOfWheels() {
        return numberOfWheels;
    }

    public long getCapacity() {
        return capacity;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void update(Vehicle vehicle) {
        this.name = vehicle.getName();
        this.coordinates = vehicle.getCoordinates();
        this.creationDate = vehicle.getCreationDate();
        this.enginePower = vehicle.getEnginePower();
        this.numberOfWheels = vehicle.getNumberOfWheels();
        this.capacity = vehicle.getCapacity();
        this.fuelType = vehicle.getFuelType();
    }

    public boolean validate() {
        if (id == null || id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (enginePower == null || enginePower <= 0) return false;
        if (numberOfWheels == null || numberOfWheels <= 0) return false;
        if (capacity <= 0) return false;
        return true;
    }

    @Override
    public int compareTo(Vehicle o) {
        return (int) (this.capacity - o.getCapacity());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", coordinates=" + getCoordinates() +
                ", creationDate=" + getCreationDate() +
                ",enginePower=" + getEnginePower() +
                ", numberOfWheels=" + getNumberOfWheels() +
                ", capacity=" + getCapacity() +
                ", fuelType=" + getFuelType() +
                '}';
    }

}


