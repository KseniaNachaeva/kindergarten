package main.java.kindergarten.model;

import java.util.Objects;

public class Child {
    private Long id;
    private String fullName;
    private boolean male;
    private int age;
    private Long groupId;

    public Child() {
    }

    public Child(String fullName, boolean male, int age) {
        this.fullName = fullName;
        this.male = male;
        this.age = age;
    }

    public Child(Long id, String fullName, boolean male, int age) {
        this.id = id;
        this.fullName = fullName;
        this.male = male;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return male == child.male && age == child.age && Objects.equals(id, child.id)
                && Objects.equals(fullName, child.fullName) && Objects.equals(groupId, child.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, male, age, groupId);
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", male=" + male +
                ", age=" + age +
                ", groupId=" + groupId +
                '}';
    }
}
