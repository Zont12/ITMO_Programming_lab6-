package coomon.exceptions;

public class CommandUsageEX extends Exception {
    public CommandUsageEX(String message) {
        super(message);
    }
    public CommandUsageEX() {super();}
}