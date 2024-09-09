package server.commands;

public class ExecuteScript extends AbstractCommand {
    public ExecuteScript() {
        super("execute_script", "<file_name>", "Считать скрипт и исполнить его");
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        return true;
    }
}