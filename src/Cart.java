import java.io.*;
import java.util.*;
import java.util.Map;

public class Cart {
    private Map<Item, Integer> items;
    private double total;

    public Cart() {
        this.items = new HashMap<>();
        this.total = 0.0;
    }

    public void additem(Item item, int quantity) {
        if (item != null && quantity > 0) {
            items.put(item, items.getOrDefault(item, 0) + quantity);
            caltotal();
        }
    }

    public void updatequantity(String itemId, int quantity) {
        for (Item item : items.keySet()) {
            if (item.getid().equals(itemId)) {
                if (quantity <= 0) {
                    items.remove(item);
                } else {
                    items.put(item, quantity);
                }
                caltotal();
                break;
            }
        }
    }
    public void saveCartToFile(String customerId) {
        try (OutputStream outputStream = new FileOutputStream("cart_" + customerId + ".txt")) {
            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                String line = entry.getKey().getName() + "," + entry.getValue() + "\n";
                outputStream.write(line.getBytes());
            }
        } catch (IOException e) {
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }

    public void loadCartFromFile(String customerId) {
        File file = new File("cart_" + customerId + ".txt");
        if (!file.exists()) return;

        try (InputStream inputStream = new FileInputStream(file)) {
            StringBuilder content = new StringBuilder();
            int byteData;
            while ((byteData = inputStream.read()) != -1) { // Read byte by byte
                content.append((char) byteData);
            }

            String[] lines = content.toString().split("\n");
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length != 2) continue;

                String itemId = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                Item item = Admin.getMenu().getItemById(itemId);
                if (item != null) {
                    items.put(item, quantity);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading cart: " + e.getMessage());
        }
    }
    public void removeitem(String itemId) {
        items.keySet().removeIf(item -> item.getid().equals(itemId));
        caltotal();
    }

    public double caltotal() {
        total = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public Map<Item, Integer> getItems() {
        return new HashMap<>(items);
    }

    public double getTotal() {
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getQuantity(String itemId) {
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if (entry.getKey().getid().equals(itemId)) {
                return entry.getValue();
            }
        }
        return 0;
    }



    public List<Item> getItemsList() {
        List<Item> itemsList = new ArrayList<>();
        // Add all items from the cart's keys (which are Item objects)
        itemsList.addAll(items.keySet());
        return itemsList;
    }


    public void clear() {
        items.clear();
        total = 0;
    }
}
