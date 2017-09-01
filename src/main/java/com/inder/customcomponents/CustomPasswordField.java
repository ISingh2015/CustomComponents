/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inder.customcomponents;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPasswordField;

/**
 *
 * @author ISanhot
 */
public class CustomPasswordField extends JPasswordField {

    private Shape shape;

    public CustomPasswordField() {
        doInit();
    }

    private void doInit() {
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                CustomPasswordField.this.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(null) || getText().equals("")) {
                    setText("0");
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                int key = evt.getKeyCode();
                if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_TAB) {
                    transferFocus();
                }
            }
        });

    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }
        return shape.contains(x, y);
    }
}
