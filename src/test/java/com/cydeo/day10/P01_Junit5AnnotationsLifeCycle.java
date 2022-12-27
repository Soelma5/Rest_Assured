package com.cydeo.day10;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class P01_Junit5AnnotationsLifeCycle {
    @BeforeAll
    public static void init() {
        System.out.println("BeforeAll is running");
    }

    @Test
    public void test1(){
        System.out.println("Test1 is running");
    }

    @Test
    public void test2(){
        System.out.println("Test2 is running");
    }

    @AfterAll
    public static void destroy() {
        System.out.println("AfterAll is running");
    }
}
