package com.mnw.tabmover;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.impl.EditorTabbedContainer;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.ui.tabs.JBTabs;
import com.intellij.ui.tabs.TabInfo;

/**
 * TODO description of this class is missing
 */
public class MoveTabToLastAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        final EditorWindow editorWindow = EditorWindow.DATA_KEY.getData(event.getDataContext());
        if (editorWindow == null) {
            return;
        }
        final EditorTabbedContainer tabbedPane = editorWindow.getTabbedPane();
        if (tabbedPane == null) {
            return;
        }
        final JBTabs tabs = tabbedPane.getTabs();
        final TabInfo selectedInfo = tabs.getSelectedInfo();
        if (selectedInfo == null) {
            return;
        }
        final int index = tabs.getIndexOf(selectedInfo);
        if (index >= tabbedPane.getTabCount() - 1) {
            return;
        }
        tabs.removeTab(selectedInfo);
        tabs.addTab(selectedInfo);
        tabs.select(selectedInfo, true);
    }
}
