/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

import static com.javid.Line.drawLine;
import static com.javid.ParabolicSegment.drawP;

/**
 *
 * @author asus
 */
public class ParametricCubic {

    public static void main(String[] args) {
        StdDraw.setPenRadius(0.02);
        draw(new Point(0.4, 0.2), new Point(0, 0.5), new Point(0.3, 0.8), new Point(0.4, 0.5), 0.001);
        draw(new Point(0.4, 0.2), new Point(0.8, 0.5), new Point(0.5, 0.8), new Point(0.4, 0.5), 0.001);
        drawLine(new Point(0.7, 0.65), new Point(0.4, 0.395));
        drawP(new Point(0.45, 0.39), new Point(0.3, 0.3), new Point(0.4, 0.45));
    }

    public static void draw(Point a, Point b, Point c, Point z, double step) {

        for (double i = 0.0; i <= 1.0; i += step) {
            double x = Math.pow((1 - i), 3) * a.getX() + 3 * i * Math.pow((1 - i), 2) * b.getX() + 3 * Math.pow((i), 2) * (1 - i) * c.getX() + Math.pow(i, 3) * z.getX();
            double y = Math.pow((1 - i), 3) * a.getY() + 3 * i * Math.pow((1 - i), 2) * b.getY() + 3 * Math.pow((i), 2) * (1 - i) * c.getY() + Math.pow(i, 3) * z.getY();
            StdDraw.point(x, y);
        }
    }

}
