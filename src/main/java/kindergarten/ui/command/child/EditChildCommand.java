package main.java.kindergarten.ui.command.child;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.model.Group;
import main.java.kindergarten.service.ChildService;
import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.ui.ConsoleReader;
import main.java.kindergarten.ui.command.Command;

import java.util.List;

public class EditChildCommand implements Command {

    private final ChildService childService;
    private final GroupService groupService;
    private final ConsoleReader reader;

    public EditChildCommand(ChildService childService, GroupService groupService, ConsoleReader reader) {
        this.childService = childService;
        this.groupService = groupService;
        this.reader = reader;
    }

    @Override
    public String getDescription() {
        return "Редактировать ребёнка";
    }

    @Override
    public void execute() {
        System.out.println("\n--- Редактирование ребёнка ---");
        long id = reader.readLong("ID ребёнка: ");
        try {
            Child child = childService.getChildById(id);
            System.out.println("Текущие данные: " + child.getFullName()
                    + ", " + (child.isMale() ? "м" : "ж") + ", " + child.getAge() + " лет"
                    + (child.getGroupId() != null ? ", группа ID=" + child.getGroupId() : ""));

            String fullName = reader.readLine("Новое ФИО (пусто = не менять): ");
            String maleStr = reader.readLine("Новый пол м/ж (пусто = не менять): ");
            String ageStr = reader.readLine("Новый возраст (пусто = не менять): ");

            printAvailableGroups();
            String groupIdStr = reader.readLine("ID новой группы (пусто = без группы, '-' = не менять): ");

            String newFullName = fullName.isEmpty() ? child.getFullName() : fullName;
            boolean newMale = maleStr.isEmpty() ? child.isMale() : maleStr.trim().equalsIgnoreCase("м");
            int newAge;
            try {
                newAge = ageStr.isEmpty() ? child.getAge() : Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный возраст, оставлено старое значение.");
                newAge = child.getAge();
            }
            Long newGroupId = "-".equals(groupIdStr) ? child.getGroupId()
                    : (groupIdStr.isEmpty() ? null : Long.parseLong(groupIdStr));

            childService.updateChild(id, newFullName, newMale, newAge, newGroupId);
            System.out.println("Ребёнок обновлён.");
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
