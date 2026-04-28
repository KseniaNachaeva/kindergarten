package main.java.kindergarten.service.impl;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.repository.ChildRepository;
import main.java.kindergarten.repository.GroupRepository;
import main.java.kindergarten.service.ChildService;

import java.util.List;

public class ChildServiceImpl implements ChildService {

    private static final int MIN_AGE = 1;
    private static final int MAX_AGE = 7;

    private final ChildRepository childRepository;
    private final GroupRepository groupRepository;

    public ChildServiceImpl(ChildRepository childRepository, GroupRepository groupRepository) {
        this.childRepository = childRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Child createChild(String fullName, boolean male, int age, Long groupId) {
        validateAge(age);
        validateGroupExists(groupId);
        ensureNotDuplicate(fullName, age, groupId, null);
        return childRepository.save(new Child.Builder()
                .fullName(fullName).male(male).age(age).groupId(groupId).build());
    }

    @Override
    public Child updateChild(Long id, String fullName, boolean male, int age, Long groupId) {
        Child child = getChildById(id);
        validateAge(age);
        validateGroupExists(groupId);
        ensureNotDuplicate(fullName, age, groupId, id);
        child.setFullName(fullName);
        child.setMale(male);
        child.setAge(age);
        child.setGroupId(groupId);
        return childRepository.save(child);
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

    private void validateAge(int age) {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException("Возраст должен быть от " + MIN_AGE + " до " + MAX_AGE + " лет");
        }
    }

    private void validateGroupExists(Long groupId) {
        if (groupId != null && !groupRepository.existsById(groupId)) {
            throw new IllegalArgumentException("Группа с ID " + groupId + " не найдена");
        }
    }

    private void ensureNotDuplicate(String fullName, int age, Long groupId, Long excludeId) {
        if (groupId == null) return;
        for (Child c : childRepository.findByGroupId(groupId)) {
            if ((excludeId == null || !c.getId().equals(excludeId))
                    && c.getFullName().equalsIgnoreCase(fullName) && c.getAge() == age) {
                throw new IllegalArgumentException(
                        "Ребёнок '" + fullName + "' такого же возраста уже есть в этой группе");
            }
        }
    }
}
