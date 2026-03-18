package main.java.kindergarten.config;

import main.java.kindergarten.config.init.DataInitializer;
import main.java.kindergarten.repository.ChildRepository;
import main.java.kindergarten.repository.GroupRepository;
import main.java.kindergarten.repository.inmemory.InMemoryChildRepository;
import main.java.kindergarten.repository.inmemory.InMemoryGroupRepository;
import main.java.kindergarten.service.KindergartenService;
import main.java.kindergarten.service.impl.KindergartenServiceImpl;
import main.java.kindergarten.ui.ConsoleApplication;

public class AppConfig {
    
    private final KindergartenService service;
    
    public AppConfig() {

        GroupRepository groupRepository = new InMemoryGroupRepository();
        ChildRepository childRepository = new InMemoryChildRepository();
        
        this.service = new KindergartenServiceImpl(groupRepository, childRepository);

        new DataInitializer(groupRepository, childRepository).init();
    }
    
    public KindergartenService getService() {
        return service;
    }
    
    public ConsoleApplication createConsoleApplication() {
        return new ConsoleApplication(service);
    }
}
