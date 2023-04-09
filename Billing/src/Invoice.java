import java.util.ArrayList;
import java.util.HashMap;

public class Invoice {
    private String cardNumber;
    private String error;
    private ArrayList<Item> items;
    private HashMap<String, Integer> initialItems;
    int total;

    public Invoice() {
        this.cardNumber = "";
        this.items = new ArrayList<Item>();
        this.initialItems = new HashMap<String, Integer>();
        this.error = "";
        this.total = 0;
    }

    public void loadDataFromCSV(CSVFileReader cs) {
        for (int i = 0; i < cs.Values.get("Item").size(); i++) {
            String name = cs.Values.get("Item").get(i).toUpperCase();
            int quantity = Integer.parseInt(cs.Values.get("Quantity").get(i));
            initialItems.put(name, quantity);
        }
        this.cardNumber = cs.Values.get("CardNumber").get(0);
    }

    public Invoice addItem(String itemType, int quantity, int price, String name) {
        Item newItem = new Item(itemType, quantity, price, name);
        this.items.add(newItem);
        return this;
    }

    public Invoice addItem(Item item) {
        this.items.add(item);
        return this;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public HashMap<String, Integer> getInitialItems() {
        return this.initialItems;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void display(OutputWriter outputWriter) {
        if (this.error.length() > 0)
            outputWriter.print(new InvoiceAdapterInitial(this));
        else
            outputWriter.print(new InvoiceAdapter(this));
    }

    public void displayInitial(OutputWriter outputWriter) {
        outputWriter.print(new InvoiceAdapterInitial(this));
    }
}
