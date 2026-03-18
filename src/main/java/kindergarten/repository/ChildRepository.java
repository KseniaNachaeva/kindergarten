package main.java.kindergarten.repository;

import main.java.kindergarten.model.Child;

import java.util.List;
import java.util.Optional;

public interface ChildRepository {
    
    Child save(Child child);
    
    Optional<Child> findById(Long id);
    
    List<Child> findAll();
    
    List<Child> findByGroupId(Long groupId);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}
