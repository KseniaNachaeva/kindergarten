package main.java.kindergarten.ui.command.group;

import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.ui.ConsoleReader;
import main.java.kindergarten.ui.command.Command;

public class DeleteGroupCommand implements Command {

    private final GroupService groupService;
    private final ConsoleReader reader;

    public DeleteGroupCommand(GroupService groupService, ConsoleReader reader) {
        this.groupService = groupService;
        this.reader = reader;
    }

    @Override
    public String getDescription() {
        return "Удалить группу";
    }

    @Override
    public void execute() {
        System.out.println("\n--- Удаление группы ---");
        long id = reader.readLong("ID группы: ");
        try {
            groupService.deleteGroup(id);
            System.out.println("Группа удалена.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
