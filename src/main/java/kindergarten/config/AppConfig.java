package main.java.kindergarten.config;

import main.java.kindergarten.repository.jdbc.ConnectionManager;
import main.java.kindergarten.config.factory.RepositoryFactory;
import main.java.kindergarten.config.factory.ServiceFactory;
import main.java.kindergarten.config.init.DataInitializer;
import main.java.kindergarten.repository.ChildRepository;
import main.java.kindergarten.repository.GroupRepository;
import main.java.kindergarten.service.ChildService;
import main.java.kindergarten.service.GroupService;
import main.java.kindergarten.ui.ConsoleApplication;

public class AppConfig {

    private final GroupService groupService;
    private final ChildService childService;

    public AppConfig() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.initSchema();

        RepositoryFactory repositoryFactory = new RepositoryFactory(connectionManager);
        GroupRepository groupRepository = repositoryFactory.createGroupRepository();
        ChildRepository childRepository = repositoryFactory.createChildRepository();

        ServiceFactory serviceFactory = new ServiceFactory();
        this.groupService = serviceFactory.createGroupService(groupRepository, childRepository);
        this.childService = serviceFactory.createChildService(childRepository, groupRepository);

        new DataInitializer(groupRepository, childRepository).init();
    }

    public ConsoleApplication createConsoleApplication() {
        return new ConsoleApplication(groupService, childService);
    }
}
