package com.mnw.tabmover.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorComposite;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class MoveTabToNextSplitter extends DumbAwareAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = PlatformDataKeys.PROJECT.getData(event.getDataContext());
        final FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(project);
        final EditorWindow activeWindowPane = EditorWindow.DATA_KEY.getData(event.getDataContext());

        if (activeWindowPane == null) return; // Action invoked when no files are open; do nothing

        final EditorWindow nextWindowPane = fileEditorManager.getNextWindow(activeWindowPane);

        if (nextWindowPane == activeWindowPane) return; // Action invoked with one pane open; do nothing

        final EditorComposite activeEditorTab = activeWindowPane.getSelectedComposite();
        if (activeEditorTab != null) {
            final VirtualFile activeFile = activeEditorTab.getFile();

            nextWindowPane.getManager().openFileImpl2(nextWindowPane, activeFile, true);

            activeWindowPane.closeFile(activeFile, true, false);
        }
    }
}
