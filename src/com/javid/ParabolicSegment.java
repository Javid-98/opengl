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
public class ParabolicSegment {

    public static void main(String[] args) {
        StdDraw.setPenRadius(0.02);
        drawP(new Point(0, 0), new Point(0.5, 1), new Point(1, 0));
    }

    public static void drawP(Point a, Point b, Point c) {
        if (equals(a.getX(), b.getX()) && equals(a.getY(), b.getY()) && equals(b.getX(), c.getX()) && equals(b.getY(), c.getY())) {
            return;
        }
        Point m = midPoint(a, b);
        Point m2 = midPoint(b, c);
        Point m3 = midPoint(m, m2);

        drawP(a, m, m3);
        drawP(m3, m2, c);
        double mid_x = (m.getX() + m2.getX()) / 2;
        double mid_y = (m.getY() + m2.getY()) / 2;
        StdDraw.point(mid_x, mid_y);
    }

    public static boolean equals(double d1, double d2) {
        return Math.abs(d2 - d1) < 0.001;
    }

    private static Point midPoint(Point a, Point b) {
        double x = (a.getX() + b.getX()) / 2;
        double y = (a.getY() + b.getY()) / 2;
        return new Point(x, y);
    }
}
