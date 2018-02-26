/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose4 Tools | Templates
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

import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author javiko
 */
public class RectangleAnimate implements GLEventListener {

    float limitL = -0.93f;
    float limitR = 0.93f;

    private static float y = -0.5f;
    private static float x = -1.0f;
    private static float yr = -0.5f;
    private static float yr0 = -0.5f;
    float cx;
    float cy;

    private boolean LD = false;
    private boolean RD = true;
    private boolean RU = false;
    private boolean LU = false;
    private int status = 4;
    private final float speed = 0.01f;
    private final float speedy = 0.02f;
    private final GLU glu = new GLU();
    private static final float side = 0.1f;
    private final float centerX = 0.0f;
    private final float centerY = 0.0f;
    static boolean up;
    static boolean down;
    static boolean up0;
    static boolean down0;
    private static int p1 = 0;
    private static int p2 = 0;

    public static void main(String[] args) {
      

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.setSize(1000, 700);
        RectangleAnimate rectangle = new RectangleAnimate();
        glcanvas.addKeyListener(new KeyListenerDemo());
        glcanvas.setFocusable(true);
        glcanvas.requestFocus();
        glcanvas.addGLEventListener(rectangle);

        JTextField textField = new JTextField();
        JButton button = new JButton();
        button.setText("Play");
        button.setLocation(-1, 1);
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                rectangle.setStatus(4);
                rectangle.setRD(true);
                textField.setText("" + p1 + "     " + p2);
            }
        });
        textField.setEditable(false);
        textField.setBackground(Color.BLACK);

        textField.setFont(new java.awt.Font("Roboto", Font.BOLD, 30));
        textField.setForeground(Color.GREEN);
        textField.setHorizontalAlignment(JTextField.CENTER);

        final JFrame frame = new JFrame();

        frame.getContentPane().add(glcanvas);
        frame.add(textField, BorderLayout.NORTH);
        frame.add(button, BorderLayout.PAGE_END);

        textField.setText("" + p1 + "     " + p2);

        frame.setSize(frame.getContentPane().getPreferredSize());

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();

    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setRD(boolean RD) {
        this.RD = RD;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void init(GLAutoDrawable arg0) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {

        cx = x + centerX;
        cy = y + centerY;

        final GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        final GL2 gl4 = drawable.getGL().getGL2();
        gl4.glLoadIdentity();

        gl4.glBegin(GL2.GL_LINES);
        gl4.glColor3f(0, 1.0f, 0);
        gl4.glVertex3f(0f, -1.0f, 0);
        gl4.glVertex3f(0f, 1f, 0);

        gl4.glEnd();
        gl4.glFlush();
        final GL2 gl3 = drawable.getGL().getGL2();
        move();
        gl3.glLoadIdentity();
        gl3.glTranslatef(0, yr, 0);
        gl3.glBegin(GL2.GL_QUADS);
        gl3.glColor3f(0.9f, 0, 0);
        gl3.glVertex3f(1f, 0.8f, 0);
        gl3.glVertex3f(0.97f, 0.8f, 0);
        gl3.glVertex3f(0.97f, 0.4f, 0);
        gl3.glVertex3f(1f, 0.4f, 0);
        gl3.glEnd();
        gl3.glFlush();

        final GL2 gl2 = drawable.getGL().getGL2();

        gl2.glLoadIdentity();
        gl2.glTranslatef(0, yr0, 0);
        gl2.glBegin(GL2.GL_QUADS);
        gl2.glVertex3f(-1f, 0.8f, 0);
        gl2.glVertex3f(-0.97f, 0.8f, 0);
        gl2.glVertex3f(-0.97f, 0.4f, 0);
        gl2.glVertex3f(-1f, 0.4f, 0);

        gl2.glEnd();
        gl2.glFlush();
        gl.glLoadIdentity();
        gl.glScalef(0.95f, 0.95f, 0);
        gl.glTranslatef(x, y, 0);

        // System.out.println(x + " " + y);
        gl.glBegin(GL2.GL_QUADS);

        Draw(centerX, centerY, side, gl);

        ballMove(gl);

        gl.glEnd();
        gl.glFlush();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        final float h = (float) width / (float) height;

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glMatrixMode(GL2.GL_MODELVIEW);

    }

    public void Draw(double centerX, double centerY, double side, GL2 gl) {
        double hSide = side / 2;
        double xRight = centerX + hSide;
        double xLeft = centerX - hSide;
        double yUp = centerY + hSide;
        double yDown = centerY - hSide;

        gl.glColor3f(0.9f, 0, 0);
        gl.glVertex3d(xRight, yDown, 0);
        gl.glVertex3d(xLeft, yDown, 0);
        gl.glVertex3d(xLeft, yUp, 0);
        gl.glVertex3d(xRight, yUp, 0);

    }

    public static void move() {
        if (up && yr < 0.2f) {
            yr += 0.09f;
            System.out.println(yr);
            up = false;
        }
        if (down && yr > -1.4f) {
            yr -= 0.09f;
            down = false;
            System.out.println(yr);
        }
        if (up0 && yr0 < 0.2f) {
            yr0 += 0.09f;
            System.out.println(yr0);
            up0 = false;
        }
        if (down0 && yr0 > -1.4f) {
            yr0 -= 0.09f;
            down0 = false;
            System.out.println(yr0);
        }
    }

    public void ballMove(GL2 gl) {
        switch (status) {
            case 1:
                x -= speed;
                y -= speedy;

                break;
            case 2:
                x -= speed;
                y += speedy;

                break;
            case 3:
                x += speed;
                y += speedy;

                break;
            case 4:

                x += speed;
                y -= speedy;

                break;
            case 5:

                gl.glEnd();
                gl.glFlush();
                y = 0.0f;
                x = 0;

                break;

        }

        if (LD && (int) cy <= -1 + side / 2) {

            LD = false;
            LU = true;
            status = 2;

        }
        if (LU) {
            if ((cx < -0.95f)) {
                if ((cy <= yr0 + 0.9f && cy >= yr0 + 0.4f)) {
                    LU = false;
                    RU = true;

                    status = 3;
                } else {

                    status = 5;
                }

            }

        }
        if (RU && (int) cy >= 1 - side / 2) {
            RU = false;
            RD = true;

            status = 4;
        }
        if (RD) {
            if ((cx > 0.95f)) {
                if ((cy <= yr + 0.9f && cy >= yr + 0.398888f)) {
                    RD = false;
                    LD = true;
                    status = 1;
                } else {

                    status = 5;
                }
            }

        }
        if (RD && (int) cy <= -1 + side / 2) {
            RD = false;
            RU = true;
            status = 3;
        }
        if (RU) {
            if ((cx > 0.95f)) {
                if ((cy <= yr + 0.9f && cy >= yr + 0.3988888f)) {
                    RU = false;
                    LU = true;
                    status = 2;
                    System.out.println(cy);
                } else {

                    status = 5;

                }

            }
        }
        if (LU && (int) cy >= 1 - side / 2) {
            LU = false;
            LD = true;
            status = 1;
        }
        if (LD) {
            if ((cx < -0.95f)) {
                if ((cy <= yr0 + 0.9f && cy >= yr0 + 0.4f)) {
                    LD = false;
                    RD = true;
                    status = 4;
                    System.out.println(cy);
                } else {

                    status = 5;

                }

            }

        }
    }

}
