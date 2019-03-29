package ru.ifmo.rain.terentev.students;

import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentGroupQuery;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentGroupQuery {

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getInfo(students,
                Student::getFirstName,
                ArrayList::new);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getInfo(students,
                Student::getLastName,
                ArrayList::new);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return getInfo(students,
                Student::getGroup,
                ArrayList::new);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return getInfo(students,
                x -> x.getFirstName().concat(" " + x.getLastName()),
                ArrayList::new);
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return getInfo(students,
                Student::getFirstName,
                TreeSet::new);
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream()
                .min(Student::compareTo)
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return toSortedAndCollected(students.stream(),
                Student::compareTo,
                ArrayList::new);
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return toSortedAndCollected(students.stream(),
                compareNames,
                ArrayList::new);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return toFilteredAndSorted(students.stream(), (x -> x.getFirstName().equals(name)));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return toFilteredAndSorted(students.stream(), x -> x.getLastName().equals(name));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return toFilteredAndSorted(students.stream(), x -> x.getGroup().equals(group));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return students.stream()
                .filter(x -> x.getGroup().equals(group))
                .collect(Collectors.toMap(
                        Student::getLastName,
                        Student::getFirstName,
                        BinaryOperator.minBy(String::compareTo)));
    }

    private static final Comparator<Student> compareNames = Comparator.comparing(Student::getLastName, String::compareTo)
            .thenComparing(Student::getFirstName, String::compareTo)
            .thenComparingInt(Student::getId);

    //Group
    @Override
    public List<Group> getGroupsByName(Collection<Student> collection) {
        return sortGroups(collection, this::sortStudentsByName);
    }

    @Override
    public List<Group> getGroupsById(Collection<Student> collection) {
        return sortGroups(collection, this::sortStudentsById);
    }

    @Override
    public String getLargestGroup(Collection<Student> collection) {
        return getAnyLargestGroup(getGroupsStream(collection), List::size);
    }

    @Override
    public String getLargestGroupFirstName(Collection<Student> students) {
        return getAnyLargestGroup(getGroupsStream(students), studentsList -> getDistinctFirstNames(studentsList).size());
    }

    private <C extends Collection<String>> C getInfo(List<Student> students, Function<Student, String> supplier, Supplier<C> collection) {
        return students.stream()
                .map(supplier)
                .collect(Collectors.toCollection(collection));
    }

    private <C extends Collection<Student>> C toSortedAndCollected(Stream<Student> students, Comparator<Student> comp, Supplier<C> supplier) {
        return students.sorted(comp)
                .collect(Collectors.toCollection(supplier));
    }

    private List<Student> toFilteredAndSorted(Stream<Student> students, Predicate<Student> predicate) {
        return toSortedAndCollected(students.filter(predicate), compareNames, ArrayList::new);
    }

    private Stream<Map.Entry<String, List<Student>>> getGroupsStream(Collection<Student> students) {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, TreeMap::new, Collectors.toList()))
                .entrySet()
                .stream();
    }

    private List<Group> sortGroups(Collection<Student> collection, UnaryOperator<List<Student>> sorter) {
        return getGroupsStream(collection).map(x -> new Group(x.getKey(), sorter.apply(x.getValue())))
                .collect(Collectors.toList());
    }

    private String getAnyLargestGroup(Stream<Map.Entry<String, List<Student>>> stream, Function<List<Student>, Integer> filter) {
        return stream.max(Comparator.comparingInt((Map.Entry<String, List<Student>> group) -> filter.apply(group.getValue()))
                .thenComparing(Map.Entry::getKey, Collections.reverseOrder(String::compareTo)))
                .map(Map.Entry::getKey)
                .orElse("");
    }
}
