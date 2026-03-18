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


        Child child1 = new Child("Иванов Иван", true, 5);
        child1.setGroupId(group1.getId());
        childRepository.save(child1);

        Child child2 = new Child("Петрова Анна", false, 4);
        child2.setGroupId(group1.getId());
        childRepository.save(child2);

        Child child3 = new Child("Сидоров Максим", true, 6);
        child3.setGroupId(group1.getId());
        childRepository.save(child3);

        Child child4 = new Child("Смирнова Елена", false, 5);
        child4.setGroupId(group2.getId());
        childRepository.save(child4);

        Child child5 = new Child("Козлов Дмитрий", true, 4);
        child5.setGroupId(group2.getId());
        childRepository.save(child5);

        Child child6 = new Child("Морозова Ольга", false, 6);
        child6.setGroupId(group3.getId());
        childRepository.save(child6);

        Child child7 = new Child("Волков Сергей", true, 5);
        child7.setGroupId(group3.getId());
        childRepository.save(child7);

        Child child8 = new Child("Лебедева Мария", false, 4);
        child8.setGroupId(group3.getId());
        childRepository.save(child8);

        childRepository.save(new Child("Новиков Артём", true, 3));

        System.out.println("У нас всего: 3 группы, 9 детей");
    }
}
