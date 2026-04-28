package main.java.kindergarten.service;

import main.java.kindergarten.model.Child;

import java.util.List;

public interface ChildService {

    Child createChild(String fullName, boolean male, int age, Long groupId);

    Child updateChild(Long id, String fullName, boolean male, int age, Long groupId);

    void deleteChild(Long id);

    Child getChildById(Long id);

    List<Child> getAllChildren();

    List<Child> getChildrenByGroupId(Long groupId);
}
