import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

class DailyReport {
    private LocalDate date;
    private int totalOrders;
    private double totalSales;
    private List<Item> popularItems;
    private int completedOrders;
    private int cancelledOrders;

    public DailyReport(LocalDate date, int totalOrders, double totalSales,
                       List<Item> popularItems, int completedOrders, int cancelledOrders) {
        this.date = date;
        this.totalOrders = totalOrders;
        this.totalSales = totalSales;
        this.popularItems = popularItems;
        this.completedOrders = completedOrders;
        this.cancelledOrders = cancelledOrders;
    }

    public LocalDate getDate() { return date; }
    public int getTotalOrders() { return totalOrders; }
    public double getTotalSales() { return totalSales; }
    public List<Item> getPopularItems() { return popularItems; }
    public int getCompletedOrders() { return completedOrders; }
    public int getCancelledOrders() { return cancelledOrders; }

    @Override
    public String toString() {
        return String.format("""
            Daily Report for %s
            Total Orders: %d
            Completed Orders: %d
            Cancelled Orders: %d
            Total Sales: ₹ %.2f
            Top 5 Popular Items: %s
            """,
                date, totalOrders, completedOrders, cancelledOrders, totalSales,
                popularItems.stream().map(Item::getName).collect(Collectors.joining(", "))
        );
    }
}