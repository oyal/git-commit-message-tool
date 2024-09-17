package app.oyal.plugins.gitcommitmessagetool.ui;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class CommitTemplateDialog extends DialogWrapper {
    private ComboBox<String> typeComboBox;
    private JBTextField scopeField;
    private JBTextField shortDescriptionField;
    private JBTextArea detailedDescriptionArea;
    private JBTextArea breakingChangeCheckBox;
    private JBTextField closedIssuesField;
    private JCheckBox skipCICheckBox;

    public CommitTemplateDialog() {
        super(true);
        setTitle("Commit");
        setSize(750, 520);
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = JBUI.insets(5);

        gbc.weightx = 0.2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Change Type"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Scope"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Short Description"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Detailed Description"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Breaking Change"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Closed Issues"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Skip CI"), gbc);

        gbc.weightx = 0.8;
        gbc.gridx = 1;
        gbc.gridy = 0;
        typeComboBox = new ComboBox<>(new String[]{
                "feat - A new feature",
                "fix - A bug fix",
                "docs - Documentation only changes",
                "style - Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)",
                "refactor - A code change that neither fixes a bug nor adds a feature",
                "perf - A code change that improves performance",
                "test - Adding missing tests or correcting existing tests",
                "build - Changes that affect the build system or external dependencies (example scopes: gulp, broccoli, npm)",
                "ci - Changes to our CI configuration files and scripts (example scopes: Travis, Circle, BrowserStack, SauceLabs)",
                "chore - Other changes that don't modify src or test files",
                "revert - Reverts a previous commit"
        });
        formPanel.add(typeComboBox, gbc);
        gbc.gridy++;
        scopeField = new JBTextField();
        formPanel.add(scopeField, gbc);
        gbc.gridy++;
        shortDescriptionField = new JBTextField();
        formPanel.add(shortDescriptionField, gbc);
        gbc.gridy++;
        detailedDescriptionArea = new JBTextArea(7, 20);
        formPanel.add(new JScrollPane(detailedDescriptionArea), gbc);
        gbc.gridy++;
        breakingChangeCheckBox = new JBTextArea(3, 20);
        formPanel.add(new JScrollPane(breakingChangeCheckBox), gbc);
        gbc.gridy++;
        closedIssuesField = new JBTextField();
        formPanel.add(closedIssuesField, gbc);
        gbc.gridy++;
        skipCICheckBox = new JCheckBox();
        formPanel.add(skipCICheckBox, gbc);

        dialogPanel.add(formPanel, BorderLayout.CENTER);

        return dialogPanel;
    }

    public String getType() {
        return (String) typeComboBox.getSelectedItem();
    }

    public void setType(String type) {
        for (int i = 0; i < typeComboBox.getItemCount(); i++) {
            if (typeComboBox.getItemAt(i).startsWith(type)) {
                typeComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public String getScope() {
        return scopeField.getText();
    }

    public void setScope(String scope) {
        scopeField.setText(scope);
    }

    public String getShortDescription() {
        return shortDescriptionField.getText();
    }

    public void setShortDescription(String shortDescription) {
        shortDescriptionField.setText(shortDescription);
    }

    public String getDetailedDescription() {
        return detailedDescriptionArea.getText();
    }

    public void setDetailedDescription(String detailedDescription) {
        detailedDescriptionArea.setText(detailedDescription);
    }

    public String getBreakingChange() {
        return breakingChangeCheckBox.getText();
    }

    public void setBreakingChange(String breakingChange) {
        breakingChangeCheckBox.setText(breakingChange);
    }

    public String getClosedIssues() {
        return closedIssuesField.getText();
    }

    public void setClosedIssues(String closedIssues) {
        closedIssuesField.setText(closedIssues);
    }

    public boolean isSkipCI() {
        return skipCICheckBox.isSelected();
    }

    public void setSkipCI(boolean skipCI) {
        skipCICheckBox.setSelected(skipCI);
    }
}