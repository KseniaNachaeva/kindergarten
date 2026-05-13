package main.java.kindergarten.repository.jdbc;

import main.java.kindergarten.model.Group;
import main.java.kindergarten.repository.GroupRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGroupRepository implements GroupRepository {

    private final ConnectionManager cm;

    public JdbcGroupRepository(ConnectionManager cm) {
        this.cm = cm;
    }

    @Override
    public Group save(Group group) {
        if (group.getId() == null) {
            String sql = "INSERT INTO groups (name, number) VALUES (?, ?) RETURNING id";
            try (Connection conn = cm.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, group.getName());
                stmt.setInt(2, group.getNumber());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    group.setId(rs.getLong("id"));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при сохранении группы", e);
            }
        } else {
            String sql = "UPDATE groups SET name = ?, number = ? WHERE id = ?";
            try (Connection conn = cm.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, group.getName());
                stmt.setInt(2, group.getNumber());
                stmt.setLong(3, group.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при обновлении группы", e);
            }
        }
        return group;
    }

    @Override
    public Optional<Group> findById(Long id) {
        String sql = "SELECT id, name, number FROM groups WHERE id = ?";
        try (Connection conn = cm.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске группы", e);
        }
    }

    @Override
    public List<Group> findAll() {
        String sql = "SELECT id, name, number FROM groups ORDER BY id";
        List<Group> result = new ArrayList<>();
        try (Connection conn = cm.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка групп", e);
        }
        return result;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM groups WHERE id = ?";
        try (Connection conn = cm.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении группы", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) AS cnt FROM groups WHERE id = ?";
        try (Connection conn = cm.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("cnt") > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке существования группы", e);
        }
    }

    private Group mapRow(ResultSet rs) throws SQLException {
        return new Group(rs.getLong("id"), rs.getString("name"), rs.getInt("number"));
    }
}
