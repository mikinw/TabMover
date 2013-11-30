package com.mnw.tabmover;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * TODO description of this class is missing
 */
public class MoveTabToPreviousWindow extends AnAction implements FileEditorManagerListener {

    private final PreviousWindowStrategy previousWindowStrategy = new PreviousWindowStrategy();

    public void actionPerformed(AnActionEvent event) {
        final Project project = PlatformDataKeys.PROJECT.getData(event.getDataContext());
        final EditorWindow activeWindowPane = EditorWindow.DATA_KEY.getData(event.getDataContext());
        if (activeWindowPane == null) return; // Action invoked when no files are open; do nothing

        final FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        final EditorWindow previousWindowPane = previousWindowStrategy.getPreviousWindow(fileEditorManagerEx, activeWindowPane);
        if (previousWindowPane == activeWindowPane) return; // Action invoked with one pane open; do nothing

        final EditorWithProviderComposite activeEditorTab = activeWindowPane.getSelectedEditor();
        activeEditorTab.getInitialFileTimeStamp();
        final VirtualFile activeFile = activeEditorTab.getFile();
        previousWindowPane.getManager().openFileImpl2(previousWindowPane, activeFile, true);
        previousWindowPane.getManager().addFileEditorManagerListener(this);
        activeWindowPane.closeFile(activeFile, true, false);

        final Application application = ApplicationManager.getApplication();
        final TabMoverAppComponent appComponent = application.getComponent(TabMoverAppComponent.class);
        appComponent.moveFocusToWindowPane(previousWindowPane);
    }

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {

    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {

    }
}
