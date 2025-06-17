import java.time.LocalDateTime;

public class Review {
    private Customer customer;
    private String comment;
    private int rating;
    private LocalDateTime timestamp;

    public Review(Customer cus, String com, int Rate) {
        this.customer = cus;
        this.comment = com;
        this.rating = Rate;
        this.timestamp = LocalDateTime.now();
    }
}