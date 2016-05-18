/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splinesalgo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.factories.StringFactory;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.operators.StringCrossover;
import org.uncommons.watchmaker.framework.operators.StringMutation;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author Alfr3
 */
public class PointsEx extends JFrame {

    Surface surface;
    JPanel holder;
    JPanel buttons;

    public PointsEx() {
        initUI();
    }

    private void initUI() {
        /*final JFrame frame = new JFrame(PointsEx.class.getSimpleName());*/
        //frame.

        //frame.
        holder = new JPanel();
        //holder.setLayout(new BoxLayout(holder, BoxLayout.PAGE_AXIS));
        holder.setLayout(new BorderLayout(50, 50));

        JButton calc = new JButton("Calcular");
        calc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcBezier();
            }
        });
        buttons = new JPanel();

        buttons.add(calc);
        buttons.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        buttons.setSize(new Dimension(100, 50));
        holder.add(buttons);

        surface = new Surface();
        //surface.setPreferredSize(new Dimension(100, 100));
        //add(surface);
        holder.add(surface);
        add(holder);
        setTitle("Points");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        surface.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {

            }

            public void mouseEntered(MouseEvent evt) {

            }

            public void mouseExited(MouseEvent evt) {

            }

            public void mousePressed(MouseEvent evt) {
                addPoint(evt);
            }

            public void mouseReleased(MouseEvent evt) {

            }
        });
    }

    public void calcBezier() {

        ArrayList<Integer> data = surface.generateBezier();
        int ax = data.get(0);
        int ay = data.get(1);
        int bx = data.get(2);
        int by = data.get(3);
        int cx = data.get(4);
        int cy = data.get(5);

        ArrayList<Integer> newData = evolvepoints(ax, ay, bx, by, cx, cy, 50);


        System.out.println(newData);

        surface.drawBezier(newData.get(0), newData.get(1), newData.get(2), newData.get(3), newData.get(4), newData.get(5));
        holder.repaint();
    }

    public void addPoint(MouseEvent e) {
        surface.setNextPoint(new Point(e.getX(), e.getY()));
        holder.repaint();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PointsEx ex = new PointsEx();
                ex.setVisible(true);
            }
        });

    }

    private ArrayList<Integer> evolvepoints(int ax, int ay, int bx, int by, int cx, int cy, int number_evolve) {

        ArrayList<Integer> candidates_ax = new ArrayList<>();
        ArrayList<Integer> candidates_ay = new ArrayList<>();
        ArrayList<Integer> candidates_bx = new ArrayList<>();
        ArrayList<Integer> candidates_by = new ArrayList<>();
        ArrayList<Integer> candidates_cx = new ArrayList<>();
        ArrayList<Integer> candidates_cy = new ArrayList<>();

        int tmp_ax = ax;
        int tmp_ay = ay;
        int tmp_bx = bx;
        int tmp_by = by;
        int tmp_cx = cx;
        int tmp_cy = cy;

        if (ax < 0) ax = ax * (-1);
        if (ay < 0) ay = ay * (-1);
        if (bx < 0) bx = bx * (-1);
        if (by < 0) by = by * (-1);
        if (cx < 0) cx = cx * (-1);
        if (cy < 0) cy = cy * (-1);

        int new_ax = 0;
        int new_ay = 0;
        int new_bx = 0;
        int new_by = 0;
        int new_cx = 0;
        int new_cy = 0;

        Random rand = new Random();

        while (candidates_ax.size() != 50) {
            int tmp = rand.nextInt(2600 + 1);
            if (!candidates_ax.contains(tmp)) {
                candidates_ax.add(tmp);
            }
        }

        while (candidates_ay.size() != 50) {
            int tmp = rand.nextInt(2600 + 1);
            if (!candidates_ay.contains(tmp)) {
                candidates_ay.add(tmp);
            }
        }

        while (candidates_bx.size() != 50) {
            int tmp = rand.nextInt(2600 + 1);
            if (!candidates_bx.contains(tmp)) {
                candidates_bx.add(tmp);
            }
        }

        while (candidates_by.size() != 50) {
            int tmp = rand.nextInt(2600 + 1);
            if (!candidates_by.contains(tmp)) {
                candidates_by.add(tmp);
            }
        }

        while (candidates_cx.size() != 50) {
            int tmp = rand.nextInt(2600 + 1);
            if (!candidates_cx.contains(tmp)) {
                candidates_cx.add(tmp);
            }
        }

        while (candidates_cy.size() != 50) {
            int tmp = rand.nextInt(2600 + 1);
            if (!candidates_cy.contains(tmp)) {
                candidates_cy.add(tmp);
            }
        }
        
        int count = 0;
        while (count < number_evolve) {
            /*
            Map<Integer, Integer> fitness_ax = new HashMap<>();
            Map<Integer, Integer> fitness_ay = new HashMap<>();
            Map<Integer, Integer> fitness_bx = new HashMap<>();
            Map<Integer, Integer> fitness_by = new HashMap<>();
            Map<Integer, Integer> fitness_cx = new HashMap<>();
            Map<Integer, Integer> fitness_cy = new HashMap<>();

            LinkedHashMap<Integer, String> fitness_ax_binary = new LinkedHashMap<>();
            LinkedHashMap<Integer, String> fitness_ay_binary = new LinkedHashMap<>();
            LinkedHashMap<Integer, String> fitness_bx_binary = new LinkedHashMap<>();
            LinkedHashMap<Integer, String> fitness_by_binary = new LinkedHashMap<>();
            LinkedHashMap<Integer, String> fitness_cx_binary = new LinkedHashMap<>();
            LinkedHashMap<Integer, String> fitness_cy_binary = new LinkedHashMap<>();
            */
            
            
            ArrayList<Integer> tournament_ax = new ArrayList<>();
            ArrayList<Integer> tournament_ay = new ArrayList<>();
            ArrayList<Integer> tournament_bx = new ArrayList<>();
            ArrayList<Integer> tournament_by = new ArrayList<>();
            ArrayList<Integer> tournament_cx = new ArrayList<>();
            ArrayList<Integer> tournament_cy = new ArrayList<>();

            while (tournament_ax.size() != 25) {
                int candidate_1 = candidates_ax.remove(rand.nextInt((candidates_ax.size() - 1 - 0) + 1) + 0);
                int candidate_2 = candidates_ax.remove(rand.nextInt((candidates_ax.size() - 1 - 0) + 1) + 0);

                if ((int) Math.pow((ax - candidate_1), 2) <= (int) Math.pow((ax - candidate_2), 2)) {
                    tournament_ax.add(candidate_1);

                } else {
                    tournament_ax.add(candidate_2);
                }
            }

            while (tournament_ay.size() < 25) {
                int candidate_1 = candidates_ay.remove(rand.nextInt((candidates_ay.size() - 1) + 1));
                int candidate_2 = candidates_ay.remove(rand.nextInt((candidates_ay.size() - 1) + 1));
                if ((int) Math.pow((ay - candidate_1), 2) <= (int) Math.pow((ay - candidate_2), 2)) {
                    tournament_ay.add(candidate_1);
                } else {
                    tournament_ay.add(candidate_2);
                }

            }

            while (tournament_bx.size() < 25) {
                int candidate_1 = candidates_bx.remove(rand.nextInt((candidates_bx.size() - 1) + 1));
                int candidate_2 = candidates_bx.remove(rand.nextInt((candidates_bx.size() - 1) + 1));
                if ((int) Math.pow((bx - candidate_1), 2) <= (int) Math.pow((bx - candidate_2), 2)) {
                    tournament_bx.add(candidate_1);
                } else {
                    tournament_bx.add(candidate_2);
                }
            }

            while (tournament_by.size() < 25) {
                int ran_ind_1 = rand.nextInt((candidates_by.size() - 2) + 1);
                int ran_ind_2 = rand.nextInt((candidates_by.size() - 2) + 1);
                int candidate_1 = candidates_by.remove(ran_ind_1);
                int candidate_2 = candidates_by.remove(ran_ind_2);
                
                if ((int) Math.pow((by - candidate_1), 2) <= (int) Math.pow((by - candidate_2), 2)) {
                    tournament_by.add(candidate_1);
                } else {
                    tournament_by.add(candidate_2);
                }
            }

            while (tournament_cx.size() < 25) {

                int candidate_1 = candidates_cx.remove(rand.nextInt((candidates_cx.size() - 1) + 1));
                int candidate_2 = candidates_cx.remove(rand.nextInt((candidates_cx.size() - 1) + 1));
                if ((int) Math.pow((cx - candidate_1), 2) <= (int) Math.pow((cx - candidate_2), 2)) {
                    tournament_cx.add(candidate_1);
                } else {
                    tournament_cx.add(candidate_2);
                }
            }

            while (tournament_cy.size() < 25) {
                int candidate_1 = candidates_cy.remove(rand.nextInt((candidates_cy.size() - 1) + 1));
                int candidate_2 = candidates_cy.remove(rand.nextInt((candidates_cy.size() - 1) + 1));
                if ((int) Math.pow((cy - candidate_1), 2) <= (int) Math.pow((cy - candidate_2), 2)) {
                    tournament_cy.add(candidate_1);
                    candidates_cy.add(candidate_2);
                } else {
                    tournament_cy.add(candidate_2);
                    candidates_cy.add(candidate_1);
                }
            }
            
            candidates_ax = tournament_ax;
            candidates_ay = tournament_ay;
            candidates_bx = tournament_bx;
            candidates_by = tournament_by;
            candidates_cx = tournament_cx;
            candidates_cy = tournament_cy;
            
            
            ArrayList<Integer> fitness_ax = new ArrayList<>();
            ArrayList<Integer> fitness_ay = new ArrayList<>();
            ArrayList<Integer> fitness_bx = new ArrayList<>();
            ArrayList<Integer> fitness_by = new ArrayList<>();
            ArrayList<Integer> fitness_cx = new ArrayList<>();
            ArrayList<Integer> fitness_cy = new ArrayList<>();

            ArrayList<String> fitness_ax_binary = new ArrayList<>();
            ArrayList<String> fitness_ay_binary = new ArrayList<>();
            ArrayList<String> fitness_bx_binary = new ArrayList<>();
            ArrayList<String> fitness_by_binary = new ArrayList<>();
            ArrayList<String> fitness_cx_binary = new ArrayList<>();
            ArrayList<String> fitness_cy_binary = new ArrayList<>();
            
            for (int i = 0; i < candidates_ax.size() - 1; i++) {
                fitness_ax.add((int) Math.pow((ax - candidates_ax.get(i)), 2));
            }
            for (int i = 0; i < candidates_ay.size() - 1; i++) {
                fitness_ay.add((int) Math.pow((ay - candidates_ay.get(i)), 2));
            }
            for (int i = 0; i < candidates_bx.size() - 1; i++) {
                fitness_bx.add( (int) Math.pow((bx - candidates_bx.get(i)), 2));
            }
            for (int i = 0; i < candidates_by.size() - 1; i++) {
                fitness_by.add((int) Math.pow((by - candidates_by.get(i)), 2));
            }
            for (int i = 0; i < candidates_cx.size() - 1; i++) {
                fitness_cx.add((int) Math.pow((cx - candidates_cx.get(i)), 2));
            }
            for (int i = 0; i < candidates_cy.size() - 1; i++) {
                fitness_cy.add( (int) Math.pow((cy - candidates_cy.get(i)), 2));
            }
            
            for (Integer i : candidates_ax) {
                String binary_candidate_ax = Integer.toBinaryString(i);

                String tmp = "";
                if (binary_candidate_ax.length() != 12) {
                    for (int j = 0; j < 12 - binary_candidate_ax.length(); j++) {
                        tmp += "0";
                    }
                    binary_candidate_ax = tmp + binary_candidate_ax;
                }
                fitness_ax_binary.add(binary_candidate_ax);
            }

            for (Integer i : candidates_ay) {
                String binary_candidate_ay = Integer.toBinaryString(i);

                String tmp = "";
                if (binary_candidate_ay.length() != 12) {
                    for (int j = 0; j < 12 - binary_candidate_ay.length(); j++) {
                        tmp += "0";
                    }
                    binary_candidate_ay = tmp + binary_candidate_ay;
                }

                fitness_ay_binary.add(binary_candidate_ay);
            }

            for (Integer i : candidates_bx) {
                String binary_candidate_bx = Integer.toBinaryString(i);

                String tmp = "";
                if (binary_candidate_bx.length() != 12) {
                    for (int j = 0; j < 12 - binary_candidate_bx.length(); j++) {
                        tmp += "0";
                    }
                    binary_candidate_bx = tmp + binary_candidate_bx;
                }
                fitness_bx_binary.add(binary_candidate_bx);
            }

            for (Integer i : candidates_by) {
                String binary_candidate_by = Integer.toBinaryString(i);
                
                String tmp = "";
                if (binary_candidate_by.length() != 12) {
                    for (int j = 0; j < 12 - binary_candidate_by.length(); j++) {
                        tmp += "0";
                    }
                    binary_candidate_by = tmp + binary_candidate_by;
                }
                fitness_by_binary.add(binary_candidate_by);
            }

            for (Integer i : candidates_cx) {
                String binary_candidate_cx = Integer.toBinaryString(i);

                String tmp = "";
                if (binary_candidate_cx.length() != 12) {
                    for (int j = 0; j < 12 - binary_candidate_cx.length(); j++) {
                        tmp += "0";
                    }
                    binary_candidate_cx = tmp + binary_candidate_cx;
                }
                fitness_cx_binary.add(binary_candidate_cx);
            }

            for (Integer i : candidates_cy) {
                String binary_candidate_cy = Integer.toBinaryString(i);

                String tmp = "";
                if (binary_candidate_cy.length() != 12) {
                    for (int j = 0; j < 12 - binary_candidate_cy.length(); j++) {
                        tmp += "0";
                    }
                    binary_candidate_cy = tmp + binary_candidate_cy;
                }
                fitness_cy_binary.add(binary_candidate_cy);
            }

            //Elitism
            
            String elitism_value_ax = "";
            String elitism_value_ay = "";
            String elitism_value_bx = "";
            String elitism_value_by = "";
            String elitism_value_cx = "";
            String elitism_value_cy = "";
            

            
            int minIndex = fitness_ax.indexOf(Collections.min(fitness_ax));
            elitism_value_ax = fitness_ax_binary.get(minIndex);
            
            minIndex = fitness_ay.indexOf(Collections.min(fitness_ay));
            elitism_value_ay = fitness_ay_binary.get(minIndex);
            
            minIndex = fitness_bx.indexOf(Collections.min(fitness_bx));
            elitism_value_bx = fitness_bx_binary.get(minIndex);
            
            minIndex = fitness_by.indexOf(Collections.min(fitness_by));
            elitism_value_by = fitness_by_binary.get(minIndex);
            
            minIndex = fitness_cx.indexOf(Collections.min(fitness_cx));
            elitism_value_cx = fitness_cx_binary.get(minIndex);
            
            minIndex = fitness_cy.indexOf(Collections.min(fitness_cy));
            elitism_value_cy = fitness_cy_binary.get(minIndex);


            for (String i : fitness_ax_binary) {
                String new_candidate_1 = "";
                String new_candidate_2 = "";
                //Crossov
                int mit_1 = rand.nextInt(6);
                int mit_2 = rand.nextInt((12 - 6) + 1) + 6;
                
                
                new_candidate_1 = elitism_value_ax.substring(0, mit_1) + i.substring(mit_1, mit_2) + elitism_value_ax.substring(mit_2, 12);
                new_candidate_2 = i.substring(0, mit_1) + elitism_value_ax.substring(mit_1, mit_2) + i.substring(mit_2, 12);
                
                
                // Mutacion
                if (Math.random() < 0.2) {
                    char[] myNameChars_1 = new_candidate_1.toCharArray();
                    char[] myNameChars_2 = new_candidate_2.toCharArray();
                    if (myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] == '1') {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '1';
                    }
                    if (myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] == '1') {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '1';
                    }
                    new_candidate_1 = String.valueOf(myNameChars_1);
                    new_candidate_2 = String.valueOf(myNameChars_2);
                }
                candidates_ax.add(Integer.parseInt(new_candidate_1, 2));
                candidates_ax.add(Integer.parseInt(new_candidate_2, 2));
            }

            for (String i : fitness_ay_binary) {
                String new_candidate_1 = "";
                String new_candidate_2 = "";
                //Crossover
                int mit_1 = rand.nextInt(6);
                int mit_2 = rand.nextInt((12 - 6) + 1) + 6;
                
                
                new_candidate_1 = elitism_value_ay.substring(0, mit_1) + i.substring(mit_1, mit_2) + elitism_value_ay.substring(mit_2, 12);
                new_candidate_2 = i.substring(0, mit_1) + elitism_value_ay.substring(mit_1, mit_2) + i.substring(mit_2, 12);
                
                
                // Mutacion
                if (Math.random() < 0.2) {
                    char[] myNameChars_1 = new_candidate_1.toCharArray();
                    char[] myNameChars_2 = new_candidate_2.toCharArray();
                    if (myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] == '1') {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '1';
                    }
                    if (myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] == '1') {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '1';
                    }
                    new_candidate_1 = String.valueOf(myNameChars_1);
                    new_candidate_2 = String.valueOf(myNameChars_2);
                }
                candidates_ay.add(Integer.parseInt(new_candidate_1, 2));
                candidates_ay.add(Integer.parseInt(new_candidate_2, 2));
            }

            for (String i : fitness_bx_binary) {
                String new_candidate_1 = "";
                String new_candidate_2 = "";
                //Crossover
                int mit_1 = rand.nextInt(6);
                int mit_2 = rand.nextInt((12 - 6) + 1) + 6;
                
                
                new_candidate_1 = elitism_value_bx.substring(0, mit_1) + i.substring(mit_1, mit_2) + elitism_value_bx.substring(mit_2, 12);
                new_candidate_2 = i.substring(0, mit_1) + elitism_value_bx.substring(mit_1, mit_2) + i.substring(mit_2, 12);
                
                // Mutacion
                if (Math.random() < 0.2) {
                    char[] myNameChars_1 = new_candidate_1.toCharArray();
                    char[] myNameChars_2 = new_candidate_2.toCharArray();
                    if (myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] == '1') {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '1';
                    }
                    if (myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] == '1') {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '1';
                    }
                    new_candidate_1 = String.valueOf(myNameChars_1);
                    new_candidate_2 = String.valueOf(myNameChars_2);
                }
                candidates_bx.add(Integer.parseInt(new_candidate_1, 2));
                candidates_bx.add(Integer.parseInt(new_candidate_2, 2));
            }

            for (String i : fitness_by_binary) {
                String new_candidate_1 = "";
                String new_candidate_2 = "";
                //Crossover
                int mit_1 = rand.nextInt(6);
                int mit_2 = rand.nextInt((12 - 6) + 1) + 6;
                
                
                new_candidate_1 = elitism_value_by.substring(0, mit_1) + i.substring(mit_1, mit_2) + elitism_value_by.substring(mit_2, 12);
                new_candidate_2 = i.substring(0, mit_1) + elitism_value_by.substring(mit_1, mit_2) + i.substring(mit_2, 12);
                
                
                // Mutacion
                if (Math.random() < 0.2) {
                    char[] myNameChars_1 = new_candidate_1.toCharArray();
                    char[] myNameChars_2 = new_candidate_2.toCharArray();
                    if (myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] == '1') {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '1';
                    }
                    if (myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] == '1') {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '1';
                    }
                    new_candidate_1 = String.valueOf(myNameChars_1);
                    new_candidate_2 = String.valueOf(myNameChars_2);
                }
                candidates_by.add(Integer.parseInt(new_candidate_1, 2));
                candidates_by.add(Integer.parseInt(new_candidate_2, 2));
            }

            for (String i : fitness_cx_binary) {
                String new_candidate_1 = "";
                String new_candidate_2 = "";
                //Crossover
                int mit_1 = rand.nextInt(6);
                int mit_2 = rand.nextInt((12 - 6) + 1) + 6;
                
                
                new_candidate_1 = elitism_value_cx.substring(0, mit_1) + i.substring(mit_1, mit_2) + elitism_value_cx.substring(mit_2, 12);
                new_candidate_2 = i.substring(0, mit_1) + elitism_value_cx.substring(mit_1, mit_2) + i.substring(mit_2, 12);
                
                // Mutacion
                if (Math.random() < 0.2) {
                    char[] myNameChars_1 = new_candidate_1.toCharArray();
                    char[] myNameChars_2 = new_candidate_2.toCharArray();
                    if (myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] == '1') {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '1';
                    }
                    if (myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] == '1') {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '1';
                    }
                    new_candidate_1 = String.valueOf(myNameChars_1);
                    new_candidate_2 = String.valueOf(myNameChars_2);
                }
                candidates_cx.add(Integer.parseInt(new_candidate_1, 2));
                candidates_cx.add(Integer.parseInt(new_candidate_2, 2));
            }

            for (String i : fitness_cy_binary) {
                String new_candidate_1 = "";
                String new_candidate_2 = "";
                //Crossover
                int mit_1 = rand.nextInt(6);
                int mit_2 = rand.nextInt((12 - 6) + 1) + 6;
                
                
                new_candidate_1 = elitism_value_cy.substring(0, mit_1) + i.substring(mit_1, mit_2) + elitism_value_cy.substring(mit_2, 12);
                new_candidate_2 = i.substring(0, mit_1) + elitism_value_cy.substring(mit_1, mit_2) + i.substring(mit_2, 12);
                // Mutacion
                if (Math.random() < 0.2) {
                    char[] myNameChars_1 = new_candidate_1.toCharArray();
                    char[] myNameChars_2 = new_candidate_2.toCharArray();
                    if (myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] == '1') {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_1[rand.nextInt((new_candidate_1.length() - 1) + 1) + 0] = '1';
                    }
                    if (myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] == '1') {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '0';
                    } else {
                        myNameChars_2[rand.nextInt((new_candidate_2.length() - 1) + 1) + 0] = '1';
                    }
                    new_candidate_1 = String.valueOf(myNameChars_1);
                    new_candidate_2 = String.valueOf(myNameChars_2);
                }
                candidates_cy.add(Integer.parseInt(new_candidate_1, 2));
                candidates_cy.add(Integer.parseInt(new_candidate_2, 2));
            }
            count++;
            
        }

        
        int min = (int) Math.pow((double) (ax - candidates_ax.get(0)), 2);
        for (int i = 0; i <= candidates_ax.size() - 1; i++) {
            int tmp = (int) Math.pow((double) (ax - candidates_ax.get(i)), 2);
            if (tmp <= min) {
                new_ax = candidates_ax.get(i);
                min = tmp;
            }
        }

        min = (int) Math.pow((double) (ay - candidates_ay.get(0)), 2);
        for (int i = 0; i <= candidates_ay.size() - 1; i++) {
            int tmp = (int) Math.pow((double) (ay - candidates_ay.get(i)), 2);
            if (tmp <= min) {
                new_ay = candidates_ay.get(i);
                min = tmp;
            }
        }

        min = (int) Math.pow((double) (bx - candidates_bx.get(0)), 2);
        for (int i = 0; i <= candidates_bx.size() - 1; i++) {
            int tmp = (int) Math.pow((double) (bx - candidates_bx.get(i)), 2);
            if (tmp <= min) {
                new_bx = candidates_bx.get(i);
                min = tmp;
            }
        }

        min = (int) Math.pow((double) (by - candidates_by.get(0)), 2);
        for (int i = 0; i <= candidates_by.size() - 1; i++) {
            int tmp = (int) Math.pow((double) (by - candidates_by.get(i)), 2);
            if (tmp <= min) {
                new_by = candidates_by.get(i);
                min = tmp;
            }
        }
        
        min = (int) Math.pow((double) (cx - candidates_cx.get(0)), 2);
        for (int i = 0; i <= candidates_cx.size() - 1; i++) {
            int tmp = (int) Math.pow((double) (cx - candidates_cx.get(i)), 2);
            if (tmp <= min) {
                new_cx = candidates_cx.get(i);
                min = tmp;  
            }
        }

        min = (int) Math.pow((double) (cy - candidates_cy.get(0)), 2);
        for (int i = 0; i < candidates_cy.size() - 1; i++) {
            int tmp = (int) Math.pow((double) (cy - candidates_cy.get(i)), 2);
            if (tmp <= min) {
                new_cy = candidates_cy.get(i);
                min = tmp;
            }
        }
        
        if (tmp_ax < 0) new_ax = new_ax * (-1);
        if (tmp_ay < 0) new_ay = new_ay * (-1);
        if (tmp_bx < 0) new_bx = new_bx * (-1);
        if (tmp_by < 0) new_by = new_by * (-1);
        if (tmp_cx < 0) new_cx = new_cx * (-1);
        if (tmp_cy < 0) new_cy = new_cy * (-1);

        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(new_ax);
        result.add(new_ay);
        result.add(new_bx);
        result.add(new_by);
        result.add(new_cx);
        result.add(new_cy);
        return result;
    }
}
