/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

/**
 *
 * @author asus
 */
public class ParametricQuad {

    public static void main(String[] args) {
        StdDraw.setPenRadius(0.02);
        draw(new Point(0, 0), new Point(0.5, 1), new Point(1, 0), 0.001);
    }

    public static void draw(Point a, Point b, Point c, double step) {

        for (double i = 0.0; i <= 1.0; i += step) {
            double x = Math.pow((1 - i), 2) * a.getX() + 2 * i * (1 - i) * b.getX() + Math.pow((i), 2) * c.getX();
            double y = Math.pow((1 - i), 2) * a.getY() + 2 * i * (1 - i) * b.getY() + Math.pow((i), 2) * c.getY();
            StdDraw.point(x, y);
        }
    }

}
