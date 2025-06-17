import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private TreeMap<String, List<Item>> categoryItems;
    private HashMap<String, Item> itemsById;

    public Menu() {
        this.categoryItems = new TreeMap<>();
        this.itemsById = new HashMap<>();
    }


    public void addItem(Item item) {

        itemsById.put(item.getid(), item);

        categoryItems.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
    }

    public List<Item> searchItems(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(itemsById.values());
        }
        keyword = keyword.toLowerCase().trim();

        String finalKeyword = keyword;
        return itemsById.values().stream()
                .filter(item ->
                        item.getName().toLowerCase().contains(finalKeyword) ||
                                item.getCategory().toLowerCase().contains(finalKeyword))
                .collect(Collectors.toList());
    }

    public List<Item> filterByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return new ArrayList<>(itemsById.values());
        }

        return categoryItems.getOrDefault(category, new ArrayList<>());
    }

    public List<Item> sortByPrice(boolean ascending) {
        List<Item> sortedItems = new ArrayList<>(itemsById.values());

        if (ascending) {
            sortedItems.sort(Comparator.comparingDouble(Item::getPrice));
        } else {
            sortedItems.sort(Comparator.comparingDouble(Item::getPrice).reversed());
        }

        return sortedItems;
    }


    public Item getItemById(String id) {
        return itemsById.get(id);
    }


    public boolean removeItem(String itemId) {
        Item item = itemsById.remove(itemId);
        if (item != null) {
            List<Item> categoryList = categoryItems.get(item.getCategory());
            if (categoryList != null) {
                categoryList.remove(item);
                if (categoryList.isEmpty()) {
                    categoryItems.remove(item.getCategory());
                }
            }
            return true;
        }
        return false;
    }

    public void updateItem(Item item) {
        removeItem(item.getid());
        addItem(item);
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(itemsById.values());
    }
}