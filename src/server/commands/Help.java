package server.commands;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.ResponseOutputer;

public class Help extends AbstractCommand {

    public Help() {
        super("help", "", "вывести справку по доступным командам");
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        ResponseOutputer.appendln("{help - вывести справку по доступным командам}\n");
        ResponseOutputer.appendln("{info - вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)}\n");
        ResponseOutputer.appendln("{show - Вывести в стандартный поток вывода все элементы коллекции в строковом представлении}\n");
        ResponseOutputer.appendln("{add - Добавить элемент в коллекцию}\n");
        ResponseOutputer.appendln("{execute_add_if_max - добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции)}\n");
        ResponseOutputer.appendln("{update_id - обновить значение элемента коллекции, id которого равен заданному}\n");
        ResponseOutputer.appendln("{clear - Очистить коллекцию}\n");
        ResponseOutputer.appendln("{execute_script file_name - Считать скрипт и исполнить его}\n");
        ResponseOutputer.appendln("{exit - Отключение клиента и сервера}\n");
        ResponseOutputer.appendln("{add_if_min - добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции}\n");
        ResponseOutputer.appendln("{count_by_fuel_type 'fueltype' - вывести количество элементов, значение поля fuelType которых равно заданному}\n");
        ResponseOutputer.appendln("{Min_By_Number_Of_Wheels - вывести любой объект из коллекции, значение поля numberOfWheels которого является минимальным}\n");
        ResponseOutputer.appendln("{print_unique_engine_power - Вывести уникальные значения поля enginePower всех элементов в коллекции}\n");
        ResponseOutputer.appendln("{remove_by_id id - Удалить элемент коллекции по id}\n");
        ResponseOutputer.appendln("{remove_first - удалить первый элемент из коллекции}");
        return true;
    }
}