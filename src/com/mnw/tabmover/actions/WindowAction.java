package com.mnw.tabmover.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.mnw.tabmover.OtherWindowStrategy;

public abstract class WindowAction extends AnAction {
    private final OtherWindowStrategy otherWindowStrategy;
    protected EditorWindow otherWindowPane;
    protected EditorWindow activeWindowPane;

    protected WindowAction(OtherWindowStrategy otherWindowStrategy) {
        this.otherWindowStrategy = otherWindowStrategy;
    }


    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = PlatformDataKeys.PROJECT.getData(event.getDataContext());
        if (project == null) {
            return;
        }
        activeWindowPane = EditorWindow.DATA_KEY.getData(event.getDataContext());
        final FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        if (activeWindowPane == null) {return;} // Action invoked when no files are open; do nothing

        otherWindowPane = otherWindowStrategy.getOtherWindow(fileEditorManagerEx, activeWindowPane);
        if (otherWindowPane == activeWindowPane) {return;} // Action invoked with one pane open; do nothing

        performAction();

        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                moveFocus(otherWindowPane);
            }
        });
    }

    private void moveFocus(final EditorWindow windowPane) {
//        windowPane.setSelectedEditor(windowPane.findFileComposite(activeFile), true);
//        final boolean b = windowPane.getOwner().requestFocus(false);
//        final boolean b1 = windowPane.getOwner().requestFocusInWindow();
        windowPane.requestFocus(true);
    }

    protected abstract void performAction();
}
