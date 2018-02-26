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
public class KeyListenerDemo extends RectangleAnimate implements java.awt.event.KeyListener {

    private static LinkedList<KeyEvent> set = new LinkedList<>();
    RectangleAnimate r = new RectangleAnimate();

    @Override
    public void keyTyped(KeyEvent e) {

    }

//    @Override
//    public void keyPressed(KeyEvent e) {
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_P:
//                up = true;
//                down = false;
//                break;
//            case KeyEvent.VK_L:
//                down = true;
//                up = false;
//                break;
//
//        }
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_W:
//                up0 = true;
//                down0 = false;
//                break;
//            case KeyEvent.VK_S:
//                down0 = true;
//                up = false;
//                break;
//        }
//    }
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        set.add(e);
        for (KeyEvent b : set) {
            switch (b.getKeyCode()) {
                case KeyEvent.VK_P:
                    up = true;
                    down = false;
                    

                    break;
                case KeyEvent.VK_L:
                    down = true;
                    up = false;
                   
                    break;
                case KeyEvent.VK_W:
                    up0 = true;
                    down0 = false;
                   
                    break;
                case KeyEvent.VK_S:
                    down0 = true;
                    up0 = false;
                    
                    break;

            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        set.remove(e);
    }

}
