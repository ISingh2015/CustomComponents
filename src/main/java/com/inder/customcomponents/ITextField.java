package com.inder.customcomponents;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.text.*;

public class ITextField extends JTextField {

    private int maxLength = 10, fontSize = 12;
    protected PlainDocument fieldFilter;
    private boolean upperCase;
    private boolean allowSpecialChars, allowEmailSymbol, allowDot, allowSpaceSymbol = true;
    private Shape shape;

    public ITextField() {
        this(10);
    }

    public ITextField(boolean allowSpecialChars, boolean allowEmailSymbol, boolean allowDot) {
        this(10);
        this.allowSpecialChars = allowSpecialChars;
        this.allowEmailSymbol = allowEmailSymbol;
        this.allowDot = allowDot;
    }

    public ITextField(int maxlen, boolean allowSpecialChars, boolean allowEmailSymbol, boolean allowDot) {
        this(maxlen);
        this.allowSpecialChars = allowSpecialChars;
        this.allowEmailSymbol = allowEmailSymbol;
        this.allowDot = allowDot;
    }

    public ITextField(int maxLen) {
        setMaxLength(maxLen);
        setFont(new Font("Arial", Font.BOLD, getFontSize()));
        fieldFilter = new JFieldFilter();
        super.setDocument(fieldFilter);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ITextField.this.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                int key = evt.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    transferFocus();
                }
            }
        });
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

    /**
     * @return the upperCase
     */
    public boolean isUpperCase() {
        return upperCase;
    }

    /**
     * @param upperCase the upperCase to set
     */
    public void setUpperCase(boolean upperCase) {
        this.upperCase = upperCase;
    }

    /**
     * @return the allowSpecialChars
     */
    public boolean isAllowSpecialChars() {
        return allowSpecialChars;
    }

    /**
     * @param allowSpecialChars the allowSpecialChars to set
     */
    public void setAllowSpecialChars(boolean allowSpecialChars) {
        this.allowSpecialChars = allowSpecialChars;
    }

    /**
     * @return the allowEmailSymbol
     */
    public boolean isAllowEmailSymbol() {
//        System.out.println(allowEmailSymbol);
        return allowEmailSymbol;
    }

    /**
     * @param allowEmailSymbol the allowEmailSymbol to set
     */
    public void setAllowEmailSymbol(boolean allowEmailSymbol) {
        this.allowEmailSymbol = allowEmailSymbol;
    }

    /**
     * @return the allowDot
     */
    public boolean isAllowDot() {
        return allowDot;
    }

    /**
     * @param allowDot the allowDot to set
     */
    public void setAllowDot(boolean allowDot) {
        this.allowDot = allowDot;
    }

    /**
     * @return the allowSpaceSymbol
     */
    public boolean isAllowSpaceSymbol() {
        return allowSpaceSymbol;
    }

    /**
     * @param allowSpaceSymbol the allowSpaceSymbol to set
     */
    public void setAllowSpaceSymbol(boolean allowSpaceSymbol) {
        this.allowSpaceSymbol = allowSpaceSymbol;
    }

    /**
     * @return the fontSize
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    class JFieldFilter extends PlainDocument {

        public JFieldFilter() {
            super();
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            String text = getText(0, offset) + str + getText(offset, (getLength() - offset));
            int textLength = text.length();

            if (textLength > maxLength) {
                return;
            }
            if (isUpperCase()) {
                str = str.toUpperCase();
            }
            if (!isAllowSpecialChars()) {
                str = str.replaceAll("[|~!#$%^&*()_+}{:;><?,`*/'/=//-]", "").replace("\"", "").replace("[", "").replace("]", "").replace("\\", "");
            }
            if (!isAllowEmailSymbol()) {
                str = str.replaceAll("@", "");
            }
            if (!isAllowDot()) {
                str = str.replaceAll("\\.", "");
            }
            if (!isAllowSpaceSymbol()) {
                str = str.replaceAll("\\ ", "");
            }

            super.insertString(offset, str, attr);
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
