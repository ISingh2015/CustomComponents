/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inder.customcomponents;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Inderjit
 */
public class IFadingPanel extends JPanel
        implements ActionListener {

    private static final long serialVersionUID = 1L;

    private float opacity = 0f;
    private Timer fadeTimer;

    public void beginFade() {
        fadeTimer
                = new javax.swing.Timer(75, this);
        fadeTimer.setInitialDelay(0);
        fadeTimer.start();

    }

    public void actionPerformed(ActionEvent e) {
        opacity += .03;
        if (opacity > 1) {
            opacity = 1;
            fadeTimer.stop();
            fadeTimer = null;
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        ((Graphics2D) g).setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        opacity));
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
