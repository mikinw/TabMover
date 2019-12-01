package com.mnw.tabmover.actions;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.tabs.TabInfo;

public class TogglePinOnAllOpenTabsAction extends PinnedAction {


    @SuppressWarnings("BreakStatement")
    @Override
    protected void performAction() {
        boolean pinAllOn = false;
        final int tabCount = tabs.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            final TabInfo currentTab = tabs.getTabAt(i);
            if (!isTabPinned(currentTab)) {
                pinAllOn = true;
                break;
            }
        }
        for (int i = 0; i < tabCount; i++) {
            final TabInfo currentTab = tabs.getTabAt(i);
            setPinned(currentTab, pinAllOn);
        }
    }

    private void setPinned(TabInfo currentTab, boolean pinAllOn) {
        if (currentTab == null) {return;}
        final Object currentTabObject = currentTab.getObject();
        if (currentTabObject == null) {return;}
        if (!(currentTabObject instanceof VirtualFile)) {return;}
        final VirtualFile virtualFile = (VirtualFile) currentTabObject;
        editorWindow.setFilePinned(virtualFile, pinAllOn);

    }

}
