package com.mnw.tabmover.actions;

public class MoveTabUpAction extends SameSplitterAction {
    @Override
    public void performAction() {
        final int index = tabs.getIndexOf(selectedInfo);
        if (index == 0) {
            return;
        }
        tabs.removeTab(selectedInfo);
        tabs.addTab(selectedInfo, index - 1);
        tabs.select(selectedInfo, true);
    }
}
