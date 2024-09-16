package app.oyal.plugins.GitCommitMessageTool.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.VcsDataKeys;
import org.jetbrains.annotations.NotNull;
import app.oyal.plugins.GitCommitMessageTool.ui.CommitTemplateDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommitTemplateAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        CommitMessageI commitMessageComponent = e.getData(VcsDataKeys.COMMIT_MESSAGE_CONTROL);
        Document commitMessageDocument = e.getData(VcsDataKeys.COMMIT_MESSAGE_DOCUMENT);
        String existingCommitMessage = commitMessageDocument != null ? commitMessageDocument.getText() : "";

        CommitTemplateDialog dialog = new CommitTemplateDialog();
        if (!existingCommitMessage.isEmpty()) {
            CommitMessageData data = parseCommitMessage(existingCommitMessage);
            dialog.setType(data.type);
            dialog.setScope(data.scope);
            dialog.setShortDescription(data.shortDescription);
            dialog.setDetailedDescription(data.detailedDescription);
            dialog.setBreakingChange(data.breakingChange);
            dialog.setClosedIssues(data.closedIssues);
            dialog.setSkipCI(data.skipCI);
        }

        if (dialog.showAndGet()) {
            String typeWithDescription = dialog.getType();
            String type = typeWithDescription.split(" - ")[0];
            String scope = dialog.getScope();
            String shortDescription = dialog.getShortDescription();
            String detailedDescription = dialog.getDetailedDescription();
            String breakingChange = dialog.getBreakingChange();
            String closedIssues = dialog.getClosedIssues();
            boolean skipCI = dialog.isSkipCI();

            String commitMessage = formatCommitMessage(type, scope, shortDescription, detailedDescription, breakingChange, closedIssues, skipCI);

            if (commitMessageComponent != null) {
                commitMessageComponent.setCommitMessage(commitMessage);
            }
        }
    }

    private CommitMessageData parseCommitMessage(String commitMessage) {
        Pattern pattern = Pattern.compile("^(\\w+)(?:\\(([^)]+)\\))?: (.+?)(?:\\n\\n(.+?))?(?:\\n\\nBREAKING CHANGE: (.+?))?(?:\\n\\nCloses: (.+?))?(?:\\n\\n\\[skip ci])?$", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(commitMessage);

        if (matcher.find()) {
            String type = matcher.group(1);
            String scope = matcher.group(2) != null ? matcher.group(2) : "";
            String shortDescription = matcher.group(3);
            String detailedDescription = matcher.group(4) != null ? matcher.group(4) : "";
            String breakingChange = matcher.group(5) != null ? matcher.group(5) : "";
            String closedIssues = matcher.group(6) != null ? matcher.group(6) : "";
            boolean skipCI = commitMessage.contains("[skip ci]");

            return new CommitMessageData(type, scope, shortDescription, detailedDescription, breakingChange, closedIssues, skipCI);
        }

        return new CommitMessageData("", "", "", "", "", "", false);
    }

    private static class CommitMessageData {
        String type;
        String scope;
        String shortDescription;
        String detailedDescription;
        String breakingChange;
        String closedIssues;
        boolean skipCI;

        CommitMessageData(String type, String scope, String shortDescription, String detailedDescription, String breakingChange, String closedIssues, boolean skipCI) {
            this.type = type;
            this.scope = scope;
            this.shortDescription = shortDescription;
            this.detailedDescription = detailedDescription;
            this.breakingChange = breakingChange;
            this.closedIssues = closedIssues;
            this.skipCI = skipCI;
        }
    }

    private String formatCommitMessage(String type, String scope, String shortDescription, String detailedDescription, String breakingChange, String closedIssues, boolean skipCI) {
        StringBuilder commitMessage = new StringBuilder();
        commitMessage.append(type);
        if (!scope.isEmpty()) {
            commitMessage.append("(").append(scope).append(")");
        }
        commitMessage.append(": ").append(shortDescription);
        if (!detailedDescription.isEmpty()) {
            commitMessage.append("\n\n").append(detailedDescription);
        }
        if (!breakingChange.isEmpty()) {
            commitMessage.append("\n\n").append("BREAKING CHANGE: ").append(breakingChange);
        }
        if (!closedIssues.isEmpty()) {
            commitMessage.append("\n\n").append("Closes: ").append(closedIssues);
        }
        if (skipCI) {
            commitMessage.append("\n\n").append("[skip ci]").append("\n");
        }
        return commitMessage.toString();
    }
}