package main.java.kindergarten.config.init;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.model.Group;
import main.java.kindergarten.repository.ChildRepository;
import main.java.kindergarten.repository.GroupRepository;

public class DataInitializer {

    private final GroupRepository groupRepository;
    private final ChildRepository childRepository;

    public DataInitializer(GroupRepository groupRepository, ChildRepository childRepository) {
        this.groupRepository = groupRepository;
        this.childRepository = childRepository;
    }

    public void init() {
        if (!groupRepository.findAll().isEmpty()) {
            return;
        }

        Group group1 = groupRepository.save(new Group("Солнышко", 1));
        Group group2 = groupRepository.save(new Group("Ромашка", 2));
        Group group3 = groupRepository.save(new Group("Звёздочка", 3));

        childRepository.save(new Child.Builder().fullName("Иванов Иван").male(true).age(5).groupId(group1.getId()).build());
        childRepository.save(new Child.Builder().fullName("Петрова Анна").male(false).age(4).groupId(group1.getId()).build());
        childRepository.save(new Child.Builder().fullName("Сидоров Максим").male(true).age(6).groupId(group1.getId()).build());
        childRepository.save(new Child.Builder().fullName("Смирнова Елена").male(false).age(5).groupId(group2.getId()).build());
        childRepository.save(new Child.Builder().fullName("Козлов Дмитрий").male(true).age(4).groupId(group2.getId()).build());
        childRepository.save(new Child.Builder().fullName("Морозова Ольга").male(false).age(6).groupId(group3.getId()).build());
        childRepository.save(new Child.Builder().fullName("Волков Сергей").male(true).age(5).groupId(group3.getId()).build());
        childRepository.save(new Child.Builder().fullName("Лебедева Мария").male(false).age(4).groupId(group3.getId()).build());
        childRepository.save(new Child.Builder().fullName("Новиков Артём").male(true).age(3).build());

        int groupCount = groupRepository.findAll().size();
        int childCount = childRepository.findAll().size();
        System.out.println("Инициализировано: " + groupCount + " групп, " + childCount + " детей");
    }
}
