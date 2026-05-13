package main.java.kindergarten.service;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.model.Group;

import java.util.List;
import java.util.Map;

public interface GroupService {

    Group createGroup(String name, int number);

    Group updateGroup(Long id, String name, int number);

    void deleteGroup(Long id);

    Group getGroupById(Long id);

    List<Group> getAllGroups();

    Map<Group, List<Child>> getGroupsInfo();
}
