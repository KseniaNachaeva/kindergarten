package main.java.kindergarten.repository.jdbc;

import main.java.kindergarten.model.Child;
import main.java.kindergarten.repository.ChildRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcChildRepository implements ChildRepository {

    private final ConnectionManager cm;

    public JdbcChildRepository(ConnectionManager cm) {
        this.cm = cm;
    }

    @Override
    public Child save(Child child) {
        if (child.getId() == null) {
            String sql = "INSERT INTO children (full_name, male, age, group_id) VALUES (?, ?, ?, ?) RETURNING id";
            try (Connection conn = cm.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, child.getFullName());
                stmt.setBoolean(2, child.isMale());
                stmt.setInt(3, child.getAge());
                if (child.getGroupId() != null) {
                    stmt.setLong(4, child.getGroupId());
                } else {
                    stmt.setNull(4, Types.BIGINT);
                }
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    child.setId(rs.getLong("id"));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при сохранении ребёнка", e);
            }
        } else {
            String sql = "UPDATE children SET full_name = ?, male = ?, age = ?, group_id = ? WHERE id = ?";
            try (Connection conn = cm.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, child.getFullName());
                stmt.setBoolean(2, child.isMale());
                stmt.setInt(3, child.getAge());
                if (child.getGroupId() != null) {
                    stmt.setLong(4, child.getGroupId());
                } else {
                    stmt.setNull(4, Types.BIGINT);
                }
                stmt.setLong(5, child.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при обновлении ребёнка", e);
            }
        }
        return child;
    }

    @Override
    public Optional<Child> findById(Long id) {
        String sql = "SELECT id, full_name, male, age, group_id FROM children WHERE id = ?";
        try (Connection conn = cm.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске ребёнка", e);
        }
    }

    @Override
    public List<Child> findAll() {
        String sql = "SELECT id, full_name, male, age, group_id FROM children ORDER BY id";
        List<Child> result = new ArrayList<>();
        try (Connection conn = cm.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка детей", e);
        }
        return result;
    }

    @Override
    public List<Child> findByGroupId(Long groupId) {
        String sql = "SELECT id, full_name, male, age, group_id FROM children WHERE group_id = ? ORDER BY id";
        List<Child> result = new ArrayList<>();
        try (Connection conn = cm.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, groupId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске детей по группе", e);
        }
        return result;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM children WHERE id = ?";
        try (Connection conn = cm.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении ребёнка", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) AS cnt FROM children WHERE id = ?";
        try (Connection conn = cm.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("cnt") > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке существования ребёнка", e);
        }
    }

    private Child mapRow(ResultSet rs) throws SQLException {
        long groupIdVal = rs.getLong("group_id");
        Long groupId = rs.wasNull() ? null : groupIdVal;
        return new Child.Builder()
                .id(rs.getLong("id"))
                .fullName(rs.getString("full_name"))
                .male(rs.getBoolean("male"))
                .age(rs.getInt("age"))
                .groupId(groupId)
                .build();
    }
}
