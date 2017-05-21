package mechanism.initBlock;

import java.util.ArrayList;

/**
 * Created by tl on 5/20/17.
 */
public class Main {

    private static int staticVar;
    private int instanceVar;

//    static initialization blocks
    static {
        staticVar = 1;
    }


//    instance initialization blocks
    {
        instanceVar = 2;
    }


    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("staticVar: " + Main.staticVar);
        System.out.println("instanceVar: " + main.instanceVar);
    }

}
