package com.mnw.tabmover;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.impl.WindowManagerImpl;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TabMoverAppComponent implements ApplicationComponent, Disposable, ProjectManagerListener {

    private Map<VirtualFile, Long> fileMap;

    public TabMoverAppComponent() {
        ProjectManager.getInstance().addProjectManagerListener(this);
//        EditorFactory.getInstance().addEditorFactoryListener(this);
        fileMap = new HashMap<VirtualFile, Long>();
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "TabMover";
    }


    @Override
    public void dispose() {

    }

    @Override
    public void projectOpened(Project project) {
        project.getMessageBus().connect(this).subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
            }

            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                if (fileMap.containsKey(file)) {
                    return;
                }
                fileMap.put(file, System.currentTimeMillis());

                //System.out.println("fileOpened(). " + file);
                //System.out.println("fileOpen. content:");
                //System.out.println(fileMap);
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                fileMap.remove(file);
                //System.out.println("fileClosed(). " + file);

            }
        });

    }

    @Override
    public boolean canCloseProject(Project project) {
        return true;
    }

    @Override
    public void projectClosed(Project project) {

    }

    @Override
    public void projectClosing(Project project) {
        fileMap.clear();

    }
}
