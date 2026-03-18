package main.java.kindergarten.ui;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.model.Group;
import main.java.kindergarten.service.KindergartenService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApplication {
    
    private final KindergartenService service;
    private final Scanner scanner;
    
    public ConsoleApplication(KindergartenService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println();
        System.out.println("=== Детский сад РОМАШКА ===");
        
        while (true) {
            printMainMenu();
            String choice = readLine("Выберите пункт меню: ");
            
            switch (choice) {
                case "1":
                    manageGroups();
                    break;
                case "2":
                    manageChildren();
                    break;
                case "3":
                    showGroupsInfo();
                    break;
                case "4":
                    System.out.println("Выход из приложения...");
                    return;
                default:
                    System.out.println("Неверный пункт меню. Попробуйте снова.");
            }
        }
    }
    
    private void printMainMenu() {
        System.out.println("\n--- Главное меню ---");
        System.out.println("1. Управление группами");
        System.out.println("2. Управление детьми");
        System.out.println("3. Информация по группам");
        System.out.println("4. Выход");
    }
    
    private void manageGroups() {
        while (true) {
            System.out.println("\n--- Управление группами ---");
            System.out.println("1. Добавить группу");
            System.out.println("2. Редактировать группу");
            System.out.println("3. Удалить группу");
            System.out.println("4. Показать все группы");
            System.out.println("5. Назад");
            
            String choice = readLine("Выберите пункт меню: ");
            
            switch (choice) {
                case "1":
                    addGroup();
                    break;
                case "2":
                    editGroup();
                    break;
                case "3":
                    deleteGroup();
                    break;
                case "4":
                    showAllGroups();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Неверный пункт меню.");
            }
        }
    }
    
    private void manageChildren() {
        while (true) {
            System.out.println("\n--- Управление детьми ---");
            System.out.println("1. Добавить ребёнка");
            System.out.println("2. Редактировать ребёнка");
            System.out.println("3. Удалить ребёнка");
            System.out.println("4. Показать всех детей");
            System.out.println("5. Назад");
            
            String choice = readLine("Выберите пункт меню: ");
            
            switch (choice) {
                case "1":
                    addChild();
                    break;
                case "2":
                    editChild();
                    break;
                case "3":
                    deleteChild();
                    break;
                case "4":
                    showAllChildren();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Неверный пункт меню.");
            }
        }
    }
    
    private void addGroup() {
        System.out.println("\n--- Добавление группы ---");
        String name = readLine("Название группы: ");
        int number = readInt("Номер группы: ");
        
        try {
            Group group = service.createGroup(name, number);
            System.out.println("Группа добавлена: ID=" + group.getId() + ", " + group.getName() + " (№" + group.getNumber() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void editGroup() {
        System.out.println("\n--- Редактирование группы ---");
        Long id = readLong("ID группы: ");

        try {
            Group group = service.getGroupById(id);
            System.out.println("Текущие данные: " + group.getName() + " (№" + group.getNumber() + ")");

            String name = readLine("Новое название (оставьте пустым, чтобы не менять): ");
            String numberStr = readLine("Новый номер (оставьте пустым, чтобы не менять): ");

            String newName = name.isEmpty() ? group.getName() : name;
            int newNumber;
            try {
                newNumber = numberStr.isEmpty() ? group.getNumber() : Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный номер, оставлено старое значение.");
                newNumber = group.getNumber();
            }

            service.updateGroup(id, newName, newNumber);
            System.out.println("Группа обновлена.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void deleteGroup() {
        System.out.println("\n--- Удаление группы ---");
        Long id = readLong("ID группы: ");
        
        try {
            service.deleteGroup(id);
            System.out.println("Группа удалена.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void showAllGroups() {
        System.out.println("\n--- Все группы ---");
        List<Group> groups = service.getAllGroups();
        
        if (groups.isEmpty()) {
            System.out.println("Группы не найдены.");
            return;
        }
        
        for (Group group : groups) {
            System.out.println("ID=" + group.getId() + " | " + group.getName() + " (№" + group.getNumber() + ")");
        }
    }
    private void addChild() {
        System.out.println("\n--- Добавление ребёнка ---");
        String fullName = readLine("ФИО ребёнка: ");
        String maleStr = readLine("Пол (м/ж): ");
        while (!maleStr.trim().equalsIgnoreCase("м") && !maleStr.trim().equalsIgnoreCase("ж")) {
            System.out.println("Некорректный пол. Введите 'м' или 'ж': ");
            maleStr = readLine("Пол (м/ж): ");
        }
        System.out.print("Возраст: ");
        int age;
        while (true) {
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0 || age > 7) {
                    System.out.println("Возраст должен быть от 1 до 7 лет. Введите возраст: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число. Возраст: ");
            }
        }

        System.out.println("Доступные группы:");
        showAllGroups();
        String groupIdStr = readLine("ID группы (оставьте пустым, если без группы): ");
        Long groupId = groupIdStr.isEmpty() ? null : Long.parseLong(groupIdStr);

        try {
            boolean male = maleStr.trim().equalsIgnoreCase("м");
            Child child = service.createChild(fullName, male, age, groupId);
            System.out.println("Ребёнок добавлен: ID=" + child.getId() + ", " + child.getFullName() +
                    ", " + (child.isMale() ? "м" : "ж") + ", " + child.getAge() + " лет");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void editChild() {
        System.out.println("\n--- Редактирование ребёнка ---");
        Long id = readLong("ID ребёнка: ");
        
        try {
            Child child = service.getChildById(id);
            System.out.println("Текущие данные: " + child.getFullName() + 
                    ", " + (child.isMale() ? "м" : "ж") + ", " + child.getAge() + " лет" +
                    (child.getGroupId() != null ? ", группа ID=" + child.getGroupId() : ""));
            
            String fullName = readLine("Новое ФИО (оставьте пустым, чтобы не менять): ");
            String maleStr = readLine("Новый пол м/ж (оставьте пустым, чтобы не менять): ");
            String ageStr = readLine("Новый возраст (оставьте пустым, чтобы не менять): ");
            
            System.out.println("Доступные группы:");
            showAllGroups();
            String groupIdStr = readLine("ID новой группы (пусто = без группы, '-' = не менять): ");
            
            String newFullName = fullName.isEmpty() ? child.getFullName() : fullName;
            boolean newMale = maleStr.isEmpty() ? child.isMale() : maleStr.trim().equalsIgnoreCase("м");
            int newAge = ageStr.isEmpty() ? child.getAge() : Integer.parseInt(ageStr);
            Long newGroupId = "-".equals(groupIdStr) ? child.getGroupId() : 
                    (groupIdStr.isEmpty() ? null : Long.parseLong(groupIdStr));
            
            service.updateChild(id, newFullName, newMale, newAge, newGroupId);
            System.out.println("Ребёнок обновлён.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void deleteChild() {
        System.out.println("\n--- Удаление ребёнка ---");
        Long id = readLong("ID ребёнка: ");
        
        try {
            service.deleteChild(id);
            System.out.println("Ребёнок удалён.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void showAllChildren() {
        System.out.println("\n--- Все дети ---");
        List<Child> children = service.getAllChildren();
        
        if (children.isEmpty()) {
            System.out.println("Дети не найдены.");
            return;
        }
        
        for (Child child : children) {
            System.out.println("ID=" + child.getId() + " | " + child.getFullName() + 
                    ", " + (child.isMale() ? "м" : "ж") + ", " + child.getAge() + " лет" +
                    (child.getGroupId() != null ? ", группа ID=" + child.getGroupId() : " (без группы)"));
        }
    }

    private void showGroupsInfo() {
        System.out.println("\n=== Информация по группам ===");
        Map<Group, List<Child>> groupsInfo = service.getGroupsInfo();
        
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
                    System.out.println("  - " + child.getFullName() + 
                            ", " + (child.isMale() ? "м" : "ж") + ", " + child.getAge() + " лет");
                }
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            }
        }
    }
    
    private long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            }
        }
    }
}
