/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author OwaryLtd
 */
public class DummyOpenGLClass extends JPanel implements GLEventListener {

    private static final int REFRESH_FPS = 60;  // Display refresh frames per second
    public static FPSAnimator animator;         // Used to drive display() 
    private GLU glu;                            // For the GL Utility

    private List<Point> poly;
    private List<Point> shiftedPoly;


    /* End */
    public DummyOpenGLClass() {
        GLCanvas canvas = new GLCanvas();
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        canvas.addGLEventListener(this);

        animator = new FPSAnimator(canvas, REFRESH_FPS, true);

        poly = new LinkedList<>();
        shiftedPoly = new LinkedList<>();

        poly.add(new Point(-150, 170));
        poly.add(new Point(80, 210));
        poly.add(new Point(190, 175));
        poly.add(new Point(185, -120));
        poly.add(new Point(120, -220));
        poly.add(new Point(-140, -130));
        
        poly.forEach((e) -> {
            shiftedPoly.add(new Point(e.getX() + 250, e.getY() - 10));
        });
        

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final Point p = new Point(-140, -130);
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        List<Point> pts = Driver.polygonalIntersection(poly, shiftedPoly);
        List<Point> polygon = null;

        try {
            polygon = Driver.giveMeThePolygon(poly, shiftedPoly, pts);

            List<Point> intersectedPoly = Driver.intersectOfTwoPolynomial(poly, shiftedPoly);

            drawPoly(gl, polygon);
            gl.glColor3d(1, 0, 0);
            drawPoly(gl, intersectedPoly);

            drawPoly(gl, intersectedPoly);
            for (Point pt : pts) {
                drawPoint(gl, pt);
            }
        } catch (Exception ex) {
            drawPoly(gl, poly);
            drawPoly(gl, shiftedPoly);
        }

    }

    public void drawLine(GL2 gl, Point pStart, Point pEnd) {
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glBegin(GL2.GL_LINES);
        gl.glPointSize(1.65f);

        gl.glVertex2d(pStart.getX(), pStart.getY());
        gl.glVertex2d(pEnd.getX(), pEnd.getY());

        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();
    }

    public void drawPoly(GL2 gl, List<Point> p) {
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glPointSize(1.65f);

        for (Point point : p) {
            gl.glVertex2d(point.getX(), point.getY());
        }

        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();
    }

    public void drawPoint(GL2 gl, Point p) {
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glPointSize(5.65f);
        gl.glColor3d(0, 0, 1);
        gl.glBegin(GL2.GL_POINTS);

        gl.glVertex2d(p.getX(), p.getY());

        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        GL2 gl = glad.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-500, 500, -240, 240, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public static void main(String[] args) {
        final int WINDOW_WIDTH = 640;  // Width of the drawable
        final int WINDOW_HEIGHT = 480; // Height of the drawable
        final String WINDOW_TITLE = "3D Shapes";

        JFrame frame = new JFrame();
        final DummyOpenGLClass joglMain = new DummyOpenGLClass();
        frame.setContentPane(joglMain);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Use a dedicate thread to run the stop() to ensure that the
                // animator stops before program exits.
                new Thread() {
                    @Override
                    public void run() {
                        joglMain.animator.stop(); // stop the animator loop
                        System.exit(0);
                    }
                }.start();
            }
        });
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setTitle(WINDOW_TITLE);
        frame.setVisible(true);
//        joglMain.animator.start(); // start the animation loop
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // Get the OpenGL graphics context
        GL2 gl = drawable.getGL().getGL2();
        // GL Utilities
        glu = new GLU();
        // Enable smooth shading, which blends colors nicely, and smoothes out lighting.
        gl.glShadeModel(GL2.GL_SMOOTH);
        // Set background color in RGBA. Alpha: 0 (transparent) 1 (opaque) 
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Setup the depth buffer and enable the depth testing
        gl.glClearDepth(1.0f);          // clear z-buffer to the farthest
        gl.glEnable(GL.GL_DEPTH_TEST);  // enables depth testing
        gl.glDepthFunc(GL.GL_LEQUAL);   // the type of depth test to do
        // Do the best perspective correction
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

    }

    @Override
    public void dispose(GLAutoDrawable glad) {
    }
}
