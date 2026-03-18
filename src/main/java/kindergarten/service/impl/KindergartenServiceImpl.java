package main.java.kindergarten.service.impl;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.model.Group;
import main.java.kindergarten.repository.ChildRepository;
import main.java.kindergarten.repository.GroupRepository;
import main.java.kindergarten.service.KindergartenService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KindergartenServiceImpl implements KindergartenService {
    
    private final GroupRepository groupRepository;
    private final ChildRepository childRepository;
    
    public KindergartenServiceImpl(GroupRepository groupRepository, ChildRepository childRepository) {
        this.groupRepository = groupRepository;
        this.childRepository = childRepository;
    }
    
    @Override
    public Group createGroup(String name, int number) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название группы не может быть пустым");
        }
        if (number <= 0) {
            throw new IllegalArgumentException("Номер группы должен быть положительным числом");
        }

        List<Group> allGroups = groupRepository.findAll();
        for (Group group : allGroups) {
            if (group.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Группа с названием '" + name + "' уже существует");
            }
        }

        Group group = new Group(name, number);
        return groupRepository.save(group);
    }
    
    @Override
    public Group updateGroup(Long id, String name, int number) {
        Group existingGroup = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Группа с ID " + id + " не найдена"));

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название группы не может быть пустым");
        }
        if (number <= 0) {
            throw new IllegalArgumentException("Номер группы должен быть положительным числом");
        }

        List<Group> allGroups = groupRepository.findAll();
        for (Group group : allGroups) {
            if (!group.getId().equals(id) && group.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Группа с названием '" + name + "' уже существует");
            }
        }

        existingGroup.setName(name);
        existingGroup.setNumber(number);
        return groupRepository.save(existingGroup);
    }
    
    @Override
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new IllegalArgumentException("Группа с ID " + id + " не найдена");
        }
        List<Child> children = childRepository.findByGroupId(id);
        for (Child child : children) {
            child.setGroupId(null);
            childRepository.save(child);
        }
        groupRepository.deleteById(id);
    }
    
    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Группа с ID " + id + " не найдена"));
    }
    
    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
    
    @Override
    public Child createChild(String fullName, boolean male, int age, Long groupId) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("ФИО ребёнка не может быть пустым");
        }
        if (age <= 0 || age > 7) {
            throw new IllegalArgumentException("Возраст должен быть от 1 до 7 лет");
        }
        if (groupId != null && !groupRepository.existsById(groupId)) {
            throw new IllegalArgumentException("Группа с ID " + groupId + " не найдена");
        }

        if (groupId != null) {
            List<Child> groupChildren = childRepository.findByGroupId(groupId);
            for (Child child : groupChildren) {
                if (child.getFullName().equalsIgnoreCase(fullName) && child.getAge() == age) {
                    throw new IllegalArgumentException("Ребёнок '" + fullName + "' такого же возраста уже есть в этой группе");
                }
            }
        }

        Child child = new Child(fullName, male, age);
        child.setGroupId(groupId);
        return childRepository.save(child);
    }
    
    @Override
    public Child updateChild(Long id, String fullName, boolean male, int age, Long groupId) {
        Child existingChild = childRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ребёнок с ID " + id + " не найден"));

        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("ФИО ребёнка не может быть пустым");
        }
        if (age <= 0 || age > 7) {
            throw new IllegalArgumentException("Возраст должен быть от 1 до 7 лет");
        }
        if (groupId != null && !groupRepository.existsById(groupId)) {
            throw new IllegalArgumentException("Группа с ID " + groupId + " не найдена");
        }

        if (groupId != null) {
            List<Child> groupChildren = childRepository.findByGroupId(groupId);
            for (Child child : groupChildren) {
                if (!child.getId().equals(id) && child.getFullName().equalsIgnoreCase(fullName) && child.getAge() == age) {
                    throw new IllegalArgumentException("Ребёнок '" + fullName + "' такого же возраста уже есть в этой группе");
                }
            }
        }

        existingChild.setFullName(fullName);
        existingChild.setMale(male);
        existingChild.setAge(age);
        existingChild.setGroupId(groupId);
        return childRepository.save(existingChild);
    }
    
    @Override
    public void deleteChild(Long id) {
        if (!childRepository.existsById(id)) {
            throw new IllegalArgumentException("Ребёнок с ID " + id + " не найден");
        }
        childRepository.deleteById(id);
    }
    
    @Override
    public Child getChildById(Long id) {
        return childRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ребёнок с ID " + id + " не найден"));
    }
    
    @Override
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }
    
    @Override
    public List<Child> getChildrenByGroupId(Long groupId) {
        return childRepository.findByGroupId(groupId);
    }
    
    @Override
    public Map<Group, List<Child>> getGroupsInfo() {
        Map<Group, List<Child>> result = new LinkedHashMap<>();
        List<Group> groups = groupRepository.findAll();
        
        for (Group group : groups) {
            List<Child> children = childRepository.findByGroupId(group.getId());
            result.put(group, children);
        }
        
        return result;
    }
}
