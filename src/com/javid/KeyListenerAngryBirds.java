/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javid;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author asus
 */
public class KeyListenerAngryBirds extends AngryBird implements java.awt.event.KeyListener {

    private static LinkedList<KeyEvent> set = new LinkedList<>();
    RectangleAnimate r = new RectangleAnimate();

    @Override
    public synchronized void keyPressed(KeyEvent e) {

        if (shoot == false) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    moveStickY += 0.04f;

                    break;
                case KeyEvent.VK_S:
                    moveStickY -= 0.04f;

                    break;
                case KeyEvent.VK_SPACE:

                    shoot = true;
                    break;

            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
