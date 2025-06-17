import java.io.*;
import java.util.*;

public class Customer {
    private String id;
    private String name;
    private String email;
    private String phone;
    private boolean isVip;
    private Cart cart;
    private List<Order> orderHistory;
    private String address;
    private PaymentMethod paymentMethod;
    private String password;

    public Customer(String name, String email, String phone, String pass) {
        Random random = new Random();
        this.id =  String.valueOf(10000 + random.nextInt(90000));
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isVip = false;
        this.cart = new Cart();
        this.orderHistory = new ArrayList<>();
        this.password = pass;
    }
    public List<Item> viewMenu(Menu menu) {
        return menu.getAllItems();
    }

    public List<Item> searchitems(Menu menu, String keyword) {
        return menu.searchItems(keyword);
    }

    public List<Item> filterbyCategory(Menu menu, String category) {
        return menu.filterByCategory(category);
    }

    public List<Item> sortbyprice(Menu menu, boolean ascending) {
        return menu.sortByPrice(ascending);
    }

    public void addtocart(Item item, int quantity) {
        if (item.isAvailable()) {
            cart.additem(item, quantity);
        }
    }

    public void updatequantity(String itemId, int quantity) {
        cart.updatequantity(itemId, quantity);
    }

    public void removefromcart(String itemId) {
        cart.removeitem(itemId);
    }

    public Order checkout() {
        if (cart.isEmpty() || address == null || paymentMethod == null) {
            return null;
        }

        Order order = new Order(this, address);

        for (Map.Entry<Item, Integer> entry : cart.getItems().entrySet()) {
            order.addItem(entry.getKey(), entry.getValue());
        }

        cart.clear();

        orderHistory.add(order);

        return order;
    }

    public OrderStatus getOrderStatus(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return order.getStatus();
            }
        }
        return null;
    }

    public boolean cancelOrder(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                if (order.getStatus() == OrderStatus.RECEIVED || order.getStatus() == OrderStatus.PREPARING) {
                    order.setCancelled(true);
                    return true;
                }
                return false;
            }
        }
        return false;
    }


    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }

    public void addReview(String itemId, String comment, int rating) {
        boolean hasOrdered = false;

        for (Order order : orderHistory) {
            if (order.containsItem(itemId) && order.getStatus() == OrderStatus.DELIVERED) {
                hasOrdered = true;
                break;
            }
        }

        if (hasOrdered) {
            Review review = new Review(this, comment, rating);
        }
    }



    public void upgradeToVip() {
        this.isVip = true;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isVip() {
        return isVip;
    }

    public Cart getCart() {
        return cart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void saveOrderHistory() {
        String filename = "order_history_" + this.id + ".txt";

        try (OutputStream outputStream = new FileOutputStream(filename)) {
            for (Order order : orderHistory) {

                StringBuilder orderData = new StringBuilder();
                orderData.append("Order ID: ").append(order.getOrderId()).append("\n");
                orderData.append("Address: ").append(this.getAddress()).append("\n");
                orderData.append("Status: ").append(order.getStatus()).append("\n");
                orderData.append("Items:\n");

                for (OrderItem item : order.getItems()) {
                    Item item1 = item.getItem();
                    orderData.append("  - ")
                            .append(item1.getid()).append(": ")
                            .append(item1.getName()).append(", Quantity: ")
                            .append(item.getQuantity()).append(", Price: ")
                            .append(item1.getPrice()).append("\n");
                }
                orderData.append("Total Price: ").append(order.getCart().getTotal()).append("\n");
                orderData.append("=====\n");

                outputStream.write(orderData.toString().getBytes());
            }
        } catch (IOException e) {
            System.out.println("Error saving order history: " + e.getMessage());
        }
    }


    public void loadOrderHistory() {
        String filename = "order_history_" + this.id + ".txt";
        File file = new File(filename);

        if (!file.exists()) return;

        try (InputStream inputStream = new FileInputStream(file)) {
            StringBuilder content = new StringBuilder();
            int byteData;

            while ((byteData = inputStream.read()) != -1) {
                content.append((char) byteData);
            }

            String[] orders = content.toString().split("=====\n");
            for (String orderData : orders) {
                if (orderData.trim().isEmpty()) continue;

                String[] lines = orderData.split("\n");
                String orderId = lines[0].split(": ")[1];
                String address = lines[1].split(": ")[1];
                OrderStatus status = OrderStatus.valueOf(lines[2].split(": ")[1]);

                Order order = new Order(this, address);
                order.setID(orderId);
                order.setStatus(status);


                for (int i = 4; i < lines.length - 2; i++) {
                    String[] itemParts = lines[i].split(", ");
                    String itemId = itemParts[0].split(": ")[1];
                    String itemName = itemParts[1].split(": ")[1];
                    int quantity = Integer.parseInt(itemParts[2].split(": ")[1]);
                    int price = Integer.parseInt(itemParts[3].split(": ")[1]);

                    Item item = new Item ( itemName,"_",  price, itemId );
                    order.addItem(item, quantity);
                }

                orderHistory.add(order);
            }
        } catch (IOException e) {
            System.out.println("Error loading order history: " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return String.format("Customer [id=%s, name=%s, email=%s, VIP=%s]",
                id, name, email, isVip);
    }

    public Object getPassword() {
        return this.password;
    }
}