package com.mnw.tabmover;

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;

public class NextWindowStrategy {
    public NextWindowStrategy() { }

    EditorWindow getNextWindow(final FileEditorManagerEx fileEditorManagerEx, final EditorWindow activeWindowPane) {
        final EditorWindow[] windows = fileEditorManagerEx.getWindows();

        int i = 0;
        for (; i < windows.length; i++) {
            if (windows[i] == activeWindowPane) {
                break;
            }
        }
        i++;
        if (i >= windows.length) {
            i = 0;
        }

        return windows[i];
    }
}
