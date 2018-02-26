/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

import com.jogamp.opengl.*;



/**
 *
 * @author asus
 */
public class BezierSurface implements GLEventListener {
 
    public BezierSurface() {

    }

    public static void main(String[] args) {
        BezierSurface b1 = new BezierSurface();
        Point[][] arr = new Point[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double x = Math.random();
                double y = Math.random();
                double z = Math.random();
                arr[i][j] = new Point(x, y, z);
            }
        }

        
        for (double u = 0; u < 1; u += 0.1) {
            for (double v = 0; v < 1; v += 0.1) {
                b1.evaluate(arr, u, v);
            }
        }
       
    }

    public int fact(int n) {
        int res = 1;
        for (int i = 1; i <= n; i++) {
            res *= i;
        }
        return res;

    }

    public double bernstein(int n, int i, double u) {
        double res = fact(n) / fact(i) * fact(n - i) * Math.pow(u, i) * Math.pow(1 - u, n - i);
        return res;
    }

    public Point evaluate(Point[][] arr, double u, double v) {
        double x = 0;
        double y = 0;
        double z = 0;
        int n = arr.length;
        int m = arr[0].length;
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < m; j++) {
                x += bernstein(n, i, u) * bernstein(m, j, v) * arr[i][j].getX();
                y += bernstein(n, i, u) * bernstein(m, j, v) * arr[i][j].getY();
                z += bernstein(n, i, u) * bernstein(m, j, v) * arr[i][j].getZ();
                
              
                      
            } 

        }
        Point p = new Point(x, y, z);
 
     
        return p;

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void display(GLAutoDrawable drawable) {
         final GL2 gl = drawable.getGL().getGL2();
         gl.glBegin (GL2.GL_SURFACE_MAPPED_NV);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
