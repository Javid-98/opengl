/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

import com.jogamp.opengl.GL;
import static com.jogamp.opengl.GL.GL_LINE_LOOP;
import static com.jogamp.opengl.GL.GL_TRIANGLE_FAN;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author asus
 */
public class PolyIntersect extends JFrame implements GLEventListener {

    List<Point> poly1;
    List<Point> poly2;

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        DrawPoly(gl, poly1);
    }

    public PolyIntersect() {

        poly1 = new LinkedList<>();
        poly2 = new LinkedList<>();
        poly1.add(new Point(-150, 170, 0));
        poly1.add(new Point(80, 210, 0));
        poly1.add(new Point(190, 175, 0));
        poly1.add(new Point(185, -120, 0));
        poly1.add(new Point(120, -220, 0));
        poly1.add(new Point(-140, -130, 0));
    }

    public void DrawPoly(GL2 gl, List<Point> p) {

        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glBegin(GL2.GL_LINE_LOOP);

        p.forEach((point) -> {
            gl.glVertex2d(point.getX(), point.getX());
        });

        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        // Get the OpenGL graphics context
        GL2 gl = drawable.getGL().getGL2();

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
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    public static void main(String[] args) {

        PolyIntersect p1 = new PolyIntersect();

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.setSize(1000, 700);

        glcanvas.addGLEventListener(p1);
        final JFrame frame = new JFrame("Poly");

        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();
    }

}
