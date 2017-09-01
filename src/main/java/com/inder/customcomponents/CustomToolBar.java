/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inder.customcomponents;

import javax.swing.JToolBar;

/**
 *
 * @author ISanhot
 */
public class CustomToolBar extends JToolBar {

    private CustomButton[] toolBarButtons;
    private int noOfButtons;

    public CustomToolBar() {
        this(5);
    }

    public CustomToolBar(int noOfButtons) {
        setNoOfButtons(noOfButtons);
        toolBarButtons = new CustomButton[noOfButtons];
        for (int no = 0; no < noOfButtons; no++) {
            CustomButton cb = new CustomButton();
            cb.setText("cb " + no);
            cb.setToolTip("cb " + no);
            toolBarButtons[no] = cb;
        }
        setToolBarButtons(toolBarButtons);
    }

    /**
     * @return the toolBarButtons
     */
    public CustomButton[] getToolBarButtons() {
        return toolBarButtons;
    }

    /**
     * @param toolBarButtons the toolBarButtons to set
     */
    private void setToolBarButtons(CustomButton[] toolBarButtons) {
        this.toolBarButtons = toolBarButtons;
        for (int no = 0; no < getNoOfButtons(); no++) {
            add(toolBarButtons[no]);
        }
    }

    /**
     * @return the noOfButtons
     */
    public int getNoOfButtons() {
        return noOfButtons;
    }

    /**
     * @param noOfButtons the noOfButtons to set
     */
    private void setNoOfButtons(int noOfButtons) {
        this.noOfButtons = noOfButtons;
    }

}
