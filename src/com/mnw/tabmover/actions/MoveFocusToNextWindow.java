package com.mnw.tabmover.actions;

import com.mnw.tabmover.NextWindowStrategy;

public class MoveFocusToNextWindow extends WindowAction {


    protected MoveFocusToNextWindow() {
        super(new NextWindowStrategy());
    }

    @Override
    public void performAction() {}


}
