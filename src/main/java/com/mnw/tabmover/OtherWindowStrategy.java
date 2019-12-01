package com.mnw.tabmover;

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;

public interface OtherWindowStrategy {
    EditorWindow getOtherWindow(FileEditorManagerEx fileEditorManagerEx, EditorWindow activeWindowPane);
}
