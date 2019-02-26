package com.mycompany.mathcamp;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class RandomNumber {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int randNum;
        Random rand = new Random(123);
        Object[] x2Array, x4Array;
        
        int x2Size = 52, x2Low = 21, x2High = 280;
        int x4Size = 13, x4Low = 41, x4High = 260;
        
        Set<Integer> x2Set = new HashSet<>(), x4Set = new HashSet<>();
        
        while(x2Set.size() != x2Size){ 
            // return a random number in the range of [1, max]
            randNum = rand.nextInt(x2High - x2Low) + x2Low;
            if(!x2Set.contains(randNum) && !x2Set.contains(randNum-1) && !x2Set.contains(randNum+1))
                x2Set.add(randNum);
        }
        x2Array = x2Set.toArray();
        Arrays.sort(x2Array);

        for(int j = 0; j < x2Array.length; j++) {
            System.out.print(String.valueOf(x2Array[j] + " "));
            if((j+1) % 10 == 0) {
                System.out.println();
            }
        }
        System.out.print("\n\n{");
        for(int j = 0; j < x2Array.length; j++) {
            System.out.print(String.valueOf(x2Array[j]));
            if(j+1 != x2Array.length) 
                System.out.print(", ");
        }
        System.out.println("}\n");
        
        while(x4Set.size() != x4Size){ 
            // return a random number in the range of [1, max]
            randNum = rand.nextInt(x4High - x4Low) + x4Low;
            if(!x4Set.contains(randNum) && !x2Set.contains(randNum) && !x2Set.contains(randNum-1) && !x2Set.contains(randNum+1))
                x4Set.add(randNum);
        }
        x4Array = x4Set.toArray();
        Arrays.sort(x4Array);

        for(int j = 0; j < x4Array.length; j++) {
            System.out.print(String.valueOf(x4Array[j] + " "));
            if((j+1) % 10 == 0) {
                System.out.println();
            }
        }
        System.out.print("\n\n{");
        for(int j = 0; j < x4Array.length; j++) {
            System.out.print(String.valueOf(x4Array[j]));
            if(j+1 != x4Array.length) 
                System.out.print(", ");
        }
        System.out.println("}\n");
    }
}