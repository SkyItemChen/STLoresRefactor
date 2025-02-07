package silentdream.stlores.model;

public class CommandModel {
    private String command;
    private String message;
    private String commandExtraText;

    public CommandModel() {
    }

    public CommandModel(String command, String commandExtraText, String message) {
        this.command = command;
        this.commandExtraText = commandExtraText;
        this.message = message;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommandExtraText() {
        return commandExtraText;
    }

    public void setCommandExtraText(String commandExtraText) {
        this.commandExtraText = commandExtraText;
    }
}
