package com.mnw.tabmover;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorTabbedContainer;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.tabs.JBTabs;
import com.intellij.ui.tabs.TabInfo;

/**
 * TODO description of this class is missing
 */
public class SortByTimestamp extends AnAction {

    public void actionPerformed(AnActionEvent event) {
        final EditorWindow editorWindow = EditorWindow.DATA_KEY.getData(event.getDataContext());
        if (editorWindow == null) return; // Action invoked when no files are open; do nothing

        final EditorWithProviderComposite[] editorTabs = editorWindow.getEditors();
//        System.out.println("timestamps: ");
        final Application application = ApplicationManager.getApplication();
        final TabMoverAppComponent appComponent = application.getComponent(TabMoverAppComponent.class);

        for (EditorWithProviderComposite editorTab : editorTabs) {
//            final long timestamp = editorTab.getInitialFileTimeStamp();
            editorTab.getFile();
            appComponent.getFileList();

            System.out.println(timestamp);
        }




        final EditorTabbedContainer tabbedPane = editorWindow.getTabbedPane();
        if (tabbedPane == null) {
            return;
        }
        final JBTabs tabs = tabbedPane.getTabs();
        final TabInfo selectedInfo = tabs.getTabAt(0)..getSelectedInfo().;
        if (selectedInfo == null) {
            return;
        }
        tabs.removeTab(selectedInfo);
        tabs.addTab(selectedInfo, 0);
        tabs.select(selectedInfo, true);


    }
}
