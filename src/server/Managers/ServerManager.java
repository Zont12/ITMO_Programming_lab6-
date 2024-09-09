package server.Managers;
import coomon.Interaction.Request;
import coomon.Interaction.Response;
import coomon.Interaction.ResponseCode;

public class ServerManager {
    private final CommandManager commandManager;

    public ServerManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response handle(Request request) {
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(), request.getCommandObjectArgument());
        return new Response(responseCode, ResponseOutputer.getAndClear());
    }

    private ResponseCode executeCommand(String command, String commandStringArgument, Object commandObjectArgument) {
        switch (command) {
            case "":
                break;
            case "show":
                if (!commandManager.show(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "update_id":
                if (!commandManager.update_id(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "print_unique_engine_power":
                if (!commandManager.print_unique_engine_power(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "min_by_number_of_wheels":
                if (!commandManager.min_by_number_of_wheels(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "info":
                if (!commandManager.info(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "help":
                if (!commandManager.help(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "add":
                if (!commandManager.add(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "execute_add_if_max":
                if (!commandManager.execute_add_if_max(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "add_if_min":
                if (!commandManager.add_if_min(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_first":
                if (!commandManager.remove_frist(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "count_by_fuel_type":
                if (!commandManager.count_by_fuel_type(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "execute_script":
                if (!commandManager.execute_script(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "server_exit":
                if (!commandManager.serverExit(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                return ResponseCode.SERVER_EXIT;
            default:
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;
    }
}