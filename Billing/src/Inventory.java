import java.util.HashMap;

class InventoryBuilder {
    public Inventory build() {
        return Inventory.getOrCreateInstance(this);
    }
}

public class Inventory {
    static Inventory instance;
    private HashMap<String, Item> repo;

    public Inventory(InventoryBuilder inventoryBuilder) {
        this.repo = new HashMap<String, Item>();
    }

    public static Inventory getOrCreateInstance(InventoryBuilder inventoryBuilder) {
        if (Inventory.instance == null)
            Inventory.instance = new Inventory(inventoryBuilder);
        return Inventory.instance;
    }

    public void loadDataFromCSV(CSVFileReader cs) {
        for (int i = 0; i < cs.Values.get("Item").size(); i++) {
            String itemType = cs.Values.get("Category").get(i).toUpperCase();
            int quantity = Integer.parseInt(cs.Values.get("Quantity").get(i));
            int price = Integer.parseInt(cs.Values.get("Price (per unit)").get(i));
            String name = cs.Values.get("Item").get(i).toUpperCase();
            this.addItem(itemType, quantity, price, name);
        }
    }

    public Inventory addItem(String itemType, int quantity, int price, String name) {
        Item newItem = new Item(itemType, quantity, price, name);
        this.repo.put(name, newItem);
        return this;
    }

    public Inventory addItem(Item item) {
        this.repo.put(item.getName(), item);
        return this;
    }

    public Item getItem(String name) {
        return this.repo.get(name);
    }

    public HashMap<String, Item> getItems() {
        return this.repo;
    }

    public void display(OutputWriter outputWriter) {
        outputWriter.print(new InventoryAdapter(this));
    }
}
