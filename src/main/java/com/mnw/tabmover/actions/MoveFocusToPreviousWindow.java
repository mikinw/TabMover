package com.mnw.tabmover.actions;

import com.mnw.tabmover.PreviousWindowStrategy;

public class MoveFocusToPreviousWindow extends WindowAction {


    protected MoveFocusToPreviousWindow() {
        super(new PreviousWindowStrategy());
    }

    @Override
    public void performAction() {  }

}
