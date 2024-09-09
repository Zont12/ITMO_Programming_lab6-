package server.commands;

import server.UI.Command;


public abstract class AbstractCommand implements Command {
    private String name;

    private String arguments;

    private String description;

    public AbstractCommand(String name, String arguments, String description) {
        this.name = name;
        this.arguments = arguments;
        this.description = description;
    }

    public abstract boolean execute(String name, Object argument);


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getArguments() {
        return arguments;
    }
    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return name + " " + arguments + " (" + description + ")";
    }

    @Override
    public int hashCode() {
        return name.hashCode() + arguments.hashCode() + description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Command other = (Command) obj;
        return name.equals(other.getName()) && arguments.equals(other.getArguments()) &&
                description.equals(other.getDescription());
    }




}
