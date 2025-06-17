import java.time.LocalDate;
import java.util.*;

public class Admin {
    private static Menu menu;
    private OrderQueue orderQueue;
    private Map<String, Order> allOrders;
    private Map<LocalDate, List<Order>> dailyOrders;

    public Admin() {
        this.menu = new Menu();
        this.orderQueue = new OrderQueue();
        this.allOrders = new HashMap<>();
        this.dailyOrders = new HashMap<>();
    }

    public void additem(Item item) {
        menu.addItem(item);
    }

    public void removeitem(String itemId) {
        if (menu.removeItem(itemId)) {
            List<Order> pendingOrders = orderQueue.getPendingOrders();

            for (Order order : pendingOrders) {
                if (order.containsItem(itemId)) {
                    updateOrderstatus(order.getOrderId(), OrderStatus.DENIED);
                }
            }
        }
    }

    public void updateprice(String itemId, int newPrice) {
        Item item = menu.getItemById(itemId);
        if (item != null) {
            item.setPrice(newPrice);
            menu.updateItem(item);
        }
    }

    public void updateavailability(String itemId, boolean available) {
        Item item = menu.getItemById(itemId);
        if (item != null) {
            item.setAvailable(available);
            menu.updateItem(item);

            if (!available) {
                List<Order> pendingOrders = orderQueue.getPendingOrders();
                for (Order order : pendingOrders) {
                    if (order.containsItem(itemId)) {
                        updateOrderstatus(order.getOrderId(), OrderStatus.DENIED);
                    }
                }
            }
        }
    }
    public List<Order> viewpendingOrders() {
        return orderQueue.getPendingOrders();
    }

    public void updateOrderstatus(String orderId, OrderStatus status) {
        Order order = allOrders.get(orderId);
        if (order != null) {
            order.setStatus(status);

            if (status == OrderStatus.DELIVERED || status == OrderStatus.DENIED) {
                orderQueue.removeOrder(orderId);
            }

            LocalDate orderDate = order.getOrderTime().toLocalDate();
            dailyOrders.computeIfAbsent(orderDate, k -> new ArrayList<>()).add(order);
        }
    }

    public void dorefund(String orderId) {
        Order order = allOrders.get(orderId);
        if (order != null && (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.DENIED)) {
            order.processRefund();

        }
    }

    public void handlespecialRequest(String orderId, String request) {
        Order order = allOrders.get(orderId);
        if (order != null) {
            order.setSpecialRequest(request);
        }
    }

    public DailyReport generatedailyreport() {
        LocalDate today = LocalDate.now();
        List<Order> todaysOrders = dailyOrders.getOrDefault(today, new ArrayList<>());

        double totalSales = 0;
        for (Order order : todaysOrders) {
            if (order.getStatus() == OrderStatus.DELIVERED) {
                totalSales += order.getTotalPrice();
            }
        }


        Map<String, Long> itemFrequency = new HashMap<>();
        for (Order order : todaysOrders) {
            if (order.getStatus() == OrderStatus.DELIVERED) {
                for (OrderItem orderItem : order.getItems()) {
                    String itemId = orderItem.getItem().getid();
                    Long currentCount = itemFrequency.getOrDefault(itemId, 0L);
                    itemFrequency.put(itemId, currentCount + 1);
                }
            }
        }


        List<Map.Entry<String, Long>> sortedItems = new ArrayList<>(itemFrequency.entrySet());
        Collections.sort(sortedItems, (a, b) -> b.getValue().compareTo(a.getValue()));

        List<Item> popularItems = new ArrayList<>();
        for (int i = 0; i < Math.min(5, sortedItems.size()); i++) {
            Item item = menu.getItemById(sortedItems.get(i).getKey());
            popularItems.add(item);
        }

        int deliveredCount = 0;
        int cancelledCount = 0;
        for (Order order : todaysOrders) {
            if (order.getStatus() == OrderStatus.DELIVERED) {
                deliveredCount++;
            } else if (order.getStatus() == OrderStatus.CANCELLED) {
                cancelledCount++;
            }
        }

        return new DailyReport(today, todaysOrders.size(), totalSales, popularItems, deliveredCount, cancelledCount);
    }

    public void addNewOrder(Order order) {
        allOrders.put(order.getOrderId(), order);
        orderQueue.addOrder(order);
    }

    public static Menu getMenu() {
        return menu;
    }

    public Order getOrder(String orderId) {
        return allOrders.get(orderId);
    }
}

