import java.util.Comparator;

public class OrderPriorityComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1.isVIP() && !o2.isVIP()) return -1;
        if (!o1.isVIP() && o2.isVIP()) return 1;

        return o1.getOrderTime().compareTo(o2.getOrderTime());
    }
}
