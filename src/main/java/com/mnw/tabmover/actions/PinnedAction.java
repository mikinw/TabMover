package com.mnw.tabmover.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.impl.EditorTabbedContainer;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.tabs.JBTabs;
import com.intellij.ui.tabs.TabInfo;

public abstract class PinnedAction extends DumbAwareAction {
    protected EditorWindow editorWindow;
    protected JBTabs tabs;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        editorWindow = EditorWindow.DATA_KEY.getData(anActionEvent.getDataContext());
        if (editorWindow == null) {
            return;
        }
        final EditorTabbedContainer tabbedPane = editorWindow.getTabbedPane();
        tabs = tabbedPane.getTabs();


        performAction();
    }

    protected abstract void performAction();

    protected boolean isTabPinned(TabInfo currentTab) {
        if (currentTab == null) {return false;}
        final Object currentTabObject = currentTab.getObject();
        if (currentTabObject == null) {return false;}
        if (!(currentTabObject instanceof VirtualFile)) {return false;}
        final VirtualFile virtualFile = (VirtualFile) currentTabObject;
        return editorWindow.isFilePinned(virtualFile);
    }
}
