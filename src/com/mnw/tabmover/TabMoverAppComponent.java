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

/**
 * TODO description of this class is missing
 */
public class TabMoverAppComponent implements ApplicationComponent, EditorFactoryListener, Disposable, ProjectManagerListener {

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

    public void moveFocusToWindowPane(final EditorWindow windowPane) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                moveFocus(windowPane);
            }
        });
    }

    private void moveFocus(final EditorWindow windowPane) {
//        windowPane.setSelectedEditor(windowPane.findFileComposite(activeFile), true);
//        final boolean b = windowPane.getOwner().requestFocus(false);
//        final boolean b1 = windowPane.getOwner().requestFocusInWindow();
        windowPane.requestFocus(true);
    }

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        final Project project = event.getEditor().getProject();
//        FileEditorManagerEx.getInstanceEx(project).addFileEditorManagerListener(this);

//        final EditorWindow activeWindowPane = EditorWindow.DATA_KEY.getData(project);

//        WindowManagerImpl.getInstanceEx().
//        event.getEditor().;

        if (project == null) return;
        project.getMessageBus().connect(this).subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
            }

            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                fileMap.put(file, System.currentTimeMillis());

                System.out.println("fileOpen. content:");
                System.out.println(fileMap);
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                fileMap.remove(file);
            }
        });
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) {

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
                if (fileMap.containsKey(file)) return;
                fileMap.put(file, System.currentTimeMillis());

                System.out.println("fileOpened(). " + file);
                System.out.println("fileOpen. content:");
                System.out.println(fileMap);
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                fileMap.remove(file);
                System.out.println("fileClosed(). " + file);

            }
        });

    }

    @Override
    public boolean canCloseProject(Project project) {
        return false;
    }

    @Override
    public void projectClosed(Project project) {
        fileMap.clear();

    }

    @Override
    public void projectClosing(Project project) {

    }
}
