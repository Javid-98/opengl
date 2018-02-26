package com.javid;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus
 */
public class Line {

    public static void main(String[] args) {
        StdDraw.setPenRadius(0.02);
        drawLine(new Point(0, 0), new Point(1, 1));
    }

    public static boolean equals(double d1, double d2) {
        return Math.abs(d2 - d1) < 0.001;
    }
 
    public static void drawLine(Point a, Point b) {
        if (equals(a.getX(), b.getX()) && equals(a.getY(), b.getY())) {
            return;
        }
        Point m = midPoint(a, b);
        double mid_x = (a.getX() + b.getX()) / 2;
        double mid_y = (a.getY() + b.getY()) / 2;
        drawLine(a, m);
        drawLine(m, b);
        StdDraw.point(mid_x, mid_y);
    }

    private static Point midPoint(Point a, Point b) {
        double x = (a.getX() + b.getX()) / 2;
        double y = (a.getY() + b.getY()) / 2;
        return new Point(x, y);
    }
}
