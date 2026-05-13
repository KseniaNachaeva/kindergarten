package main.java.kindergarten.repository.inmemory;

import main.java.kindergarten.model.Group;
import main.java.kindergarten.repository.GroupRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryGroupRepository implements GroupRepository {

    private final Map<Long, Group> storage = new HashMap<>();
    private long nextId = 1;

    @Override
    public Group save(Group group) {
        if (group.getId() == null) {
            group.setId(nextId++);
        }
        storage.put(group.getId(), group);
        return group;
    }

    @Override
    public Optional<Group> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Group> findAll() {
        return new ArrayList<>(storage.values());
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
