package com.mnw.tabmover.actions;

import com.intellij.openapi.fileEditor.impl.EditorComposite;
import com.intellij.openapi.vfs.VirtualFile;
import com.mnw.tabmover.NextWindowStrategy;

public class MoveTabToNextWindow extends WindowAction {

    protected MoveTabToNextWindow() {
        super(new NextWindowStrategy());
    }

    @Override
    public void performAction() {
        final EditorComposite activeEditorTab = activeWindowPane.getSelectedComposite();
        if (activeEditorTab != null) {

            final VirtualFile activeFile = activeEditorTab.getFile();
            otherWindowPane.getManager().openFileImpl2(otherWindowPane, activeFile, true);
            activeWindowPane.closeFile(activeFile, true, false);
        }
    }

}
