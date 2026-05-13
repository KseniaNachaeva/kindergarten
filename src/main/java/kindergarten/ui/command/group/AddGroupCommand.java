package main.java.kindergarten.ui.command.group;

import main.java.kindergarten.model.Group;
import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.ui.ConsoleReader;
import main.java.kindergarten.ui.command.Command;

public class AddGroupCommand implements Command {

    private final GroupService groupService;
    private final ConsoleReader reader;

    public AddGroupCommand(GroupService groupService, ConsoleReader reader) {
        this.groupService = groupService;
        this.reader = reader;
    }

    @Override
    public String getDescription() {
        return "Добавить группу";
    }

    @Override
    public void execute() {
        System.out.println("\n--- Добавление группы ---");
        String name = reader.readLine("Название группы: ");
        int number = reader.readInt("Номер группы: ");
        try {
            Group group = groupService.createGroup(name, number);
            System.out.println("Группа добавлена: ID=" + group.getId()
                    + ", " + group.getName() + " (№" + group.getNumber() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
