package com.kuaikan.log4j2demo;

public class Test {
    public static void main(String[] arg) {
        ClassLoader c = Test.class.getClassLoader();
        System.out.println(c);
        ClassLoader c1 = c.getParent();
        System.out.println(c1);
        ClassLoader c2 = c1.getParent();
        System.out.println(c2);
    }
}
