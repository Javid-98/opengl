/* * To change this license header, choose License Headers in Project Properties.
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author asus
 */
public class AngryBird extends JFrame implements GLEventListener {

    public static float moveBallY = 0f;
    public static float moveStickY = 0f;
    public static float moveStickX = .8f;
    public static float speed = 0.005f;
    public static boolean shoot = false;
    public static float shootY = .0f;

    TextRenderer textRenderer = new TextRenderer(new Font("Colibri", Font.BOLD, 20));

    public static void main(String[] args) {
        AngryBird a1 = new AngryBird();

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.setSize(1000, 700);
        glcanvas.addKeyListener(new KeyListenerAngryBirds());
        glcanvas.setFocusable(true);
        glcanvas.requestFocus();
        glcanvas.addGLEventListener(a1);

        JButton button = new JButton();
        button.setText("RESTART");
        button.setSize(10, 10);
        button.setLocation(-1, 1);
        button.setFocusable(false);
        button.addActionListener((ActionEvent evt) -> {
            restart();
        });

        final JFrame frame = new JFrame("AngryBirds");

        frame.getContentPane().add(glcanvas);
        frame.add(button,  BorderLayout.PAGE_START);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        if (shoot == true && moveStickY == 0) {
            moveStickX -= 0.01f;

        } else if (shoot == true && moveStickY < 0) {
            moveStickX -= 0.01f;
            moveStickY -= 0.005f;
            shootY -= 0.005f;
        } else if (shoot == true && moveStickY > 0) {
            moveStickX -= 0.01f;
            moveStickY += 0.005f;
            shootY += 0.005f;
        }

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glColor4f(.6f, 0.0f, 0.0f, 0.0f);
        gl.glLoadIdentity();
        gl.glPushMatrix();

//        gl.glTranslatef(0, moveBallY += speed, 0);
//        gl.glRotated(180, 1, 0, 0);
//        gl.glScalef(0.20f, 0.20f, 0);
//
//        gl.glTranslated(-5, -4, 0);
//        DrawCircle(-0.8f, 0.0f, 0.1f, 100, gl);
        drawFilledCircle(-.8f, moveBallY += speed, 0.1f, gl);

        moveCirc();
        drawStick(moveStickX, moveStickY, gl);
        if (inCircle()) {
            textRenderer.draw("You have WON!", 350, 350);
            shoot = false;
            speed = 0;

//            restart();
        } else if ((moveStickX == -0.99999887f) && !(inCircle())) {
            textRenderer.draw("You have LOST!", 350, 350);
            shoot = false;
            speed = 0;
//            restart();
        }

        textRenderer.beginRendering(900, 700);

        textRenderer.setSmoothing(true);

        textRenderer.endRendering();
        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();

    }

    static void restart() {
        speed = 0;
        moveBallY = 0f;
        moveStickY = 0f;
        moveStickX = .8f;
        speed = 0.005f;
        shoot = false;
        shootY = .0f;

    }

    void moveCirc() {
        if (moveBallY == 0.8949993f) {
            speed *= -1;
        }
        if (moveBallY == -0.89499927f) {
            speed *= -1;
        }
    }

    boolean inCircle() {
//        float distance = (float) Math.sqrt((Math.pow((moveStickX + .2f) - moveStickX, 2)) + Math.pow(shootY - moveStickY, 2));
//        if ((((-.8f - moveStickX) * (shootY - moveStickY) - (moveBallY - moveStickY) * ((moveStickX + .2f) - moveStickX)) / distance) <= 0.1f) {
//            System.out.println("asssdt");
//        }

        return (Math.pow(moveStickX - (-.8f), 2) + Math.pow(moveStickY - moveBallY, 2)) <= Math.pow(0.1f, 2);

    }

    void drawStick(float x, float y, GL2 gl) {
        gl.glLineWidth(10f);
        gl.glBegin(GL.GL_LINES);

        gl.glVertex3f(x, y, 0.0f);
        gl.glVertex3f(x + .2f, shootY, 0);
        gl.glEnd();
    }

    void DrawCircle(float cx, float cy, float r, int num_segments, GL2 gl) {
        gl.glBegin(GL_LINE_LOOP);
        for (int ii = 0; ii < num_segments; ii++) {
            float theta = (float) (2.0f * Math.PI * (float) ii / (float) num_segments);//get the current angle

            float x = (float) (r * Math.cos(theta));  //calculate the x component
            float y = (float) (r * Math.sin(theta));//calculate the y component

            gl.glVertex2f(x + cx, y + cy);//output vertex

        }
        gl.glEnd();
    }

    void drawFilledCircle(float x, float y, float radius, GL2 gl) {
        int i;
        int triangleAmount = 100; //# of triangles used to draw circle

        //GLfloat radius = 0.8f; //radius
        gl.glBegin(GL_TRIANGLE_FAN);
        gl.glVertex2f(x, y); // center of circle
        for (i = 0; i <= triangleAmount; i++) {
            gl.glVertex2f(
                    (float) (x + (radius * Math.cos(i * 2 * Math.PI / triangleAmount))),
                    (float) (y + (radius * Math.sin(i * 2 * Math.PI / triangleAmount)))
            );
        }
        gl.glEnd();
    }

// =================================================================================================
    @Override
    public void init(GLAutoDrawable drawable) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
