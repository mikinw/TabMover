package com.mnw.tabmover;

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;

public class NextWindowStrategy implements OtherWindowStrategy {

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
        i++;
        if (i >= length) {
            i = 0;
        }

        return windows[i];
    }
}
