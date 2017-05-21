package staticClass;

/**
 * Created by tl on 5/19/17.
 * an application of static class
 */
public class Person {

    private int id;
    private int age;
    private int salary;

    public static class Builder { // without static, this class can't be used with 'new'

//        required param
        private final int id;


//        optional param
        private int age = 0;
        private int salary = 0;

        public Builder(int id) {
            this.id = id;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setSalary(int salary) {
            this.salary = salary;
            return this;
        }

        public Person build() {
            return new Person(this);
        }

    }

    public Person(Builder builder) {
        this.id = builder.id;
        this.age = builder.age;
        this.salary = builder.salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    public static void main(String[] args) {
        Person person = new Builder(1).setAge(10).setSalary(10000).build();
        Person person1 = new Person.Builder(1).setAge(10).setSalary(10000).build();
        System.out.println(person);
        System.out.println(person1);
    }

}
