package main.java.kindergarten.repository.inmemory;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.repository.ChildRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryChildRepository implements ChildRepository {
    
    private final Map<Long, Child> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Child save(Child child) {
        if (child.getId() == null) {
            child.setId(idGenerator.getAndIncrement());
        }
        storage.put(child.getId(), child);
        return child;
    }
    
    @Override
    public Optional<Child> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }
    
    @Override
    public List<Child> findAll() {
        return new ArrayList<>(storage.values());
    }
    
    @Override
    public List<Child> findByGroupId(Long groupId) {
        return storage.values().stream()
                .filter(child -> groupId.equals(child.getGroupId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
