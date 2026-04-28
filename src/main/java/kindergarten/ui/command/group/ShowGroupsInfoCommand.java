package main.java.kindergarten.ui.command.group;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.model.Group;
import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.ui.command.Command;

import java.util.List;
import java.util.Map;

public class ShowGroupsInfoCommand implements Command {

    private final GroupService groupService;

    public ShowGroupsInfoCommand(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public String getDescription() {
        return "Информация по группам";
    }

    @Override
    public void execute() {
        System.out.println("\n=== Информация по группам ===");
        Map<Group, List<Child>> groupsInfo = groupService.getGroupsInfo();
        if (groupsInfo.isEmpty()) {
            System.out.println("Группы не найдены.");
            return;
        }
        for (Map.Entry<Group, List<Child>> entry : groupsInfo.entrySet()) {
            Group group = entry.getKey();
            List<Child> children = entry.getValue();
            System.out.println("\nГруппа: " + group.getName() + " (№" + group.getNumber() + "), ID=" + group.getId());
            System.out.println("Детей в группе: " + children.size());
            if (children.isEmpty()) {
                System.out.println("  (нет детей)");
            } else {
                for (Child child : children) {
                    System.out.println("  - " + child.getFullName()
                            + ", " + (child.isMale() ? "м" : "ж") + ", " + child.getAge() + " лет");
                }
            }
        }
    }
}
