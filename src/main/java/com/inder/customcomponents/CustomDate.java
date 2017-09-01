/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inder.customcomponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

/**
 *
 * @author Inderjit
 */
public class CustomDate extends JXDatePicker {

    private static final long serialVersionUID = 1L;
    public static String DISPLAYDATEFORMAT = "dd/MM/yyyy";
    // All Date Field to be editable - Default = False
    private Shape shape;

    public CustomDate() {
        this(false);
        setDate(new Date());
        UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
        super.setFormats(new SimpleDateFormat(DISPLAYDATEFORMAT));
        super.getMonthView().setZoomable(true);
        super.getMonthView().setDaysOfTheWeekForeground(Color.red);
    }

    public CustomDate(boolean editAllowed) {
        JFormattedTextField jft = super.getEditor();
        jft.setEditable(editAllowed);
        jft.setBackground(new Color(204, 255, 204));
        JButton button = (JButton) super.getComponent(1);
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
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
