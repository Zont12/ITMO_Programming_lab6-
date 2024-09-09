package server.Managers;
import server.UI.Command;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final List<Command> commands = new ArrayList<>();
    private final Command Show;
    private final Command Add;
    private final Command Clear;
    private final Command Print_unique_engine_power;
    private final Command Add_if_min;
    private final Command Count_by_fuel_type;
    private final Command RemoveById;
    private final Command RemoveFirst;
    private final Command Execute_add_if_max;
    private final Command Min_By_Number_Of_Wheels;
    private final Command UpdateID;
    private final Command Info;
    private final Command Help;
    private final Command ExecuteScript;
    private final Command Exit;
    private final Command ServerExit;


    public CommandManager(Command show, Command add,Command clear, Command print_unique_engine_power,Command add_if_min,Command count_by_fuel_type, Command removeById, Command removeFirst,Command execute_add_if_max,Command min_by_number_of_wheels, Command update_id, Command info, Command help,Command execute_script, Command exit, Command serverExit) {
        this.Show = show;
        this.Add = add;
        this.Clear = clear;
        this.Print_unique_engine_power = print_unique_engine_power;
        this.Add_if_min = add_if_min;
        this.Count_by_fuel_type = count_by_fuel_type;
        this.RemoveById = removeById;
        this.RemoveFirst = removeFirst;
        this.Execute_add_if_max = execute_add_if_max;
        this.Min_By_Number_Of_Wheels = min_by_number_of_wheels;
        this.UpdateID = update_id;
        this.Info = info;
        this.Help = help;
        this.ExecuteScript = execute_script;
        this.Exit = exit;
        this.ServerExit = serverExit;


        commands.add(show);
        commands.add(add);
        commands.add(clear);
        commands.add(print_unique_engine_power);
        commands.add(add_if_min);
        commands.add(count_by_fuel_type);
        commands.add(removeById);
        commands.add(removeFirst);
        commands.add(execute_add_if_max);
        commands.add(min_by_number_of_wheels);
        commands.add(update_id);
        commands.add(info);
        commands.add(help);
        commands.add(execute_script);
        commands.add(exit);
        commands.add(serverExit);
    }

    public List<Command> getCommands() {
        return commands;
    }


    public boolean exit(String stringArgument, Object objectArgument) {
        return Exit.execute(stringArgument, objectArgument);
    }


    public boolean serverExit(String stringArgument, Object objectArgument) {
        return ServerExit.execute(stringArgument, objectArgument);
    }

    public boolean show(String stringArgument, Object objectArgument) {
        return Show.execute(stringArgument, objectArgument);
    }

    public boolean execute_script(String stringArgument, Object objectArgument) {
        return ExecuteScript.execute(stringArgument, objectArgument);
    }

    public boolean info(String stringArgument, Object objectArgument) {
        return Info.execute(stringArgument, objectArgument);
    }

    public boolean help(String stringArgument, Object objectArgument) {
        return Help.execute(stringArgument, objectArgument);
    }

    public boolean update_id(String stringArgument, Object objectArgument) {
        return UpdateID.execute(stringArgument, objectArgument);
    }

    public boolean remove_frist(String stringArgument, Object objectArgument) {
        return RemoveFirst.execute(stringArgument, objectArgument);
    }

    public boolean print_unique_engine_power(String stringArgument, Object objectArgument) {
        return Print_unique_engine_power.execute(stringArgument, objectArgument);
    }

    public boolean add_if_min(String stringArgument, Object objectArgument) {
        return Add_if_min.execute(stringArgument, objectArgument);
    }

    public boolean count_by_fuel_type(String stringArgument, Object objectArgument) {
        return Count_by_fuel_type.execute(stringArgument, objectArgument);
    }

    public boolean min_by_number_of_wheels(String stringArgument, Object objectArgument) {
        return Min_By_Number_Of_Wheels.execute(stringArgument, objectArgument);
    }


    public boolean add(String stringArgument, Object objectArgument) {
        return Add.execute(stringArgument, objectArgument);
    }
    public boolean clear(String stringArgument, Object objectArgument) {
        return Clear.execute(stringArgument, objectArgument);
    }

    public boolean removeById(String stringArgument, Object objectArgument) {
        return RemoveById.execute(stringArgument, objectArgument);
    }

    public boolean execute_add_if_max(String stringArgument, Object objectArgument) {
        return Execute_add_if_max.execute(stringArgument, objectArgument);
    }

}