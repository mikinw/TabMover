package com.mnw.tabmover.actions;

public class MoveTabToFirstAction extends SameSplitterAction {
    @Override
    public void performAction() {
        tabs.removeTab(selectedInfo);
        tabs.addTab(selectedInfo, 0);
        tabs.select(selectedInfo, true);
    }
}
