package pro.kensait.berrybooks;

import pro.kensait.berrybooks.api.BerryBooksApiClient;
import pro.kensait.berrybooks.model.CustomerStats;
import pro.kensait.berrybooks.model.CustomerTO;
import pro.kensait.berrybooks.ui.CustomerEditDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Berry Books ??????Swing?????????
 */
public class BerryBooksSwingApp extends JFrame {
    private final BerryBooksApiClient apiClient;
    private JTable customerTable;
    private DefaultTableModel tableModel;

    public BerryBooksSwingApp(String apiUrl) {
        super("Berry Books ?????");
        this.apiClient = new BerryBooksApiClient(apiUrl);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        
        initComponents();
        loadCustomers();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // ???????
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        JLabel titleLabel = new JLabel("????", SwingConstants.CENTER);
        titleLabel.setFont(new Font("MS Gothic", Font.BOLD, 24));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // ?????????
        JButton refreshButton = new JButton("??");
        refreshButton.addActionListener(e -> loadCustomers());
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.add(refreshButton);
        titlePanel.add(refreshPanel, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);

        // ????
        String[] columnNames = {"??ID", "???", "???????", "????", "??", "????", "????", "??"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // ?????????
            }
        };

        customerTable = new JTable(tableModel);
        customerTable.setRowHeight(35);
        customerTable.setFont(new Font("MS Gothic", Font.PLAIN, 14));
        customerTable.getTableHeader().setFont(new Font("MS Gothic", Font.BOLD, 14));
        customerTable.getTableHeader().setReorderingAllowed(false);

        // ????
        customerTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // ??ID
        customerTable.getColumnModel().getColumn(1).setPreferredWidth(150); // ???
        customerTable.getColumnModel().getColumn(2).setPreferredWidth(200); // ???
        customerTable.getColumnModel().getColumn(3).setPreferredWidth(120); // ????
        customerTable.getColumnModel().getColumn(4).setPreferredWidth(300); // ??
        customerTable.getColumnModel().getColumn(5).setPreferredWidth(100); // ????
        customerTable.getColumnModel().getColumn(6).setPreferredWidth(100); // ????
        customerTable.getColumnModel().getColumn(7).setPreferredWidth(100); // ??

        // ?????????
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        customerTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        customerTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        customerTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        customerTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        // ??????????
        customerTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
        customerTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        scrollPane.setColumnHeaderView(customerTable.getTableHeader());
        
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadCustomers() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CustomerStats> customers = apiClient.getAllCustomers();
                    updateTable(customers);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        BerryBooksSwingApp.this,
                        "??????????????:\n" + ex.getMessage(),
                        "???",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    private void updateTable(List<CustomerStats> customers) {
        tableModel.setRowCount(0);
        for (CustomerStats customer : customers) {
            Object[] row = {
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getBirthDate().toString(),
                customer.getAddress(),
                customer.getOrderCount(),
                customer.getBookCount(),
                "??"
            };
            tableModel.addRow(row);
        }
    }

    private void editCustomer(int row) {
        Long customerId = (Long) tableModel.getValueAt(row, 0);
        String customerName = (String) tableModel.getValueAt(row, 1);
        String email = (String) tableModel.getValueAt(row, 2);
        String birthDate = (String) tableModel.getValueAt(row, 3);
        String address = (String) tableModel.getValueAt(row, 4);
        Long orderCount = (Long) tableModel.getValueAt(row, 5);
        Long bookCount = (Long) tableModel.getValueAt(row, 6);

        CustomerStats customer = new CustomerStats();
        customer.setCustomerId(customerId);
        customer.setCustomerName(customerName);
        customer.setEmail(email);
        customer.setBirthDate(java.time.LocalDate.parse(birthDate));
        customer.setAddress(address);
        customer.setOrderCount(orderCount);
        customer.setBookCount(bookCount);

        CustomerEditDialog dialog = new CustomerEditDialog(this, customer);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            // API???
            CustomerTO customerTO = new CustomerTO(
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getBirthDate().toString(),
                customer.getAddress()
            );

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        apiClient.updateCustomer(customerId, customerTO);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                JOptionPane.showMessageDialog(
                                    BerryBooksSwingApp.this,
                                    "????????????",
                                    "??",
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                                loadCustomers();
                            }
                        });
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                JOptionPane.showMessageDialog(
                                    BerryBooksSwingApp.this,
                                    "??????????????:\n" + ex.getMessage(),
                                    "???",
                                    JOptionPane.ERROR_MESSAGE
                                );
                            }
                        });
                    }
                }
            }).start();
        }
    }

    // ????????
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "??" : value.toString());
            return this;
        }
    }

    // ????????
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private int editingRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "??" : value.toString();
            button.setText(label);
            editingRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            editCustomer(editingRow);
            return label;
        }
    }

    public static void main(String[] args) {
        final String apiUrl = (args.length > 0) ? args[0] : "http://localhost:8080/berry-books-rest";
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                BerryBooksSwingApp app = new BerryBooksSwingApp(apiUrl);
                app.setVisible(true);
            }
        });
    }
}
