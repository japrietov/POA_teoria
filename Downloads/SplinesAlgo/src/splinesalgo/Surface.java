/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splinesalgo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.Random;

import javax.swing.*;

/**
 *
 * @author Alfr3
 */
public class Surface extends JPanel implements ActionListener {

    int nPuntos = 4;
    int actualPuntos = 0;
    int actualPunto;
    int radio = 20;
    public class PointD{
      double x,y;
      PointD(double xp, double yp){x = xp; y = yp;}
    };
    Point puntos[];

    boolean clicked;
    //Bezier
    private boolean drawBezier;
    private boolean drawEmpiric;
    public ArrayList<Point> realCurva;
    private PointD A, B, C;

    public Surface() {
        clicked = false;
        puntos = new Point[nPuntos];
        actualPunto = 0;
        drawBezier = false;
        drawEmpiric = false;
        realCurva = new ArrayList<>();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);

        int w = getWidth();
        int h = getHeight();
        g2d.setColor(Color.blue);
        for (int i = 0; i < actualPuntos; i++) {
            g2d.fillOval(puntos[i].x - (radio / 2), puntos[i].y - (radio / 2), radio, radio);
        }
        if (realCurva.size() > 0) {
            for (Point p : realCurva) {
                g2d.fillOval(p.x,
                        p.y,
                        2, 2);
            }

        }
        g2d.setPaint(Color.red);
        if (drawEmpiric) {
            int startx = puntos[0].x, starty = puntos[0].y;
            int endx = puntos[3].x, endy = puntos[3].y;
            double t, tSquared, tCubed;
            for (int i = 0; i < 100; i++) {
                t = (double) ((double) i / 100.0);
                tSquared = t * t;
                tCubed = tSquared * t;
                int dx = (int) ((int) (A.x * tCubed) + (B.x * tSquared) + (C.x * t));
                //(int) Math.round(( endx - startx)*((double)i/100));
                int dy = (int) ((int) (A.y * tCubed) + (B.y * tSquared) + (C.y * t));
                //(int) Math.round(( endy - starty)*((double)i/100));
                g2d.fillOval(startx + dx,
                        starty + dy, 2, 2);

            }
        }
    }

    public ArrayList<Integer> generateBezier() {
        realCurva = new ArrayList<>();
        int startx = puntos[0].x, starty = puntos[0].y;
        int endx = puntos[3].x, endy = puntos[3].y;
        double ax, bx, cx;
        double ay, by, cy;
        double t, tSquared, tCubed;

        cx = (double) (3.0 * (puntos[1].x - puntos[0].x));
        bx = (double) (3.0 * (puntos[2].x - puntos[1].x) - cx);
        ax = puntos[3].x - puntos[0].x - cx - bx;

        cy = (double) (3.0 * (puntos[1].y - puntos[0].y));
        by = (double) (3.0 * (puntos[2].y - puntos[1].y) - cy);
        ay = puntos[3].y - puntos[0].y - cy - by;
        
        
        System.out.println(ax+" , "+ay+";  "+bx+" , "+by+";  "+cx+" , "+cy);
        
        for (int i = 0; i < 100; i++) {
            t = (double) ((double) i / 100.0);
            tSquared = t * t;
            tCubed = tSquared * t;
            int dx = (int) ((int) (ax * tCubed) + (bx * tSquared) + (cx * t));
            //(int) Math.round(( endx - startx)*((double)i/100));
            int dy = (int) ((int) (ay * tCubed) + (by * tSquared) + (cy * t));
            //(int) Math.round(( endy - starty)*((double)i/100));
            realCurva.add(new Point(startx + dx,
                    starty + dy)
            );
        }
        ArrayList<Integer>data = new ArrayList<>();
        data.add((int)ax);
        data.add((int)ay);
        data.add((int)bx);
        data.add((int)by);
        data.add((int)cx);
        data.add((int)cy);
        
        return data;
    }

    public void setNextPoint(Point p) {
        puntos[actualPunto++] = p;
        actualPunto = actualPunto % nPuntos;
        if (actualPuntos < nPuntos) {
            actualPuntos++;
        }else{
            generateBezier();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();

    }

    void drawBezier(double axp, double ayp, double bxp, double byp, double cxp, double cyp) {
        
        if (actualPuntos == nPuntos) {
            A = new PointD(axp, ayp);
            B = new PointD(bxp, byp);
            C = new PointD(cxp, cyp);
            
            drawEmpiric = true;
            System.out.println("...........");
            repaint();
        }
    }

}
