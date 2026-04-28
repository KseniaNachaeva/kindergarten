package main.java.kindergarten.ui.command.child;

import main.java.kindergarten.service.ChildService;
import main.java.kindergarten.ui.ConsoleReader;
import main.java.kindergarten.ui.command.Command;

public class DeleteChildCommand implements Command {

    private final ChildService childService;
    private final ConsoleReader reader;

    public DeleteChildCommand(ChildService childService, ConsoleReader reader) {
        this.childService = childService;
        this.reader = reader;
    }

    @Override
    public String getDescription() {
        return "Удалить ребёнка";
    }

    @Override
    public void execute() {
        System.out.println("\n--- Удаление ребёнка ---");
        long id = reader.readLong("ID ребёнка: ");
        try {
            childService.deleteChild(id);
            System.out.println("Ребёнок удалён.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
