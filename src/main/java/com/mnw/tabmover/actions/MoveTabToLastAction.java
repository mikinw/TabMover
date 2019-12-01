package com.mnw.tabmover.actions;

public class MoveTabToLastAction extends SameSplitterAction {
    @Override
    public void performAction() {
        final int index = tabs.getIndexOf(selectedInfo);
        if (index >= tabbedPane.getTabCount() - 1) {
            return;
        }
        tabs.removeTab(selectedInfo);
        tabs.addTab(selectedInfo);
        tabs.select(selectedInfo, true);
    }
}
