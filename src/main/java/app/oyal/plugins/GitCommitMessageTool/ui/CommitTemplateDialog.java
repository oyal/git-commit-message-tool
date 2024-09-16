package app.oyal.plugins.GitCommitMessageTool.ui;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBTextField;
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
        setTitle("提交");
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
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Label constraints
        gbc.weightx = 0.2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("更改类型"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("变更范围"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("简短描述"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("详情描述"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("重大变更"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("关闭问题"), gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Skip CI"), gbc);

        // Input field constraints
        gbc.weightx = 0.8;
        gbc.gridx = 1;
        gbc.gridy = 0;
        typeComboBox = new ComboBox<>(new String[]{
                "feat - 新功能",
                "fix - 修复错误",
                "docs - 仅文档更改",
                "style - 不影响代码含义的更改（空格、格式、缺少分号等）",
                "refactor - 既不修复错误也不添加功能的代码更改",
                "perf - 提高性能的代码更改",
                "test - 添加缺失的测试或更正现有的测试",
                "build - 更改构建系统或外部依赖项（例如：gulp、broccoli、npm）",
                "ci - 更改持续集成配置文件和脚本（例如：Travis、Circle、BrowserStack、SauceLabs）",
                "chore - 构建过程或辅助工具和库（如文档生成）的更改",
                "revert - 撤销先前的提交"
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
        System.out.println("scope: " + scope);
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