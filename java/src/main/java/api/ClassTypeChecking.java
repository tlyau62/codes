package api;

/**
 * Created by tlyau on 6/3/17.
 * https://stackoverflow.com/questions/496928/what-is-the-difference-between-instanceof-and-class-isassignablefrom
 */
public class ClassTypeChecking {

    static class MyClass {}

    public static void main(String[] args) {

        MyClass myClass = new MyClass();

        System.out.println(myClass instanceof MyClass); // check type at compile time: true
        System.out.println(MyClass.class.isAssignableFrom(myClass.getClass())); // check type at runtime: true

    }

}
