package com.employee.system.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Student {
    private String name;
    private int gradeLevel;

    public Student(String name, int gradeLevel) {
        this.name = name;
        this.gradeLevel = gradeLevel;
    }

    public String getName() {
        return name;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gradeLevel=" + gradeLevel +
                '}';
    }

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Alice", 9),
                new Student("Bob", 10),
                new Student("Charlie", 9),
                new Student("David", 10),
                new Student("Eve", 11)
        );

        Map<Integer, List<Student>> collect =
                students.stream().collect(Collectors.groupingBy(Student::getGradeLevel));

    }
}
