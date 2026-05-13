package main.java.kindergarten.config.factory;

import main.java.kindergarten.repository.ChildRepository;
import main.java.kindergarten.repository.GroupRepository;
import main.java.kindergarten.service.ChildService;
import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.service.impl.ChildServiceImpl;
import main.java.kindergarten.service.impl.GroupServiceImpl;

public class ServiceFactory {

    public GroupService createGroupService(GroupRepository groupRepository, ChildRepository childRepository) {
        return new GroupServiceImpl(groupRepository, childRepository);
    }

    public ChildService createChildService(ChildRepository childRepository, GroupRepository groupRepository) {
        return new ChildServiceImpl(childRepository, groupRepository);
    }
}
