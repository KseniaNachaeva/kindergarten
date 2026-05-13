package main.java.kindergarten.service.impl;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.model.Group;
import main.java.kindergarten.repository.ChildRepository;
import main.java.kindergarten.repository.GroupRepository;
import main.java.kindergarten.service.GroupService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ChildRepository childRepository;

    public GroupServiceImpl(GroupRepository groupRepository, ChildRepository childRepository) {
        this.groupRepository = groupRepository;
        this.childRepository = childRepository;
    }

    @Override
    public Group createGroup(String name, int number) {
        ensureNameUnique(name, null);
        return groupRepository.save(new Group(name, number));
    }

    @Override
    public Group updateGroup(Long id, String name, int number) {
        Group group = getGroupById(id);
        ensureNameUnique(name, id);
        group.setName(name);
        group.setNumber(number);
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new IllegalArgumentException("Группа с ID " + id + " не найдена");
        }
        for (Child child : childRepository.findByGroupId(id)) {
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
    public Map<Group, List<Child>> getGroupsInfo() {
        Map<Group, List<Child>> result = new LinkedHashMap<>();
        for (Group group : groupRepository.findAll()) {
            result.put(group, childRepository.findByGroupId(group.getId()));
        }
        return result;
    }

    private void ensureNameUnique(String name, Long excludeId) {
        for (Group g : groupRepository.findAll()) {
            if ((excludeId == null || !g.getId().equals(excludeId)) && g.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Группа с названием '" + name + "' уже существует");
            }
        }
    }
}
