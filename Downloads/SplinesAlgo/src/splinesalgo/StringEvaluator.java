/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splinesalgo;

import java.util.List;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

/**
 *
 * @author Alfr3
 */
public class StringEvaluator implements FitnessEvaluator<String>
{
    private final String targetString = "HELLO WORLD";

    /**
     * Assigns one "fitness point" for every character in the
     * candidate String that matches the corresponding position in
     * the target string.
     */
    public double getFitness(String candidate,
                             List<? extends String> population)
    {
        int matches = 0;
        for (int i = 0; i < candidate.length(); i++)
        {
            if (candidate.charAt(i) == targetString.charAt(i))
            {
                ++matches;
            }
        }
        return matches;
    }

    public boolean isNatural()
    {
        //true -> maximizacion
        //false -> minimizacion
        return true;
    }
    
}
