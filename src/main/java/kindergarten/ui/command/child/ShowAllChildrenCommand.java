package main.java.kindergarten.ui.command.child;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.service.ChildService;
import main.java.kindergarten.ui.command.Command;

import java.util.List;

public class ShowAllChildrenCommand implements Command {

    private final ChildService childService;

    public ShowAllChildrenCommand(ChildService childService) {
        this.childService = childService;
    }

    @Override
    public String getDescription() {
        return "Показать всех детей";
    }

    @Override
    public void execute() {
        System.out.println("\n--- Все дети ---");
        List<Child> children = childService.getAllChildren();
        if (children.isEmpty()) {
            System.out.println("Дети не найдены.");
            return;
        }
        for (Child child : children) {
            System.out.println("ID=" + child.getId() + " | " + child.getFullName()
                    + ", " + (child.isMale() ? "м" : "ж") + ", " + child.getAge() + " лет"
                    + (child.getGroupId() != null ? ", группа ID=" + child.getGroupId() : " (без группы)"));
        }
    }
}
