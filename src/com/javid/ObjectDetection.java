/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import java.util.*;
import java.util.Random;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author asus
 */
public class ObjectDetection implements GLEventListener {

    static final int ROW = 10, COL = 10;
    static List<List<Point>> main_list = new ArrayList<>();
    int values[][] = new int[ROW][COL];
    public static float a = 1.0f;
    public float b = 0f;
    public static int i = 0;
    Random random_color = new Random();

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glRotated(180, 1, 0, 0);
        gl.glScalef(0.20f, 0.20f, 0);

        gl.glTranslated(-5, -4, 0);
        gl.glBegin(GL2.GL_QUADS);

        double stX = 0;
        double stY = 0;
        double side = 1;

        for (i = 0; i < main_list.size(); i++) {
            gl.glColor3d(random_color.nextDouble(), random_color.nextDouble(), random_color.nextDouble());
            for (int j = 0; j < main_list.get(i).size(); j++) {
                drawSquare(main_list.get(i).get(j).getX(), main_list.get(i).get(j).getY(), 1, gl);

            }

        }

        gl.glPopMatrix();
        gl.glFlush();
        gl.glEnd();

    }

    public void drawSquare(double stX, double stY, double side, GL2 gl) {
        gl.glVertex3d(stX, stY, 0);
        gl.glVertex3d(stX + side, stY, 0);
        gl.glVertex3d(stX + side, stY - side, 0);
        gl.glVertex3d(stX, stY - side, 0);
    }

    public static void main(String[] args) {
        ObjectDetection o1 = new ObjectDetection();
        System.out.println(o1.countIslands(o1.createMatrix()));
        main_list.forEach(System.out::println);

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.setSize(1000, 700);

        glcanvas.addGLEventListener(o1);
        final JFrame frame = new JFrame("Detection");

        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
//        animator.start();
    }

    public int[][] createMatrix() {
        Random random = new Random();

        for (int[] value : values) {
            // do the for in the row according to the column size
            for (int j = 0; j < value.length; j++) {
                if (random.nextDouble() < 0.2) {
                    value[j] = 1;
                } else {
                    value[j] = 0;
                }
                System.out.print(value[j]);
            }
            // add a new line
            System.out.println();
        }

        return values;
    }

    boolean isSafe(int M[][], int row, int col, boolean visited[][]) {
        // row number is in range, column number is in range
        // and value is 1 and not yet visited
        return (row >= 0) && (row < ROW)
                && (col >= 0) && (col < COL)
                && (M[row][col] == 1 && !visited[row][col]);
    }

    void DFS(int M[][], int row, int col, boolean visited[][], List<Point> list) {
        // These arrays a re used to get row and column numbers
        // of 8 neighbors of a given cell
        int rowNbr[] = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        int colNbr[] = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

        // Mark this cell as visited
        visited[row][col] = true;
//        List<Point> temp_list = new ArrayList<>();
        // Recur for all connected neighbours
        for (int k = 0; k < 8; ++k) {
            if (isSafe(M, row + rowNbr[k], col + colNbr[k], visited)) {

                double x = col + colNbr[k];
                double y = row + rowNbr[k];
                Point p1 = new Point(x, y);

                list.add(p1);

                DFS(M, row + rowNbr[k], col + colNbr[k], visited, list); 

            }
        }

    }

    int countIslands(int M[][]) {
        // Make a bool array to mark visited cells.
        // Initially all cells are unvisited
        boolean visited[][] = new boolean[ROW][COL];
        
        

        // Initialize count as 0 and travese through the all cells
        // of given matrix
        int count = 0;
        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; ++j) {
                if (M[i][j] == 1 && !visited[i][j]) // If a cell with
                {                                 // value 1 is not
                    // visited yet, then new island found, Visit all
                    // cells in this island and increment island count
                    List<Point> foo = new ArrayList<>();
                    double x = j;
                    double y = i;
                    Point p2 = new Point(x, y);
                    foo.add(p2);

                    DFS(M, i, j, visited, foo);
                    main_list.add(foo);
                    ++count;
                }
            }
        }

        return count;
    }

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

    }

}
