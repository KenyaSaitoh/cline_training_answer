package pro.kensait.berrybooks.ui;

import pro.kensait.berrybooks.model.CustomerStats;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * ???????????
 */
public class CustomerEditDialog extends JDialog {
    private final CustomerStats customer;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField birthDateField;
    private JTextField addressField;
    private boolean confirmed = false;

    public CustomerEditDialog(Frame parent, CustomerStats customer) {
        super(parent, "??????", true);
        this.customer = customer;
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 350);

        // ???????
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ??ID????????
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("??ID:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField idField = new JTextField(customer.getCustomerId().toString());
        idField.setEditable(false);
        idField.setBackground(Color.LIGHT_GRAY);
        formPanel.add(idField, gbc);

        // ???
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("???: *"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        nameField = new JTextField(customer.getCustomerName());
        formPanel.add(nameField, gbc);

        // ???????
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("???????: *"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        emailField = new JTextField(customer.getEmail());
        formPanel.add(emailField, gbc);

        // ????
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("????: *"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        birthDateField = new JTextField(customer.getBirthDate().toString());
        formPanel.add(birthDateField, gbc);

        // ?????????
        gbc.gridx = 1;
        gbc.gridy = 4;
        JLabel dateFormatLabel = new JLabel("(??: yyyy-MM-dd)");
        dateFormatLabel.setFont(new Font(dateFormatLabel.getFont().getName(), Font.ITALIC, 10));
        dateFormatLabel.setForeground(Color.GRAY);
        formPanel.add(dateFormatLabel, gbc);

        // ??
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        formPanel.add(new JLabel("??: *"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        addressField = new JTextField(customer.getAddress());
        formPanel.add(addressField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ??????
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton okButton = new JButton("??");
        okButton.addActionListener(e -> onOk());

        JButton cancelButton = new JButton("?????");
        cancelButton.addActionListener(e -> onCancel());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(okButton);
    }

    private void onOk() {
        // ???????
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String birthDateStr = birthDateField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "?????????????", 
                "?????", 
                JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "?????????????????", 
                "?????", 
                JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return;
        }

        // ?????????????
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this, 
                "????????????????????", 
                "?????", 
                JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return;
        }

        if (birthDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "??????????????", 
                "?????", 
                JOptionPane.ERROR_MESSAGE);
            birthDateField.requestFocus();
            return;
        }

        // ???????????
        try {
            LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, 
                "?????yyyy-MM-dd????????????\n?: 1990-01-15", 
                "?????", 
                JOptionPane.ERROR_MESSAGE);
            birthDateField.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "????????????", 
                "?????", 
                JOptionPane.ERROR_MESSAGE);
            addressField.requestFocus();
            return;
        }

        // ???????
        customer.setCustomerName(name);
        customer.setEmail(email);
        customer.setBirthDate(LocalDate.parse(birthDateStr));
        customer.setAddress(address);

        confirmed = true;
        dispose();
    }

    private void onCancel() {
        confirmed = false;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
