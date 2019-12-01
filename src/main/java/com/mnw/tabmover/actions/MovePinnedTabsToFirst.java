package com.mnw.tabmover.actions;

import com.intellij.ui.tabs.TabInfo;

public class MovePinnedTabsToFirst extends PinnedAction {

    @Override
    public void performAction() {
        final TabInfo selectedInfo = tabs.getSelectedInfo();
        final int tabCount = tabs.getTabCount();
        int lastPinnedPosition = 0;
        for (int i = 0; i < tabCount; i++) {
            final TabInfo currentTab = tabs.getTabAt(i);
            if (isTabPinned(currentTab)) {
                if (i > lastPinnedPosition) {
                    movePinnedForward(lastPinnedPosition, currentTab);
                }
                lastPinnedPosition++;
            }
        }
        if (selectedInfo != null) {
            tabs.select(selectedInfo, false);
        }
    }

    private void movePinnedForward(final int lastPinnedPosition, final TabInfo currentTab) {
        tabs.removeTab(currentTab);
        tabs.addTab(currentTab, lastPinnedPosition);
    }

}
