package main.java.kindergarten.ui.command;

public class ExitCommand implements Command {

    private final String description;

    public ExitCommand(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute() {
    }
}
