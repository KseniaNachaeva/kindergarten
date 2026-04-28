package main.java.kindergarten.ui.command.child;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.model.Group;
import main.java.kindergarten.service.ChildService;
import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.ui.ConsoleReader;
import main.java.kindergarten.ui.command.Command;

import java.util.List;

public class AddChildCommand implements Command {

    private final ChildService childService;
    private final GroupService groupService;
    private final ConsoleReader reader;

    public AddChildCommand(ChildService childService, GroupService groupService, ConsoleReader reader) {
        this.childService = childService;
        this.groupService = groupService;
        this.reader = reader;
    }

    @Override
    public String getDescription() {
        return "Добавить ребёнка";
    }

    @Override
    public void execute() {
        System.out.println("\n--- Добавление ребёнка ---");
        String fullName = reader.readLine("ФИО ребёнка: ");

        String maleStr;
        do {
            maleStr = reader.readLine("Пол (м/ж): ").trim();
        } while (!maleStr.equalsIgnoreCase("м") && !maleStr.equalsIgnoreCase("ж"));

        int age = reader.readInt("Возраст: ");

        printAvailableGroups();
        String groupIdStr = reader.readLine("ID группы (пусто = без группы): ");
        Long groupId = groupIdStr.isEmpty() ? null : Long.parseLong(groupIdStr);

        try {
            boolean male = maleStr.equalsIgnoreCase("м");
            Child child = childService.createChild(fullName, male, age, groupId);
            System.out.println("Ребёнок добавлен: ID=" + child.getId() + ", " + child.getFullName()
                    + ", " + (child.isMale() ? "м" : "ж") + ", " + child.getAge() + " лет");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void printAvailableGroups() {
        List<Group> groups = groupService.getAllGroups();
        if (groups.isEmpty()) {
            System.out.println("Группы не найдены.");
            return;
        }
        System.out.println("Доступные группы:");
        for (Group group : groups) {
            System.out.println("  ID=" + group.getId() + " | " + group.getName() + " (№" + group.getNumber() + ")");
        }
    }
}
