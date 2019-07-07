package com.khtm.circuitbreaker;

import org.junit.Test;

import java.util.Random;

public class FeignTest {

    @Test
    public void generateRandom(){
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int num = random.nextInt(5);
            System.out.println(num);
        }
    }

}
