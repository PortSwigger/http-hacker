package httpraider.view.menuBars;

import httpraider.view.components.SwitchButton;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProxyBar extends JPanel {
    private final JTextField domainNameField;
    private final JTextArea descriptionArea;
    private final JButton parsingButton;
    private final SwitchButton showInStreamsToggle;
    private final JButton exportButton;

    public ProxyBar() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(300, 0));
        setBackground(new Color(0xF5F5F5));
        setBorder(new MatteBorder(0, 1, 0, 0, Color.GRAY));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Domain Name
        JLabel domainLabel = new JLabel("Domain Name:");
        domainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(domainLabel);

        domainNameField = new JTextField();
        domainNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, domainNameField.getPreferredSize().height));
        domainNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(domainNameField);

        content.add(Box.createVerticalStrut(10));

        // Description
        JLabel descLabel = new JLabel("Description:");
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(descLabel);

        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(descScroll);

        content.add(Box.createVerticalStrut(10));

        // Buttons
        parsingButton = new JButton("HTTP Parser");
        parsingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(parsingButton);

        content.add(Box.createVerticalStrut(20)); // Increased gap for visual separation

        // Toggle
        showInStreamsToggle = new SwitchButton("Show in streams");
        showInStreamsToggle.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(showInStreamsToggle);

        add(content, BorderLayout.NORTH);
        
        // Export panel at the bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        exportButton = new JButton("Export Proxy");
        
        bottomPanel.add(exportButton);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // getters & setters
    public void setShowInStreamsEnabled(boolean enabled){
        showInStreamsToggle.setSelected(enabled);
    }

    public boolean isShowInStreamsEnabled(){
        return showInStreamsToggle.isSelected();
    }

    public String getDomainName() {
        return domainNameField.getText();
    }

    public void setDomainName(String name) {
        domainNameField.setText(name);
    }

    public String getDescription() {
        return descriptionArea.getText();
    }

    public void setDescription(String desc) {
        descriptionArea.setText(desc);
    }

    // listener registration

    public void addDomainNameListener(DocumentListener l) {
        domainNameField.getDocument().addDocumentListener(l);
    }

    public void addDescriptionListener(DocumentListener l) {
        descriptionArea.getDocument().addDocumentListener(l);
    }

    public void addParsingCodeListener(ActionListener l) {
        parsingButton.addActionListener(l);
    }

    public void addShowInStreamsListener(ActionListener l) {
        showInStreamsToggle.addActionListener(l);
    }
    
    public void addExportListener(ActionListener l) {
        exportButton.addActionListener(l);
    }
}
