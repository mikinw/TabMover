package com.mnw.tabmover.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.mnw.tabmover.NextWindowStrategy;
import com.mnw.tabmover.PreviousWindowStrategy;
import com.mnw.tabmover.TabMoverAppComponent;

public class MoveTabToPreviousWindow extends WindowAction /*implements FileEditorManagerListener*/ {

    protected MoveTabToPreviousWindow() {
        super(new PreviousWindowStrategy());
    }

    @Override
    public void performAction() {
        final EditorWithProviderComposite activeEditorTab = activeWindowPane.getSelectedEditor();
        activeEditorTab.getInitialFileTimeStamp();
        final VirtualFile activeFile = activeEditorTab.getFile();
        otherWindowPane.getManager().openFileImpl2(otherWindowPane, activeFile, true);
        //previousWindowPane.getManager().addFileEditorManagerListener(this);
        activeWindowPane.closeFile(activeFile, true, false);
    }

    /*@Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {

    }*/
}
