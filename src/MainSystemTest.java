import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainSystemTest {

    private Customer customer;
    private Item outOfStockItem;

    @BeforeEach
    void setUp() {

        Admin admin = new Admin();
        Item availableItem = new Item("Burger", "Fast Food", 150, "FF001");
        outOfStockItem = new Item("Pizza", "Fast Food", 200, "FF002");
        outOfStockItem.setAvailable(false);

        admin.additem(availableItem);
        admin.additem(outOfStockItem);

        customer = new Customer("John Doe", "john@example.com", "1234567890", "password123");
    }

    @Test
    void testOrderingOutOfStockItems() {
        customer.addtocart(outOfStockItem, 1);

        assertFalse(customer.getCart().getItems().containsKey(outOfStockItem),
                "Out-of-stock items should not be added to the cart");
    }

    @Test
    void testInvalidLoginAttempts() {
        boolean loginResult = simulateCustomerLogin("wrong@example.com", "password123");
        assertFalse(loginResult, "Login should fail with incorrect email");

        loginResult = simulateCustomerLogin("john@example.com", "wrongpassword");
        assertFalse(loginResult, "Login should fail with incorrect password");

        loginResult = simulateCustomerLogin("john@example.com", "password123");
        assertTrue(loginResult, "Login should succeed with correct credentials");
    }

    private boolean simulateCustomerLogin(String email, String password) {

        if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
