import java.util.*;

public class MainSystem {
    private static Admin admin;
    private static Scanner scanner;
    private static List<Customer> customers;
    private static Customer currentCustomer;

    private static final String Fix_mail = "admin@byteme.com";
    private static final String Fix_pass = "admin123";

    public static void main(String[] args) {
        initialize();

        while (true) {
            printLoginMenu();
            int choice = ManualInput("Enter your choice: ");

            switch (choice) {
                case 1 -> handleCustomerLogin();
                case 2 -> handleCustomerSignup();
                case 3 -> handleAdminLogin();
                case 4 -> {
                    // Launch GUI display system
                    GUISystem.launchGUI(admin);
                }
                case 0 -> {
                    System.out.println("Thank you for using the Food Ordering System. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initialize() {
        admin = new Admin();
        scanner = new Scanner(System.in);
        customers = new ArrayList<>();
        admin.additem(new Item("Burger", "Fast Food", 150, "FF001"));
        admin.additem(new Item("Pizza", "Fast Food", 200, "FF002"));
        admin.additem(new Item("Coke", "Beverages", 50, "BV001"));
    }

    private static void printLoginMenu() {
        System.out.println("\nByte ME!");
        System.out.println("1. Customer Login");
        System.out.println("2. Customer Signup");
        System.out.println("3. Admin Login");
        System.out.println("4. Launch Display System (GUI)");
        System.out.println("0. Exit");
    }

    private static void handleCustomerLogin() {
        System.out.println("\nCustomer Login");
        String email = ManualInput3("Enter email: ");
        String password = ManualInput3("Enter password: ");

        Customer foundCustomer = null;
        for (Customer c : customers) {
            if (c.getEmail().equals(email)) {
                foundCustomer = c;
                break;
            }
        }

        if (foundCustomer != null) {
            currentCustomer = foundCustomer;
            currentCustomer.getCart().loadCartFromFile(currentCustomer.getId());
            currentCustomer.loadOrderHistory();
            System.out.println("Login successful! " + currentCustomer.getName());
            handleCustomerInterface();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static void handleCustomerSignup() {
        System.out.println("\nCustomer Signup");
        String name = ManualInput3("Enter your name: ");
        String email = ManualInput3("Enter email: ");
        String phone = ManualInput3("Enter phone number: ");
        String pass = ManualInput3("Enter Password: ");

        Customer newCustomer = new Customer(name, email, phone, pass);
        customers.add(newCustomer);

        System.out.println("Signup successful! Please login to continue.");
    }

    private static void handleAdminLogin() {
        System.out.println("\nAdmin Login");
        String email = ManualInput3("Enter email: ");
        String password = ManualInput3("Enter password: ");

        if (email.equals(Fix_mail) && password.equals(Fix_pass)) {
            System.out.println("Admin login successful!");
            handleAdminInterface();
        } else {
            System.out.println("Invalid admin credentials!");
        }
    }

    private static void handleCustomerInterface() {
        while (true) {
            System.out.println("\nCustomer Menu");
            System.out.println("1. Browse Menu");
            System.out.println("2. Cart Operations");
            System.out.println("3. Order History");
            System.out.println("4. View Profile");
            System.out.println("5. Upgrade to VIP");
            System.out.println("0. Logout");

            int choice = ManualInput("Enter your choice: ");

            switch (choice) {
                case 1 -> handleMenuBrowsing();
                case 2 -> handleCartOperations();
                case 3 -> handleOrderHistory();
                case 4 -> viewProfile();
                case 5 -> upgradeToVIP();
                case 0 -> {
                    currentCustomer = null;
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleAdminInterface() {
        while (true) {
            printAdminMenu();
            int choice = ManualInput("Enter your choice: ");

            switch (choice) {
                case 1 -> handleMenuManagement();
                case 2 -> handleOrderManagement();
                case 3 -> handleReportGeneration();
                case 4 -> viewCustomers();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printAdminMenu() {
        System.out.println("\nAdmin Interface");
        System.out.println("1. Menu Management");
        System.out.println("2. Order Management");
        System.out.println("3. Report Generation");
        System.out.println("4. View All Customers");
        System.out.println("0. Logout");
    }


    private static void handleMenuManagement() {
        while (true) {
            System.out.println("\n Menu Management");
            System.out.println("1. Add new item");
            System.out.println("2. Update existing item");
            System.out.println("3. Remove item");
            System.out.println("4. View all items");
            System.out.println("0. Back to main menu");

            int choice = ManualInput("Enter your choice: ");

            switch (choice) {
                case 1 -> addNewItem();
                case 2 -> updateExistingItem();
                case 3 -> removeItem();
                case 4 -> viewAllItems();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleOrderManagement() {
        while (true) {
            System.out.println("\n Order Management ");
            System.out.println("1. View pending orders");
            System.out.println("2. Update order status");
            System.out.println("3. Process refund");
            System.out.println("4. Handle special request");
            System.out.println("0. Back to main menu");

            int choice = ManualInput("Enter your choice: ");

            switch (choice) {
                case 1 -> viewPendingOrders();
                case 2 -> updateOrderStatus();
                case 3 -> processRefund();
                case 4 -> handleSpecialRequest();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleReportGeneration() {
        System.out.println("\nDaily Report");
        DailyReport report = admin.generatedailyreport();
        System.out.println(report.toString());
    }


    private static void addNewItem() {
        System.out.println("\n Add New Item");
        String name = ManualInput3("Enter item name: ");
        String category = ManualInput3("Enter item category: ");
        int price = ManualInput("Enter item price: ");
        String id = ManualInput3("Enter item ID: ");

        Item newItem = new Item(name, category, price, id);
        admin.additem(newItem);
        System.out.println("Item added successfully!");
    }

    private static void updateExistingItem() {
        System.out.println("\nUpdate Item");

        viewAllItems();

        String itemId = ManualInput3("Enter item ID to update: ");
        Item item = admin.getMenu().getItemById(itemId);

        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.println("Current item details: ");
        System.out.println("Name: " + item.getName());
        System.out.println("Category: " + item.getCategory());
        System.out.println("Price: " + item.getPrice());
        System.out.println("Available: " + item.isAvailable());

        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Price");
        System.out.println("2. Availability");
        int choice = ManualInput("Enter your choice: ");

        switch (choice) {
            case 1 -> {
                int newPrice = ManualInput("Enter new price: ");
                admin.updateprice(itemId, newPrice);
                System.out.println("Price updated successfully!");
            }
            case 2 -> {
                boolean available = ManualInput2("Is item available? (true/false): ");
                admin.updateavailability(itemId, available);
                System.out.println("Availability updated successfully!");
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void removeItem() {
        System.out.println("\n Remove Item ");

        viewAllItems();


        String itemId = ManualInput3("Enter item ID to remove: ");
        admin.removeitem(itemId);
        System.out.println("Item removed successfully!");
    }

    private static void viewAllItems() {
        System.out.println("\n All Menu Items ");
        List<Item> items = admin.getMenu().getAllItems();
        if (items.isEmpty()) {
            System.out.println("No items in menu.");
            return;
        }

        for (Item item : items) {
            System.out.printf("ID: %s, Name: %s, Category: %s, Price: ₹ %d, Available: %s%n",
                    item.getid(), item.getName(), item.getCategory(),
                    item.getPrice(), item.isAvailable());
        }
    }

    private static void viewPendingOrders() {
        System.out.println("\nPending Orders ");
        List<Order> pendingOrders = admin.viewpendingOrders();
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        for (Order order : pendingOrders) {
            System.out.println(order.toString());
        }
    }

    private static void updateOrderStatus() {
        System.out.println("\n Update Order Status");
        String orderId = ManualInput3("Enter order ID: ");

        System.out.println("Available statuses:");
        System.out.println("1. PREPARING");
        System.out.println("2. OUT_FOR_DELIVERY");
        System.out.println("3. DELIVERED");
        System.out.println("4. DENIED");

        int choice = ManualInput("Enter new status (1-4): ");
        OrderStatus newStatus = switch (choice) {
            case 1 -> OrderStatus.PREPARING;
            case 2 -> OrderStatus.OUT_FOR_DELIVERY;
            case 3 -> OrderStatus.DELIVERED;
            case 4 -> OrderStatus.DENIED;
            default -> null;
        };

        if (newStatus != null) {
            admin.updateOrderstatus(orderId, newStatus);
            System.out.println("Order status updated successfully!");
        } else {
            System.out.println("Invalid status choice.");
        }
    }

    private static void processRefund() {
        System.out.println("\nProcess Refund");
        String orderId = ManualInput3("Enter order ID for refund: ");
        admin.dorefund(orderId);
        System.out.println("Refund processed successfully!");
    }

    private static void handleSpecialRequest() {
        System.out.println("\n Handle Special Request ");

        List<Order> pendingOrders = admin.viewpendingOrders();
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        for (Order order : pendingOrders) {
            if(order.getSpecialRequest() != null) {
                System.out.println(order.getOrderId());
                System.out.println(order.getSpecialRequest());
            }
        }

        String orderId = ManualInput3("Enter order ID: ");

        String request = ManualInput3("Enter special request reply: ");
        admin.handlespecialRequest(orderId, request);
        System.out.println("Special request updated successfully!");
    }

    public static Admin getAdmin() {
        return admin;
    }

    private static void viewCustomers() {
        System.out.println("\nRegistered Customers");
        if (customers.isEmpty()) {
            System.out.println("No customers registered yet.");
            return;
        }

        for (Customer customer : customers) {
            System.out.printf("Name: %s, Email: %s, Phone: %s, VIP: %s%n",
                    customer.getName(), customer.getEmail(), customer.getPhone(),
                    customer.isVip() ? "Yes" : "No");
        }
    }

    private static void handleMenuBrowsing() {
        while (true) {
            System.out.println("\n Menu Options");
            System.out.println("1. View All Items");
            System.out.println("2. Search Items");
            System.out.println("3. Filter by Category");
            System.out.println("4. Sort by Price");
            System.out.println("0. Back");

            int choice = ManualInput("Enter your choice: ");

            switch (choice) {
                case 1 -> displayItems(admin.getMenu().getAllItems());
                case 2 -> {
                    String keyword = ManualInput3("Enter search keyword: ");
                    displayItems(currentCustomer.searchitems(admin.getMenu(), keyword));
                }
                case 3 -> {
                    String category = ManualInput3("Enter category: ");
                    displayItems(currentCustomer.filterbyCategory(admin.getMenu(), category));
                }
                case 4 -> {
                    boolean ascending = ManualInput2("Sort ascending? (true/false): ");
                    displayItems(currentCustomer.sortbyprice(admin.getMenu(), ascending));
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void displayItems(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        for (Item item : items) {
            System.out.printf("ID: %s, Name: %s, Category: %s, Price: ₹ %d, Available: %s%n",
                    item.getid(), item.getName(), item.getCategory(),
                    item.getPrice(), item.isAvailable());
        }

        if (ManualInput2("Would you like to add an item to cart? (true/false): ")) {
            String itemId = ManualInput3("Enter item ID to add: ");
            int quantity = ManualInput("Enter quantity: ");
            Item item = admin.getMenu().getItemById(itemId);
            if (item != null && item.isAvailable()) {
                currentCustomer.addtocart(item, quantity);
                currentCustomer.getCart().saveCartToFile(currentCustomer.getId());
                System.out.println("Item added to cart successfully!");
            } else {
                System.out.println("Item not found or not available.");
            }
        }
    }

    private static void handleCartOperations() {
        while (true) {
            System.out.println("\n Cart Operations");
            System.out.println("1. View Cart");
            System.out.println("2. Update Quantity");
            System.out.println("3. Remove Item");
            System.out.println("4. LoadCart From File");
            System.out.println("5. Checkout");
            System.out.println("0. Back");

            int choice = ManualInput("Enter your choice: ");

            switch (choice) {
                case 1 -> viewCart();
                case 2 -> updateCartQuantity();
                case 3 -> removeFromCart();
                case 4 ->  currentCustomer.getCart().loadCartFromFile(currentCustomer.getId());
                case 5 -> checkout();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewCart() {
        Cart cart = currentCustomer.getCart();
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n=== Your Cart ===");
        for (Map.Entry<Item, Integer> entry : cart.getItems().entrySet()) {
            System.out.printf("%s x%d - ₹ %d%n",
                    entry.getKey().getName(),
                    entry.getValue(),
                    entry.getKey().getPrice() * entry.getValue());
        }
        System.out.printf("Total: ₹ %.2f%n", cart.getTotal());
    }

    private static void updateCartQuantity() {
        viewCart();
        String itemId = ManualInput3("Enter item ID to update: ");
        int quantity = ManualInput("Enter new quantity: ");
        currentCustomer.updatequantity(itemId, quantity);
        currentCustomer.getCart().saveCartToFile(currentCustomer.getId());

        System.out.println("Quantity updated successfully!");
    }

    private static void removeFromCart() {
        viewCart();
        String itemId = ManualInput3("Enter item ID to remove: ");
        currentCustomer.removefromcart(itemId);
        currentCustomer.getCart().saveCartToFile(currentCustomer.getId());

        System.out.println("Item removed successfully!");
    }

    private static void checkout() {
        if (currentCustomer.getCart().isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        String address = ManualInput3("Enter delivery address: ");
        currentCustomer.setAddress(address);

        String paymentType = ManualInput3("Enter payment method (CREDIT_CARD/DEBIT_CARD/UPI): ");
        String paymentDetails = ManualInput3("Enter payment details: ");
        currentCustomer.setPaymentMethod(new PaymentMethod(paymentType, paymentDetails));


        boolean addSpecialInstructions = ManualInput2("Do you want to add any special instructions? (true/false): ");
        String specialInstructions = null;
        if (addSpecialInstructions) {
            specialInstructions = ManualInput3("Enter your special instructions: ");
        }

        Order order = currentCustomer.checkout();
        if (order != null) {
            order.setSpecialRequest(specialInstructions);
            admin.addNewOrder(order);
            System.out.println("Order placed successfully! Order ID: " + order.getOrderId());
            currentCustomer.saveOrderHistory();
        } else {
            System.out.println("Failed to place order. Please try again.");
        }
    }

    private static void handleOrderHistory() {
        List<Order> orderHistory = currentCustomer.getOrderHistory();
        if (orderHistory.isEmpty()) {
            System.out.println("No order history found.");
            return;
        }

        System.out.println("\n Order History ");
        for (Order order : orderHistory) {
            System.out.println(order.toString());
        }
    }

    private static void viewProfile() {
        System.out.println("\n Your Profile");
        System.out.println(currentCustomer.toString());
    }

    private static void upgradeToVIP() {
        if (currentCustomer.isVip()) {
            System.out.println("You are already a VIP customer!");
            return;
        }

        System.out.println("Upgrade to VIP for priority order processing!");
        if (ManualInput2("Would you like to upgrade for ₹ 350 monthly? (true/false): ")) {
            currentCustomer.upgradeToVip();
            String a = ManualInput3("Enter payment method (CREDIT_CARD/DEBIT_CARD/UPI): ");
            String b = ManualInput3("Enter payment details: ");
            System.out.println("Congratulations! You are now a VIP customer!");
        }
    }




    private static int ManualInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static boolean ManualInput2(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            }
            System.out.println("Please enter 'true' or 'false'.");
        }
    }

    private static String ManualInput3(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}