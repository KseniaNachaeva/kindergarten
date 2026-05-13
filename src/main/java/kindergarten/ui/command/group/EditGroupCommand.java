package main.java.kindergarten.ui.command.group;

import main.java.kindergarten.model.Group;
import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.ui.ConsoleReader;
import main.java.kindergarten.ui.command.Command;

public class EditGroupCommand implements Command {

    private final GroupService groupService;
    private final ConsoleReader reader;

    public EditGroupCommand(GroupService groupService, ConsoleReader reader) {
        this.groupService = groupService;
        this.reader = reader;
    }

    @Override
    public String getDescription() {
        return "Редактировать группу";
    }

    @Override
    public void execute() {
        System.out.println("\n--- Редактирование группы ---");
        long id = reader.readLong("ID группы: ");
        try {
            Group group = groupService.getGroupById(id);
            System.out.println("Текущие данные: " + group.getName() + " (№" + group.getNumber() + ")");

            String name = reader.readLine("Новое название (пусто = не менять): ");
            String numberStr = reader.readLine("Новый номер (пусто = не менять): ");

            String newName = name.isEmpty() ? group.getName() : name;
            int newNumber;
            try {
                newNumber = numberStr.isEmpty() ? group.getNumber() : Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный номер, оставлено старое значение.");
                newNumber = group.getNumber();
            }

            groupService.updateGroup(id, newName, newNumber);
            System.out.println("Группа обновлена.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
