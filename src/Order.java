import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Order implements Comparable<Order> {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private String specialRequest;
    private boolean isCancelled;
    private double totalPrice;
    private String deliveryAddress;
    private OrderStatus status;
    private LocalDateTime orderTime;
    private boolean VIP;

    public Order(Customer customer, String deliveryAddress) {
        Random random = new Random();
        this.orderId =  String.valueOf(1000 + random.nextInt(9000));
        this.customer = customer;
        this.items = new ArrayList<>();
        this.specialRequest = "";
        this.isCancelled = false;
        this.totalPrice = 0.0;
        this.deliveryAddress = deliveryAddress;
        this.status = OrderStatus.RECEIVED;
        this.orderTime = LocalDateTime.now();
        this.VIP = customer.isVip();
    }

    public void addItem(Item item, int quantity) {
        OrderItem orderItem = new OrderItem(item, quantity);
        items.add(orderItem);
        calculateTotalPrice();
    }

    public void setID(String ID) {
        this.orderId = ID;
    }

    private void calculateTotalPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getItem().getPrice() * item.getQuantity();
        }
        this.totalPrice = total;
    }

    public boolean containsItem(String itemId) {
        return items.stream()
                .anyMatch(orderItem -> orderItem.getItem().getid().equals(itemId));
    }

    public Cart getCart() {
        return customer.getCart();
    }


    public void processRefund() {
        if (isCancelled || status == OrderStatus.DENIED) {
            System.out.println("Refund processed for order: " + orderId);
        }
    }

    @Override
    public int compareTo(Order other) {
        if (this.customer.isVip() && !other.customer.isVip()) {
            return -1;
        } else if (!this.customer.isVip() && other.customer.isVip()) {
            return 1;
        }
        return this.orderTime.compareTo(other.orderTime);
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
        if (cancelled) {
            this.status = OrderStatus.CANCELLED;
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }


    @Override
    public String toString() {
        return String.format("""
            Order ID: %s
            Customer: %s
            Status: %s
            Items: %d
            Total Price: ₹ %.2f
            Order Time: %s
            Special Request: %s
            """,
                orderId,
                customer.getName(),
                status,
                items.size(),
                totalPrice,
                orderTime,
                (specialRequest != null && !specialRequest.isEmpty()) ? specialRequest : "None"
        );
    }

    public boolean isVIP() {
        return VIP;
    }

    public void setVIP(boolean VIP) {
        this.VIP = VIP;
    }
}