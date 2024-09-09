package server.UI;

public interface Command {
    String getName();

    String getArguments();

    String getDescription();

    boolean execute(String commandStringArgument, Object commandObjectArgument);
}