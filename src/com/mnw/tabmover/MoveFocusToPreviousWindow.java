package com.mnw.tabmover;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;

/**
 * TODO description of this class is missing
 */
public class MoveFocusToPreviousWindow extends AnAction {


    private final PreviousWindowStrategy previousWindowStrategy = new PreviousWindowStrategy();

    public void actionPerformed(AnActionEvent event) {
        final Project project = PlatformDataKeys.PROJECT.getData(event.getDataContext());
        final EditorWindow activeWindowPane = EditorWindow.DATA_KEY.getData(event.getDataContext());
        if (activeWindowPane == null) return; // Action invoked when no files are open; do nothing

        final FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        final EditorWindow previousWindowPane = previousWindowStrategy.getPreviousWindow(fileEditorManagerEx, activeWindowPane);
        if (previousWindowPane == activeWindowPane) return; // Action invoked with one pane open; do nothing

        final Application application = ApplicationManager.getApplication();
        final TabMoverAppComponent appComponent = application.getComponent(TabMoverAppComponent.class);
        appComponent.moveFocusToWindowPane(previousWindowPane);

        System.out.println("moving focus prev");

    }

}
