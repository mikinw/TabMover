package com.mnw.tabmover.actions;

import com.intellij.openapi.fileEditor.impl.EditorComposite;
import com.intellij.openapi.vfs.VirtualFile;
import com.mnw.tabmover.PreviousWindowStrategy;

public class MoveTabToPreviousWindow extends WindowAction /*implements FileEditorManagerListener*/ {

    protected MoveTabToPreviousWindow() {
        super(new PreviousWindowStrategy());
    }

    @Override
    public void performAction() {
        final EditorComposite activeEditorTab = activeWindowPane.getSelectedComposite();
        if (activeEditorTab != null) {
            final VirtualFile activeFile = activeEditorTab.getFile();
            otherWindowPane.getManager().openFileImpl2(otherWindowPane, activeFile, true);
            //previousWindowPane.getManager().addFileEditorManagerListener(this);
            activeWindowPane.closeFile(activeFile, true, false);
        }
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
