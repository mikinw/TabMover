package com.mnw.tabmover.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorComposite;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class MoveTabToNewWindow extends DumbAwareAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Project project = PlatformDataKeys.PROJECT.getData(anActionEvent.getDataContext());
        if (project == null) {
            return;
        }
        final FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        final EditorWindow activeWindowPane = EditorWindow.DATA_KEY.getData(anActionEvent.getDataContext());

        if (activeWindowPane == null) return; // Action invoked when no files are open; do nothing

        if (!(fileEditorManagerEx instanceof FileEditorManagerImpl)) return;

        final FileEditorManagerImpl fileEditorManagerImpl = (FileEditorManagerImpl) fileEditorManagerEx;
        final EditorComposite activeEditorTab = activeWindowPane.getSelectedComposite();
        if (activeEditorTab != null) {
            final VirtualFile activeFile = activeEditorTab.getFile();

            activeWindowPane.closeFile(activeFile, true, false);

            fileEditorManagerImpl.openFileInNewWindow(activeFile);
        }
    }
}
