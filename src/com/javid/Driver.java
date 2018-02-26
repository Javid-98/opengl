/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

import java.util.*;

/**
 *
 * @author OwaryLtd
 */
public class Driver {

    static Point intersect(Point pStart, Point pEnd, Point qStart, Point qEnd) {
        double k = 0, m = 0, b = 0, e = 0, x = 0, y = 0;
        if (pEnd.getX() == pStart.getX() && qEnd.getX() == qStart.getX()) {
            return null;
        }
        if (pEnd.getX() == pStart.getX()) {
            // slope of q line
            m = (qEnd.getY() - qStart.getY()) / (qEnd.getX() - qStart.getX());
            // y incr of q line
            e = qStart.getY() - m * qStart.getX();
            // intersect x 
            x = pEnd.getX();
            // intersect y
            y = m * x + e;
        } else if (qEnd.getX() == qStart.getX()) {
            // slope of p line
            k = (pEnd.getY() - pStart.getY()) / (pEnd.getX() - pStart.getX());
            // y incr of p line
            b = pStart.getY() - k * pStart.getX();

            // intersect x 
            x = qStart.getX();
            // intersect y
            y = k * x + b;
        } else {
            // slope of p line
            k = (pEnd.getY() - pStart.getY()) / (pEnd.getX() - pStart.getX());
            // slope of q line
            m = (qEnd.getY() - qStart.getY()) / (qEnd.getX() - qStart.getX());

            // y incr of p line
            b = pStart.getY() - k * pStart.getX();
            // y incr of q line
            e = qStart.getY() - m * qStart.getX();

            if (m == k) {
                return null;
            }

            // intersect x 
            x = (b - e) / (m - k);
            // intersect y
            y = k * x + b;
        }
        Point res = null;

        if (isBetween(x, pStart.getX(), pEnd.getX())
                && isBetween(x, qStart.getX(), qEnd.getX())
                && isBetween(y, pStart.getY(), pEnd.getY())
                && isBetween(y, qStart.getY(), qEnd.getY())) {
            res = new Point(x, y);
        }

        return res;
    }

    private static boolean isBetween(double x, double prev, double next) {
        if (prev > next) {
            double temp = prev;
            prev = next;
            next = temp;
        }
        return x >= prev && x <= next;
    }

    static List<Point> polygonalIntersection(List<Point> polyP, List<Point> polyQ) {
        int sizeP = polyP.size();
        int sizeQ = polyQ.size();
        Point pStart, pEnd;
        Point qStart, qEnd;

        List<Point> newPoly = new LinkedList<>();
        for (int i = 0; i < sizeP; i++) {
            if (i == sizeP - 1) {
                pStart = polyP.get(i);
                pEnd = polyP.get(0);
            } else {
                pStart = polyP.get(i);
                pEnd = polyP.get(i + 1);
            }
            for (int j = 0; j < sizeQ; j++) {
                if (j == sizeQ - 1) {
                    qStart = polyQ.get(j);
                    qEnd = polyQ.get(0);
                } else {
                    qStart = polyQ.get(j);
                    qEnd = polyQ.get(j + 1);
                }

                Point p = intersect(pStart, pEnd, qStart, qEnd);
                if (p != null) {
                    newPoly.add(p);
                }
            }
        }
        return newPoly;
    }

    static List<Point> polygon(List<Point> polyP, List<Point> polyQ) {
        int sizeP = polyP.size();
        int sizeQ = polyQ.size();
        Point pStart, pEnd;
        Point qStart, qEnd;

        List<Point> newPoly = new LinkedList<>();
        for (int i = 0; i < sizeP; i++) {
            if (i == sizeP - 1) {
                pStart = polyP.get(i);
                pEnd = polyP.get(0);
            } else {
                pStart = polyP.get(i);
                pEnd = polyP.get(i + 1);
            }
            newPoly.add(pStart);
            for (int j = 0; j < sizeQ; j++) {
                if (j == sizeQ - 1) {
                    qStart = polyQ.get(j);
                    qEnd = polyQ.get(0);
                } else {
                    qStart = polyQ.get(j);
                    qEnd = polyQ.get(j + 1);
                }

                Point p = intersect(pStart, pEnd, qStart, qEnd);
                if (p != null) {
                    newPoly.add(p);
                }
            }
        }
        return newPoly;
    }

    static List<Point> giveMeThePolygon(List<Point> listP, List<Point> listQ, List<Point> intersections) throws Exception {

        if (intersections.isEmpty()) {
            throw new IllegalArgumentException("No Intersection");
        }

        listP = polygon(listP, listQ);
        listQ = polygon(listQ, listP);

        List<Point> finalList = new LinkedList<>();

        Integer[] index = new Integer[4];

        index[0] = listP.indexOf(intersections.get(0));
        index[1] = listP.indexOf(intersections.get(1));
        index[2] = listQ.indexOf(intersections.get(0));
        index[3] = listQ.indexOf(intersections.get(1));

        for (int i = 0; i < index[0]; i++) {
            finalList.add(listP.get(i));
        }
        for (int i = index[2]; i < index[3]; i++) {
            finalList.add(listQ.get(i));
        }
        for (int i = index[1]; i < listP.size(); i++) {
            finalList.add(listP.get(i));
        }

        return finalList;
    }

    static List<Point> giveTheInternalPoints(List<Point> listP, List<Point> listQ) {
        List<Point> list = new LinkedList<>();
        for (Point p : listP) {
            if (Methods.isInside(p, listQ)) {
                list.add(p);
            }
        }
        for (Point p : listQ) {
            if (Methods.isInside(p, listP)) {
                list.add(p);
            }
        }
        return list;
    }

    static List<Point> constructIntersectedPoly(List<Point> listP, List<Point> listQ) {
        List<Point> intr = giveTheInternalPoints(listP, listQ);
        List<Point> intersect = polygonalIntersection(listP, listQ);
        List<Point> list = new LinkedList<>(intr);
        list.addAll(intersect);
        return list;
    }

    static List<Point> intersectOfTwoPolynomial(List<Point> listP, List<Point> listQ) {
        List<Point> points = constructIntersectedPoly(listP, listQ);        
        return sortPolygon(points);
    }

    static double angle(Point c, Point p) {
        double y = p.getY() - c.getY();
        double x = p.getX() - c.getX();
        return Math.atan(y / x);
    }

    static int indexOfTheLowestY(List<Point> pts) {
        double y = Double.POSITIVE_INFINITY;
        int j = 0;
        for (int i = 0; i < pts.size(); i++) {
            if (pts.get(i).getY() < y) {
                y = pts.get(i).getY();
                j = i;
            }
        }
        return j;
    }

    static List<Point> sortPolygon(List<Point> pts) {
        int i = indexOfTheLowestY(pts);
        List<Point> res = new LinkedList<>();
        Map<Double, Point> map = new HashMap<>();
        res.add(pts.get(i));

        Point c = pts.get(i);

        for (int jj = 0; jj < pts.size(); jj++) {
            if (jj != i) {
                double rad = angle(c, pts.get(jj));
                double deg = Math.toDegrees(rad)<0?Math.toDegrees(rad)+180:Math.toDegrees(rad);
                map.put(deg, pts.get(jj));
            }
        }
        List<Double> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);

        for (int mm = 0; mm < keys.size(); mm++) {
            Point pt = map.get(keys.get(mm));
            res.add(pt);
        }
        return res;
    }

    static void printBeautifully(List<Point> points, String name) {
        System.out.println("========" + name + "========");
        points.forEach((e) -> {
            System.out.println(e);
        });
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}
