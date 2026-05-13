package main.java.kindergarten.config.factory;

import main.java.kindergarten.repository.jdbc.ConnectionManager;
import main.java.kindergarten.repository.ChildRepository;
import main.java.kindergarten.repository.GroupRepository;
import main.java.kindergarten.repository.jdbc.JdbcChildRepository;
import main.java.kindergarten.repository.jdbc.JdbcGroupRepository;

public class RepositoryFactory {

    private final ConnectionManager connectionManager;

    public RepositoryFactory(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public GroupRepository createGroupRepository() {
        return new JdbcGroupRepository(connectionManager);
    }

    public ChildRepository createChildRepository() {
        return new JdbcChildRepository(connectionManager);
    }
}
