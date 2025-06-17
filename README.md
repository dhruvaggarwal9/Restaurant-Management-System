# Restaurant-Management-System
A full-featured Java-based CLI + GUI application for managing restaurant orders, customers, and menus. Built using OOP principles, with persistent cart/order history storage, admin controls, and a Swing-based live dashboard.



# 🍽️ Restaurant Management System

A Java-based terminal + GUI application that simulates a full-featured restaurant management system. It allows customers to order food, admins to manage the menu and orders, and integrates persistent storage with GUI display support.

---

## 📂 Project Structure

```
RestaurantManagementSystem/
├── .idea/                     # IntelliJ project configs
├── out/production/Assignment-3  # Build output (ignore for source edits)
├── src/                       # Source code folder
│   ├── Admin.java             # Admin control logic
│   ├── Cart.java              # Cart data structure for customer
│   ├── Customer.java          # Customer class with cart/order actions
│   ├── DailyReport.java       # Report generation class
│   ├── GUISystem.java         # GUI display using Swing
│   ├── Item.java              # Food item details
│   ├── MainSystem.java        # MAIN file: Entry point (CLI logic)
│   ├── MainSystemTest.java    # JUnit test class
│   ├── Menu.java              # Menu system (collection of items)
│   ├── Order.java             # Order representation
│   ├── OrderItem.java         # Represents an item in an order
│   ├── OrderPriorityComparator.java # VIP-first comparator
│   ├── OrderQueue.java        # Handles order queue with priority
│   ├── OrderStatus.java       # Enum for order states
│   ├── PaymentMethod.java     # Customer payment info
│   └── Review.java            # Food item review system
├── cart_*.txt                 # Temporary saved carts
├── order_history_*.txt        # Persistent order history
├── project.iml           # IntelliJ module file
└── README.md                  # ← You're here
```

---

## 🚀 Features

### 👤 Customer Side (CLI)

* Sign up, log in, and upgrade to VIP
* Search, filter, and sort menu items
* Add, remove, and update cart
* Track orders in real-time
* Special request handling
* View and reorder from order history

### 🧑‍🍳 Admin Side (CLI)

* Add/update/delete menu items
* Process orders based on priority (VIP first)
* View and update order status
* Generate daily sales reports
* View registered users and handle refunds

### 🖥️ GUI (Swing)

* View **Menu** (name, price, availability)
* View **Pending Orders** (order number, status)
* Navigation buttons between menu and orders

> ⚠ GUI is display-only. All data manipulation must happen through the CLI.

---

## 💾 Persistent File Handling

Implemented using plain text:

* ✅ User cart session storage: `cart_<ID>.txt`
* ✅ Order history tracking: `order_history_<ID>.txt`

---

## 🧪 JUnit Testing

`MainSystemTest.java` includes:

* ❌ Preventing out-of-stock orders
* ❌ Handling invalid login
* ✅ Cart add/update/remove operations

---

## 🛠 How to Run

1. **Compile** the project:

```bash
javac src/*.java
```

2. **Run the CLI interface**:

```bash
java -cp src MainSystem
```

3. **Run the GUI (read-only)**:

```bash
java -cp src GUISystem
```

---

## 📷 GUI Preview

*(Add screenshots to your repo if needed)*

* **Menu Viewer Page**
* **Pending Orders Page**
* **Navigation Buttons**

---

## 👨‍💻 Developer Info

* **Author**: \[Your Name]
* **Institute Roll Number**: \[Your Roll Number]
* **Course**: Advanced Programming
* **Assignment**: 4 – "Byte Me!" → *Restaurant Management System*

---

## 📌 Notes

* 🛑 Use of AI assistants was strictly prohibited during development.
* 🧠 Collections like `ArrayList`, `HashMap`, `PriorityQueue` used effectively.
* ☕ Fully written in **Java** using **OOP principles**.


