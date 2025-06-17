import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUISystem {
    private JFrame frame;
    private JTable menuTable;
    private JTable ordersTable;
    private DefaultTableModel menuModel;
    private DefaultTableModel ordersModel;
    private Admin admin;
    private JPanel cards;
    private CardLayout cardLayout;

    public GUISystem(Admin admin) {
        this.admin = admin;
        initialize();
    }

    private void initialize() {

        frame = new JFrame("Byte ME! - Display System");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);


        JPanel navigationPanel = new JPanel();
        JButton menuButton = new JButton("View Menu");
        JButton ordersButton = new JButton("View Orders");
        navigationPanel.add(menuButton);
        navigationPanel.add(ordersButton);

        JButton refreshButton = new JButton("Refresh Data");
        navigationPanel.add(refreshButton);

        cards = new JPanel();
        cardLayout = new CardLayout();
        cards.setLayout(cardLayout);

        setupMenuTable();
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        cards.add(menuPanel, "MENU");

        setupOrdersTable();
        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        cards.add(ordersPanel, "ORDERS");


        menuButton.addActionListener(e -> {
            refreshMenu();
            cardLayout.show(cards, "MENU");
        });

        ordersButton.addActionListener(e -> {
            refreshOrders();
            cardLayout.show(cards, "ORDERS");
        });

        refreshButton.addActionListener(e -> {
            if (cards.isShowing()) {
                refreshMenu();
                refreshOrders();
            }
        });


        frame.setLayout(new BorderLayout());
        frame.add(navigationPanel, BorderLayout.NORTH);
        frame.add(cards, BorderLayout.CENTER);
    }

    private void setupMenuTable() {
        String[] columns = {"ID", "Name", "Category", "Price", "Available"};
        menuModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        menuTable = new JTable(menuModel);


        menuTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        menuTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Name
        menuTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Category
        menuTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Price
        menuTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Available

        refreshMenu();
    }

    private void setupOrdersTable() {
        String[] columns = {"Order ID", "Status", "Details"};
        ordersModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;

            }
        };
        ordersTable = new JTable(ordersModel);


        ordersTable.getColumnModel().getColumn(0).setPreferredWidth(100);  // Order ID
        ordersTable.getColumnModel().getColumn(1).setPreferredWidth(100);  // Status
        ordersTable.getColumnModel().getColumn(2).setPreferredWidth(300);  // Details

        refreshOrders();
    }

    private void refreshMenu() {
        menuModel.setRowCount(0); // Clear existing rows
        List<Item> items = admin.getMenu().getAllItems();
        for (Item item : items) {
            Object[] row = {
                    item.getid(),
                    item.getName(),
                    item.getCategory(),
                    "₹" + item.getPrice(),
                    item.isAvailable() ? "Yes" : "No"
            };
            menuModel.addRow(row);
        }
    }

    private void refreshOrders() {
        ordersModel.setRowCount(0);
        List<Order> orders = admin.viewpendingOrders();
        for (Order order : orders) {
            StringBuilder details = new StringBuilder();
            details.append("Items: ");
            List<OrderItem> food = order.getItems();
            for (OrderItem item : food){
                details.append(" "+ item.toString() + ",");
            }

//            Cart orderCart = order.getCart();
//            if (orderCart != null) {
//                List<Item> items = orderCart.getItemsList();
//                for (Item item : items) {
//                    details.append(item.getName())
//                            .append(", ");
//                }
//            }

            Object[] row = {
                    order.getOrderId(),
                    order.getStatus(),
                    details.toString()
            };
            ordersModel.addRow(row);
        }
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static void launchGUI(Admin admin) {
        new GUISystem(admin).show();
    }
}