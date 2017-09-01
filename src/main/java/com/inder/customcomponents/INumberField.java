package com.inder.customcomponents;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Point;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.text.*;

public class INumberField extends JTextField {

    private static final char DOT = '.';
    private static final char NEGATIVE = '-';
    private static final String BLANK = "";
    private static final int DEF_PRECISION = 2;
    public static final int NUMERIC = 2;
    public static final int DECIMAL = 3;
    public static final String FM_NUMERIC = "0123456789";
    public static final String FM_DECIMAL = FM_NUMERIC + DOT;
    private static final long serialVersionUID = 1L;
    private int maxLength = 10;
    private int format = NUMERIC;
    private String negativeChars = BLANK;
    private String allowedChars = null;
    private boolean allowNegative = false;
    private int precision = 2;
    protected PlainDocument numberFieldFilter;
    private static final String ERROR_TEXT = "Please only add numbers to the text field";
    private JWindow errorWindow;
    private Shape shape;

    public INumberField() {
        this(10, NUMERIC);
    }

    public INumberField(int maxLen) {
        this(maxLen, NUMERIC);
    }

    public INumberField(int maxLen, int format) {
        setAllowNegative(true);
        setMaxLength(maxLen);
        setFormat(format);
        if (format == NUMERIC) {
            setPrecision(0);
        }
        numberFieldFilter = new JNumberFieldFilter();
        super.setDocument(numberFieldFilter);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                INumberField.this.selectAll();
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
                    if (getText().equals(null) || getText().equals("")) {
                        setText("0");
                    }
                    transferFocus();
                }
            }
        });

    }

    private void showErrorWin(String error) {
        if (errorWindow == null) {
            JLabel errorLabel = new JLabel(error);
            Window topLevelWin = SwingUtilities.getWindowAncestor(this);
            errorWindow = new JWindow(topLevelWin);
            JPanel contentPane = (JPanel) errorWindow.getContentPane();
            contentPane.add(errorLabel);
            contentPane.setBackground(Color.white);
            contentPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            errorWindow.pack();
        }

        Point loc = super.getLocationOnScreen();
        errorWindow.setLocation(loc.x + 20, loc.y + 30);
        errorWindow.setVisible(true);
    }

    public void setMaxLength(int maxLen) {
        if (maxLen > 0) {
            maxLength = maxLen;
        } else {
            maxLength = 10;
        }
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setPrecision(int precision) {
        if (format == NUMERIC) {
            return;
        }

        if (precision >= 0) {
            this.precision = precision;
        } else {
            this.precision = DEF_PRECISION;
        }
    }

    public int getPrecision() {
        return precision;
    }

    public Number getNumber() {
        Number number = null;

        if (format == NUMERIC) {
            number = new Integer(getText());
        } else {
            number = new Double(getText());
        }

        return number;
    }

    public void setNumber(Number value) {
        setText(String.valueOf(value));
    }

    public int getInt() {
        return Integer.parseInt(getText());
    }

    public void setInt(int value) {
        setText(String.valueOf(value));
    }

    public float getFloat() {
        return (new Float(getText())).floatValue();
    }

    public void setFloat(float value) {
        setText(String.valueOf(value));
    }

    public double getDouble() {
        return (new Double(getText())).doubleValue();
    }

    public void setDouble(double value) {
        setText(String.valueOf(value));
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        switch (format) {
            case NUMERIC:
            default:
                this.format = NUMERIC;
                this.precision = 0;
                this.allowedChars = FM_NUMERIC;
                break;

            case DECIMAL:
                this.format = DECIMAL;
                this.precision = DEF_PRECISION;
                this.allowedChars = FM_DECIMAL;
                break;
        }
    }

    public void setAllowNegative(boolean value) {
        allowNegative = value;

        if (value) {
            negativeChars = "" + NEGATIVE;
        } else {
            negativeChars = BLANK;
        }
    }

    public boolean isAllowNegative() {
        return allowNegative;
    }

    public void setDocument(Document document) {
    }

    class JNumberFieldFilter extends PlainDocument {

        public JNumberFieldFilter() {
            super();
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            String text = getText(0, offset) + str + getText(offset, (getLength() - offset));

            if (str == null || text == null) {
                return;
            }

            for (int i = 0; i < str.length(); i++) {
                if ((allowedChars + negativeChars).indexOf(str.charAt(i)) == -1) {
                    return;
                }
            }

            int precisionLength = 0, dotLength = 0, minusLength = 0;
            int textLength = text.length();

            try {
                if (format == NUMERIC) {
                    if (!((text.equals(negativeChars)) && (text.length() == 1))) {
                        new Long(text);
                    }
                } else if (format == DECIMAL) {
                    if (!((text.equals(negativeChars)) && (text.length() == 1))) {
                        new Double(text);
                    }

                    int dotIndex = text.indexOf(DOT);
                    if (dotIndex != -1) {
                        dotLength = 1;
                        precisionLength = textLength - dotIndex - dotLength;

                        if (precisionLength > precision) {
//                            showErrorWin(ERROR_TEXT);
                            return;
                        }
                    }
                }
            } catch (Exception ex) {
                if (errorWindow != null && errorWindow.isVisible()) {
                    errorWindow.setVisible(false);
                }

                return;
            }

            if (text.startsWith("" + NEGATIVE)) {
                if (!allowNegative) {
//                    showErrorWin(ERROR_TEXT);
                    return;
                } else {
                    minusLength = 1;
                }
            }

            if (maxLength < (textLength - dotLength - precisionLength - minusLength)) {
//                showErrorWin(ERROR_TEXT);
                return;
            }

            super.insertString(offset, str, attr);
            if (errorWindow != null && errorWindow.isVisible()) {
                errorWindow.setVisible(false);
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
