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
public class Methods {

    public static double polygonalArea(List<Point> vertice) {
        double area = 0;
        int size = vertice.size();

        Point pStart = null;
        Point pEnd = null;

        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                pStart = vertice.get(i);
                pEnd = vertice.get(0);
            } else {
                pStart = vertice.get(i);
                pEnd = vertice.get(i + 1);
            }
            double term = pStart.getX() * pEnd.getY() - pStart.getY() * pEnd.getX();
            area += term / 2;
        }
        return Math.abs(area);
    }

    // Method 1. Area of Polygon equals to the sum of the smaller triangles
    public static boolean isInside(Point p, List<Point> polygon) {
        int size = polygon.size();
        double polygonalArea = 0;
        double realArea = 0;
        Point pStart = null;
        Point pEnd = null;

        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                pStart = polygon.get(i);
                pEnd = polygon.get(0);
            } else {
                pStart = polygon.get(i);
                pEnd = polygon.get(i + 1);
            }

            List<Point> vertices = new LinkedList<>();
            vertices.add(pStart);
            vertices.add(pEnd);
            vertices.add(p);

            polygonalArea += polygonalArea(vertices);
        }

        realArea = polygonalArea(polygon);
        return polygonalArea == realArea;
    }

    
    public static void main(String[] args) {
        
        Point pStart    = new Point(0,1);
        Point pEnd      = new Point(5,11);
        Point qStart    = new Point(0,10);
        Point qEnd      = new Point(5,15);
        
        
        
    }

}
