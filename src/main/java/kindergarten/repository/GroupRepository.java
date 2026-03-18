package main.java.kindergarten.repository;

import main.java.kindergarten.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    
    Group save(Group group);
    
    Optional<Group> findById(Long id);
    
    List<Group> findAll();
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}
