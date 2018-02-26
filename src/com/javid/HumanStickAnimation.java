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
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author asus
 */
public class HumanStickAnimation implements GLEventListener {

    static boolean left = false;
    static boolean right = true;

    static float flx = -0.5f;
    static float fly = -1f;
    static boolean test = false;

    public static void main(String[] args) {
        HumanStickAnimation h1 = new HumanStickAnimation();

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.setSize(1000, 700);

        glcanvas.addGLEventListener(h1);
        final JFrame frame = new JFrame("Detection");

        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        
    }

    public void drawStickman(GL2 gl) {
        

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    public static boolean isLeft() {
        return left;
    }

    public static void setLeft(boolean left) {
        HumanStickAnimation.left = left;
    }

    public static boolean isRight() {
        return right;
    }

    public static void setRight(boolean right) {
        HumanStickAnimation.right = right;
    }
}
