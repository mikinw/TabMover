package com.mnw.tabmover;

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;

public class PreviousWindowStrategy implements OtherWindowStrategy {
    public PreviousWindowStrategy() { }

    @Override
    public EditorWindow getOtherWindow(final FileEditorManagerEx fileEditorManagerEx,
                                       final EditorWindow activeWindowPane) {
        final EditorWindow[] windows = fileEditorManagerEx.getWindows();

        int i = 0;
        final int length = windows.length;
        for (; i < length; i++) {
            if (windows[i] == activeWindowPane) {
                break;
            }
        }
        i--;
        if (i < 0) {
            i = length - 1;
        }

        return windows[i];
    }
}
