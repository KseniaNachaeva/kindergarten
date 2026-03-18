package main.java.kindergarten.service;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.model.Group;

import java.util.List;
import java.util.Map;

public interface KindergartenService {

    Group createGroup(String name, int number);

    Group updateGroup(Long id, String name, int number);

    void deleteGroup(Long id);

    Group getGroupById(Long id);

    List<Group> getAllGroups();

    Child createChild(String fullName, boolean male, int age, Long groupId);

    Child updateChild(Long id, String fullName, boolean male, int age, Long groupId);

    void deleteChild(Long id);

    Child getChildById(Long id);

    List<Child> getAllChildren();

    List<Child> getChildrenByGroupId(Long groupId);

    Map<Group, List<Child>> getGroupsInfo();
}
