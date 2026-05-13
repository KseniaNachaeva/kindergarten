package main.java.kindergarten.config.factory;

import main.java.kindergarten.repository.ChildRepository;
import main.java.kindergarten.repository.GroupRepository;
import main.java.kindergarten.repository.inmemory.InMemoryChildRepository;
import main.java.kindergarten.repository.inmemory.InMemoryGroupRepository;

public class RepositoryFactory {

    public GroupRepository createGroupRepository() {
        return new InMemoryGroupRepository();
    }

    public ChildRepository createChildRepository() {
        return new InMemoryChildRepository();
    }
}
