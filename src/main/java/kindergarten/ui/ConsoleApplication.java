package main.java.kindergarten.ui;

import main.java.kindergarten.service.ChildService;
import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.ui.command.Command;
import main.java.kindergarten.ui.command.ExitCommand;
import main.java.kindergarten.ui.command.child.AddChildCommand;
import main.java.kindergarten.ui.command.child.DeleteChildCommand;
import main.java.kindergarten.ui.command.child.EditChildCommand;
import main.java.kindergarten.ui.command.child.ShowAllChildrenCommand;
import main.java.kindergarten.ui.command.group.AddGroupCommand;
import main.java.kindergarten.ui.command.group.DeleteGroupCommand;
import main.java.kindergarten.ui.command.group.EditGroupCommand;
import main.java.kindergarten.ui.command.group.ShowAllGroupsCommand;
import main.java.kindergarten.ui.command.group.ShowGroupsInfoCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {

    private final GroupService groupService;
    private final ChildService childService;
    private final ConsoleReader reader;

    public ConsoleApplication(GroupService groupService, ChildService childService) {
        this.groupService = groupService;
        this.childService = childService;
        this.reader = new ConsoleReader(new Scanner(System.in));
    }

    public void run() {
        System.out.println("\n=== Детский сад ===");
        runMenu("Главное меню", Arrays.asList(
                groupsMenuCommand(),
                childrenMenuCommand(),
                new ShowGroupsInfoCommand(groupService),
                new ExitCommand("Выход")
        ));
    }

    private void runMenu(String title, List<Command> commands) {
        boolean active = true;
        while (active) {
            System.out.println("\n--- " + title + " ---");
            for (int i = 0; i < commands.size(); i++) {
                System.out.println((i + 1) + ". " + commands.get(i).getDescription());
            }
            int choice = reader.readInt("Выберите пункт меню: ") - 1;
            if (choice < 0 || choice >= commands.size()) {
                System.out.println("Неверный пункт меню.");
                continue;
            }
            Command selected = commands.get(choice);
            if (selected instanceof ExitCommand) {
                active = false;
            } else {
                selected.execute();
            }
        }
    }

    private Command groupsMenuCommand() {
        return new Command() {
            @Override
            public String getDescription() {
                return "Управление группами";
            }

            @Override
            public void execute() {
                runMenu("Управление группами", Arrays.asList(
                        new AddGroupCommand(groupService, reader),
                        new EditGroupCommand(groupService, reader),
                        new DeleteGroupCommand(groupService, reader),
                        new ShowAllGroupsCommand(groupService),
                        new ExitCommand("Назад")
                ));
            }
        };
    }

    private Command childrenMenuCommand() {
        return new Command() {
            @Override
            public String getDescription() {
                return "Управление детьми";
            }

            @Override
            public void execute() {
                runMenu("Управление детьми", Arrays.asList(
                        new AddChildCommand(childService, groupService, reader),
                        new EditChildCommand(childService, groupService, reader),
                        new DeleteChildCommand(childService, reader),
                        new ShowAllChildrenCommand(childService),
                        new ExitCommand("Назад")
                ));
            }
        };
    }
}
