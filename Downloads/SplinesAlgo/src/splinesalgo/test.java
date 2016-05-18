/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splinesalgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author japrietov
 */
public class test {
    
    public static void main(String args[]){
        System.out.println(Integer.toBinaryString(6760000));
        System.out.println(Integer.parseInt("111101001100001000000", 2));
        
        ArrayList<Integer> candidates_ay = new ArrayList<>();
         
        Random rand = new Random();
        while (candidates_ay.size() != 50) {
            int tmp = rand.nextInt(2600 + 1);
            if (!candidates_ay.contains(tmp)) {
                candidates_ay.add(tmp);
            }
        }
        
        System.out.println(candidates_ay);
        int minIndex = candidates_ay.indexOf(Collections.min(candidates_ay));
        System.out.println(minIndex);
    }
}
