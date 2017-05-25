package lambda.stream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by tl on 5/18/17.
 * note
 * 1. avoid mechanism.loop (foreach), use reductions
 */

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

public class Main {

    public static void main(String[] args) {
        // split strings
        Stream.of("user1,pw1", "user2,pw2", "user3,pw3") // a stream of strings
                .map(tpl -> tpl.split(",")) // map each string to array of size 2; a stream of tuples of size 2
                .forEach(tpl -> System.out.printf("username: %s, password: %s\n", tpl[0], tpl[1]));

        // join strings
        System.out.println(Stream.of("peter", "john", "mary")
                .collect(Collectors.joining(", ")));

        // sort
        List<Person> people = Arrays.asList(
                new Person("peter", 40),
                new Person("john", 20),
                new Person("mary", 20),
                new Person("mary", 10));

        people.stream()
                .sorted(Comparator.comparing(Person::getName).thenComparing(Person::getAge))
                .forEach(System.out::println);

        // group [key: Person::getName, value: List<Person>]
        Map<String, List<Person>> collect = people.stream()
                .collect(Collectors.groupingBy(Person::getName));
        System.out.println(collect);

        // group [key: Person::getName, value: List<Person> map into Person::getAge]
        Map<String, List<Integer>> listMap = people.stream()
                .collect(Collectors.groupingBy(Person::getName, // group by name
                        Collectors.mapping(Person::getAge, Collectors.toList())));// map each person into string of Person::getName
        System.out.println(listMap);
    }

}
