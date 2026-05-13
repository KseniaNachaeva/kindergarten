package main.java.kindergarten.model;

import java.util.Objects;

public class Group {

    private Long id;
    private String name;
    private int number;

    public Group() {
    }

    public Group(String name, int number) {
        setName(name);
        setNumber(number);
    }

    public Group(Long id, String name, int number) {
        this.id = id;
        setName(name);
        setNumber(number);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название группы не может быть пустым");
        }
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Номер группы должен быть положительным");
        }
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return number == group.number && Objects.equals(id, group.id) && Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number);
    }

    @Override
    public String toString() {
        return "Group{id=" + id + ", name='" + name + "', number=" + number + '}';
    }
}
