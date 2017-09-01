/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inder.customcomponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.Action;
import javax.swing.JButton;

/**
 *
 * @author Inderjit
 */
public class CustomButton extends JButton {

    private static final long serialVersionUID = 1L;
    private String toolTip;
    private Timer timer;
    private Shape shape;

    public CustomButton(Action a) {
        super.setAction(a);
        super.setBorderPainted(false);
        doSetUp();
        setForeground(Color.orange);

    }

    public CustomButton() {
        super.setBorderPainted(false);
        doSetUp();
        setForeground(Color.orange);
    }

    private void doSetUp() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new ShowToolTip(getToolTip()), 100, 1000);
    }

    /**
     * @return the toolTip
     */
    public String getToolTip() {
        return toolTip;
    }

    /**
     * @param toolTip the toolTip to set
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    private class ShowToolTip extends TimerTask {

        private String tip;

        ShowToolTip(String s) {
            this.tip = s;
        }

        @Override
        public void run() {
            if (!isEnabled()) {
                setToolTip(null);
            } else {
                setToolTip(tip);
            }
        }

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
