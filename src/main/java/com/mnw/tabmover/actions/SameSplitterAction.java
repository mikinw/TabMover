package com.mnw.tabmover.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.impl.EditorTabbedContainer;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.tabs.JBTabs;
import com.intellij.ui.tabs.TabInfo;

public abstract class SameSplitterAction extends DumbAwareAction {

    protected JBTabs tabs;
    protected TabInfo selectedInfo;
    protected EditorTabbedContainer tabbedPane;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        EditorWindow editorWindow = EditorWindow.DATA_KEY.getData(anActionEvent.getDataContext());
        if (editorWindow == null) {
            return;
        }

        tabbedPane = editorWindow.getTabbedPane();
        tabs = tabbedPane.getTabs();
        selectedInfo = tabs.getSelectedInfo();
        if (selectedInfo == null) {
            return;
        }

        performAction();
    }

    public abstract void performAction();
}
