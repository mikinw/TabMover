package com.mnw.tabmover;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.impl.EditorTabbedContainer;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.tabs.JBTabs;
import com.intellij.ui.tabs.TabInfo;

/**
 * TODO description of this class is missing
 */
public class MovePinnedTabsToFirst extends AnAction {

    private EditorWindow mEditorWindow;

    public void actionPerformed(AnActionEvent event) {
        mEditorWindow = EditorWindow.DATA_KEY.getData(event.getDataContext());
        if (mEditorWindow == null) {
            return;
        }
        final EditorTabbedContainer tabbedPane = mEditorWindow.getTabbedPane();
        if (tabbedPane == null) {
            return;
        }
        final JBTabs tabs = tabbedPane.getTabs();


        final TabInfo selectedInfo = tabs.getSelectedInfo();
        final int tabCount = tabs.getTabCount();
        int lastPinnedPosition = 0;
        for (int i = 0; i < tabCount; i++) {
            final TabInfo currentTab = tabs.getTabAt(i);
            if (isTabPinned(currentTab)) {
                if (i > lastPinnedPosition) {
                    movePinnedForward(tabs, lastPinnedPosition, currentTab);
                }
                lastPinnedPosition++;
            }
        }
        if (selectedInfo != null) {
            tabs.select(selectedInfo, false);
        }
    }

    private void movePinnedForward(final JBTabs tabs, final int lastPinnedPosition, final TabInfo currentTab) {
        tabs.removeTab(currentTab);
        tabs.addTab(currentTab, lastPinnedPosition);
    }

    private boolean isTabPinned(TabInfo currentTab) {
        if (currentTab == null) return false;
        final Object currentTabObject = currentTab.getObject();
        if (currentTabObject == null) return false;
        if (!(currentTabObject instanceof VirtualFile)) return false;
        final VirtualFile virtualFile = (VirtualFile) currentTabObject;
        return mEditorWindow.isFilePinned(virtualFile);
    }
}
