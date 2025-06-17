import java.util.*;

public class OrderQueue {
    private PriorityQueue<Order> pendingOrders;

    public OrderQueue() {
        this.pendingOrders = new PriorityQueue<>(new OrderPriorityComparator());
    }

    public void addOrder(Order order) {
        if (order != null) {
            pendingOrders.offer(order);
        }
    }

    public Order getNextOrder() {
        return pendingOrders.poll();
    }

    public void removeOrder(String orderId) {
        pendingOrders.removeIf(order -> order.getOrderId().equals(orderId));
    }

    public boolean isEmpty() {
        return pendingOrders.isEmpty();
    }

    public int getPendingOrderCount() {
        return pendingOrders.size();
    }

    public List<Order> getPendingOrders() {
        return new ArrayList<>(pendingOrders);
    }

    public Order peekNextOrder() {
        return pendingOrders.peek();
    }
}