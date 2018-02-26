    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template JOGLEx2Shape3DFullScreenMain.this., choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

/**
 *
 * @author OwaryLtd
 */
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

public class JOGLEx2Shape3DFullScreenMain extends JFrame /*implements KeyListener*/ {

    private static final String WINDOW_TITLE = "3D Shapes in Full Screen Mode";
    private static int windowWidth = 1000;  // size in non-full-screen mode
    private static int windowHeight = 480;

    private DummyOpenGLClass joglMain;
    
    private GraphicsDevice device;
    private boolean fullScreen = true; // full-screen or windowed mode
    public List<KeyEvent> list = new LinkedList<>();

    // Constructor
    public JOGLEx2Shape3DFullScreenMain() {
        joglMain = new DummyOpenGLClass();
        this.getContentPane().add(joglMain);

        // Get the default graphic device and try full screen mode
        device = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
            this.setSize(windowWidth, windowHeight);
            this.setResizable(false);
            fullScreen = false;
//        }

        this.addWindowListener(new WindowAdapter() {
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

        // Enable keyboard input
        this.addKeyListener(new MultiKeyListener());
        this.setFocusable(true);  // To receive key event
        this.requestFocus();

        this.setTitle(WINDOW_TITLE);
        this.setVisible(true);
        joglMain.animator.stop(); // start the animation loop
    }

    public static void main(String[] args) {
        new JOGLEx2Shape3DFullScreenMain();
    }

    class MultiKeyListener implements KeyListener {

        
        @Override
        public synchronized void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                // F1 to toggle between full-screen and windowed modes
                case KeyEvent.VK_F1:
                    if (!fullScreen) {  // Saved the current size for restoration
                        Dimension screenSize = JOGLEx2Shape3DFullScreenMain.this.getSize();
                        windowWidth = (int) screenSize.getWidth();
                        windowHeight = (int) screenSize.getHeight();
                    }
                    fullScreen = !fullScreen;
                    JOGLEx2Shape3DFullScreenMain.this.setVisible(false); // Hide the display
                    if (JOGLEx2Shape3DFullScreenMain.this.isDisplayable()) {
                        JOGLEx2Shape3DFullScreenMain.this.dispose();      // For changing the decoration
                    }
                    if (fullScreen) {
                        if (device.isFullScreenSupported()) {
                            JOGLEx2Shape3DFullScreenMain.this.setUndecorated(true);
                            JOGLEx2Shape3DFullScreenMain.this.setResizable(false);
                            device.setFullScreenWindow(JOGLEx2Shape3DFullScreenMain.this);
                        }
                    } else {
                        JOGLEx2Shape3DFullScreenMain.this.setUndecorated(false);  // Put the title and border back
                        device.setFullScreenWindow(null); // Windowed mode
                        JOGLEx2Shape3DFullScreenMain.this.setSize(windowWidth, windowHeight);
                        JOGLEx2Shape3DFullScreenMain.this.setResizable(true);
                    }
                    JOGLEx2Shape3DFullScreenMain.this.setVisible(true);  // Show it
                    break;

                // ESC to quit
                case KeyEvent.VK_ESCAPE:
                    // Use a dedicate thread to run the stop() to ensure that the
                    // animator stops before program exits.
                    new Thread() {
                        @Override
                        public void run() {
                            joglMain.animator.stop(); // stop the animator loop
                            System.exit(0);
                        }
                    }.start();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

    }

}
