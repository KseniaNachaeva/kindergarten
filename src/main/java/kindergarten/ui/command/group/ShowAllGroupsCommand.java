package main.java.kindergarten.ui.command.group;

import main.java.kindergarten.model.Group;
import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.ui.command.Command;

import java.util.List;

public class ShowAllGroupsCommand implements Command {

    private final GroupService groupService;

    public ShowAllGroupsCommand(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public String getDescription() {
        return "Показать все группы";
    }

    @Override
    public void execute() {
        System.out.println("\n--- Все группы ---");
        List<Group> groups = groupService.getAllGroups();
        if (groups.isEmpty()) {
            System.out.println("Группы не найдены.");
            return;
        }
        for (Group group : groups) {
            System.out.println("ID=" + group.getId() + " | " + group.getName() + " (№" + group.getNumber() + ")");
        }
    }
}
