/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;
import java.util.Random;
import javax.swing.WindowConstants;

/**
 *
 * @author asus
 */
public class BSpline implements GLEventListener {

    private float rtri = 0.0f;
    public float angle = 0.0f;
    private GLU glu = new GLU();
    List<Point> list = new LinkedList<>();
    List<Point> list2 = new LinkedList<>();
    List<Point> pts = new LinkedList<>();
    List<Point> pts2 = new LinkedList<>();
    double[] vect = new double[]{0, 0, 0, 0, 1, 2, 3, 4, 4, 4, 4};
    public static float g = 0.003f;
    Random random = new Random();

    public static void main(String[] args) {

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.setSize(1000, 700);
        BSpline bspline = new BSpline();

        glcanvas.addGLEventListener(bspline);
        final JFrame frame = new JFrame("BSpline");

        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();
    }

    public static double RecursiveBSpline(int i, double[] t, int order, double x) {
        double result = 0;

        if (order == 0) {
            if (t[i] <= x && x < t[i + 1]) {
                result = 1;
            } else {
                result = 0;
            }
        } else {
            double denom1, denom2, num1, num2;

            denom1 = t[i + order + 1] - t[i + 1];
            denom2 = t[i + order] - t[i];

            if (denom1 == 0) {
                num1 = 0;
            } else {
                num1 = (t[i + order + 1] - x) / denom1;
            }

            if (denom2 == 0) {
                num2 = 0;
            } else {
                num2 = (x - t[i]) / denom2;
            }

            result = num1 * RecursiveBSpline(i + 1, t, order - 1, x)
                    + num2 * RecursiveBSpline(i, t, order - 1, x);
        }

        return result;
    }

    public Point DrawBSpline(int o) {

        Point cbc = null;
//        Point[] arr = new Point[7];

//        for (int i = 0; i <= 6; i++) {
//            double x = Math.random();
//            double y = Math.random();
//            double z = Math.random();
//            arr[i] = new Point(x, y, z);
//        }
        pts.add(new Point(-0.42015, 0.400, 0.350));
        pts.add(new Point(-0.12545, 0.564, 0.550));
        pts.add(new Point(0.15021, 0.642, 0.550));
        pts.add(new Point(0.21214, 0.499, 0.550));
        pts.add(new Point(0.45454, 0.520, 0.550));
        pts.add(new Point(0.56878, 0.550, 0.550));
        pts.add(new Point(0.87854, 0.652, 0.550));

        pts2.add(new Point(-0.72015, 0.100, 0.550));
        pts2.add(new Point(-0.12545, 0.164, 0.550));
        pts2.add(new Point(0.15021, 0.142, 0.550));
        pts2.add(new Point(0.21214, 0.199, 0.550));
        pts2.add(new Point(0.45454, 0.120, 0.550));
        pts2.add(new Point(0.56878, 0.150, 0.550));
        pts2.add(new Point(0.87854, 0.152, 0.550));

        for (double stp = 0; stp <= 1.0; stp += 0.01) {

            if (o == 0) {
                double x = 0, y = 0, z = 0;
                for (int i = 0; i <= 6; i++) {
                    double N = RecursiveBSpline(i, vect, 3, stp);
                    x += pts.get(i).getX() * N;
                    y += pts.get(i).getY() * N;
                    z += pts.get(i).getZ() * N;
                }

                cbc = new Point(x, y, z);

                list.add(cbc);
            } else {
                double x = 0, y = 0, z = 0;
                for (int i = 0; i <= 6; i++) {
                    double N = RecursiveBSpline(i, vect, 3, stp);
                    x += pts2.get(i).getX() * N;
                    y += pts2.get(i).getY() * N;
                    z += pts2.get(i).getZ() * N;
                }

                cbc = new Point(x, y, z);

                list2.add(cbc);
            }

        }

        return cbc;

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        // method body
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        // Clear The Screen And The Depth Buffer
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); // Reset The View
        gl.glTranslatef(0.3f, -0.3f, -6.0f);

        gl.glPointSize(.1f);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glColor3f(0.0f, 1.0f, 1.0f);
        DrawBSpline(0);
        DrawBSpline(1);

        pts.get(3).setY(pts.get(3).getY() + g * 5);
        pts.get(0).setY(pts.get(0).getY() + g);
        pts2.get(1).setY(pts2.get(1).getY() + g);
        pts2.get(2).setY(pts2.get(2).getY() + g);

        if (pts.get(0).getY() == 0.7870000033639372) {
            g *= -1;
        }
        if (pts.get(0).getY() == 0.08499999726191165) {
            g *= -1;
        }
        Point temp1 = list.get(0);
        Point temp2 = list2.get(0);
        Point p1, p2;
        for (int i = 0; i < list.size() - 1; i++) {

            p1 = list.get(i);
            p2 = list2.get(i);

            gl.glColor3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
            gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());

            gl.glColor3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
            gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
            gl.glColor3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
            gl.glVertex3d(temp1.getX(), temp1.getY(), temp1.getZ());

            gl.glColor3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
            gl.glVertex3d(temp2.getX(), temp2.getY(), temp2.getZ());
            gl.glColor3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
            gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
            gl.glColor3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
            gl.glVertex3d(temp1.getX(), temp1.getY(), temp1.getZ());

            temp1 = p1;
            temp2 = p2;
        }

        gl.glEnd();
        gl.glFlush();
        list.clear();
        list2.clear();

//          rtri += 0.2f;
//        angle += 0.2f;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        if (height <= 0) {
            height = 1;
        }

        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(10.0f, h, 1.0, 100.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

}
