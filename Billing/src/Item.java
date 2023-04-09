public class Item {
    private int quantity;
    private int price;
    private String name;
    private String type;

    public Item(String type, int quantity, int price, String name) {
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.type = type;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int setQuantity(int quantity) {
        return this.quantity -= quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }
}