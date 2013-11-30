package com.mnw.tabmover;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * TODO description of this class is missing
 */
public class MoveTabToNextSplitter extends AnAction {

    public void actionPerformed(AnActionEvent event) {
        final Project project = PlatformDataKeys.PROJECT.getData(event.getDataContext());
        final FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(project);
        final EditorWindow activeWindowPane = EditorWindow.DATA_KEY.getData(event.getDataContext());

        if (activeWindowPane == null) return; // Action invoked when no files are open; do nothing

        final EditorWindow nextWindowPane = fileEditorManager.getNextWindow(activeWindowPane);

        if (nextWindowPane == activeWindowPane) return; // Action invoked with one pane open; do nothing

        final EditorWithProviderComposite activeEditorTab = activeWindowPane.getSelectedEditor();
        final VirtualFile activeFile = activeEditorTab.getFile();

        nextWindowPane.getManager().openFileImpl2(nextWindowPane, activeFile, true);

        activeWindowPane.closeFile(activeFile, true, false);
    }
}
