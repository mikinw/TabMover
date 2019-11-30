package com.mnw.tabmover.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorTabbedContainer;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.Condition;
import com.intellij.ui.ShowSplashAction;

public class ShowTabMoveActions extends AnAction {

    private static final boolean SHOW_NUMBERS = true;
    private static final boolean USE_ALPHA_AS_NUMBERS = true;
    private static final int MAX_COUNT = 20;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        DataContext context = anActionEvent.getDataContext();

        if (editor == null) {return;}

        final DefaultActionGroup actionGroup = (DefaultActionGroup) ActionManager.getInstance()
                .getAction("com.mnw.tabmover.group");
        int windowCount = getWindowCount(anActionEvent);
        final int tabsCount = getTabCount(anActionEvent);
        final AnAction[] actionGroupChildren = actionGroup.getChildren(anActionEvent);
        final int childrenCount = actionGroup.getChildrenCount();


        for (int i = 0; i < childrenCount; i++) {
            final Presentation templatePresentation = actionGroupChildren[i].getTemplatePresentation();
            if (actionGroupChildren[i] instanceof WindowAction
                    || actionGroupChildren[i] instanceof MoveTabToNextSplitter
                    || actionGroupChildren[i] instanceof MoveTabToPreviousSplitter) {
                templatePresentation.setEnabled(windowCount > 1);
            } else if (actionGroupChildren[i] instanceof ShowSplashAction) {
                templatePresentation.setEnabled(tabsCount > 1);
            }
/*            if (templatePresentation.getText().equals("Move Tab to Previous Splitter")
                || templatePresentation.getText().equals("Move Tab to Next Window/Splitter")
                || templatePresentation.getText().equals("Move Tab to Previous Window/Splitter")
                || templatePresentation.getText().equals("Focus Next Window/Splitter (same Project)")
                || templatePresentation.getText().equals("Focus Previous Window/Splitter (same Project)")
                || templatePresentation.getText().equals("Move Tab to Next Splitter")
                    ) {
            }

            if (templatePresentation.getText().equals("Move Tab to First")
                    || templatePresentation.getText().equals("Move Tab to Previous")
                    || templatePresentation.getText().equals("Move Tab to Next")
                    || templatePresentation.getText().equals("Move Tab to Last")
                    ) {
            }*/
        }

        JBPopupFactory.getInstance()
                .createActionGroupPopup("TabMover Actions",
                                        actionGroup,
                                        context,
                                        SHOW_NUMBERS,
                                        USE_ALPHA_AS_NUMBERS,
                                        false,
                                        null /*onDispose*/,
                                        MAX_COUNT,
                                        null)
                .showInBestPositionFor(editor);
    }

    private int getWindowCount(AnActionEvent anActionEvent) {
        final Project project = PlatformDataKeys.PROJECT.getData(anActionEvent.getDataContext());
        if (project != null) {
            final FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
            return fileEditorManagerEx.getWindows().length;
        }
        return 0;
    }

    private int getTabCount(AnActionEvent anActionEvent) {
        EditorWindow editorWindow = EditorWindow.DATA_KEY.getData(anActionEvent.getDataContext());
        if (editorWindow == null) {
            return -1;
        }

        final EditorTabbedContainer tabbedPane = editorWindow.getTabbedPane();
        if (tabbedPane == null) {
            return -1;
        }
        return tabbedPane.getTabs().getTabCount();
    }

    private static class AnActionCondition implements Condition<AnAction> {
        private final int windowCount;
        private final int tabsCount;

        public AnActionCondition(int windowCount, int tabsCount) {
            this.windowCount = windowCount;
            this.tabsCount = tabsCount;
        }

        @Override
        public boolean value(AnAction anAction) {
            if (anAction instanceof WindowAction) {
                return windowCount > 1;
            } else if (anAction instanceof ShowSplashAction) {
                return tabsCount > 1;
            }
            return true;

        }
    }
}
