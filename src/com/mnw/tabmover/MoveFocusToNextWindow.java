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
public class MoveFocusToNextWindow extends AnAction {

    private final NextWindowStrategy nextWindowStrategy = new NextWindowStrategy();

    public void actionPerformed(AnActionEvent event) {
        final Project project = PlatformDataKeys.PROJECT.getData(event.getDataContext());
        final FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        final EditorWindow activeWindowPane = EditorWindow.DATA_KEY.getData(event.getDataContext());

        if (activeWindowPane == null) return; // Action invoked when no files are open; do nothing

        final EditorWindow nextWindowPane = nextWindowStrategy.getNextWindow(fileEditorManagerEx, activeWindowPane);

        if (nextWindowPane == activeWindowPane) return; // Action invoked with one pane open; do nothing

        final Application application = ApplicationManager.getApplication();
        final TabMoverAppComponent appComponent = application.getComponent(TabMoverAppComponent.class);
        appComponent.moveFocusToWindowPane(nextWindowPane);

        System.out.println("moving focus");
    }


}
