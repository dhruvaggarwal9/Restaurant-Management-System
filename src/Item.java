import java.util.List;

public class Item {
    private String name;
    private String ID;
    private String category;
    private int price;
    private boolean available;
    private List<Review> reviews;

    public Item(String Name, String Category, int Price, String iD){
        this.name = Name;
        this.category = Category;
        this.price = Price;
        this.available = true;
        this.reviews = null;
        this.ID = iD;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getid() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }
}