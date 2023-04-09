import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

class InvoiceValidatorBuilder {
    public int essentialLimit;
    public int luxuryLimit;
    public int miscLimit;

    public InvoiceValidatorBuilder() {
        this.essentialLimit = 5;
        this.luxuryLimit = 5;
        this.miscLimit = 5;
    }

    public InvoiceValidatorBuilder withEssentialLimit(int limit) {
        this.essentialLimit = limit;
        return this;
    }

    public InvoiceValidatorBuilder withLuxuryLimit(int limit) {
        this.luxuryLimit = limit;
        return this;
    }

    public InvoiceValidatorBuilder withMiscLimit(int limit) {
        this.miscLimit = limit;
        return this;
    }

    public InvoiceValidator build() {
        return InvoiceValidator.getOrCreateInstance(this);
    }
}

public class InvoiceValidator {
    private int essentialLimit;
    private int luxuryLimit;
    private int miscLimit;
    static InvoiceValidator instance;

    public InvoiceValidator(InvoiceValidatorBuilder InvoiceValidatorBuilder) {
        this.essentialLimit = InvoiceValidatorBuilder.essentialLimit;
        this.luxuryLimit = InvoiceValidatorBuilder.luxuryLimit;
        this.miscLimit = InvoiceValidatorBuilder.miscLimit;
    }

    public static InvoiceValidator getOrCreateInstance(InvoiceValidatorBuilder InvoiceValidatorBuilder) {
        if (InvoiceValidator.instance == null)
            InvoiceValidator.instance = new InvoiceValidator(InvoiceValidatorBuilder);
        return InvoiceValidator.instance;
    }

    public int getCap(String type) {
        switch (type) {
            case "ESSENTIALS":
                return this.essentialLimit;
            case "LUXURY":
                return this.luxuryLimit;
            case "MISC":
                return this.miscLimit;
        }
        return -1;
    }

    public boolean validate(Inventory inventory, CardRepository cardRepository, Invoice invoice) {
        ArrayList<Item> itemsToPurchase = new ArrayList<Item>();
        HashMap<String, Integer> typeCounter = new HashMap<String, Integer>();

        typeCounter.put("ESSENTIALS", 0);
        typeCounter.put("LUXURY", 0);
        typeCounter.put("MISC", 0);

        for (Entry<String, Integer> entry : invoice.getInitialItems().entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue();
            Item currentItem = inventory.getItem(name);

            if (currentItem == null) {
                invoice.setError("Item [ " + name + " ] not found!");
                return false;
            }

            String type = currentItem.getType();
            int price = currentItem.getPrice();

            if (currentItem.getQuantity() < quantity) {
                invoice.setError("Item [ " + name + " ] insufficient quantity to serve!");
                return false;
            }

        int currentTypeCounter = typeCounter.get(type);
            currentTypeCounter = currentTypeCounter + quantity;
            if (currentTypeCounter > this.getCap(type)) {
                invoice.setError("You cant purcase more than " + this.getCap(type) + " [ " + type + " ] items  !");
                return false;
            }

            typeCounter.put(type, currentTypeCounter);
            itemsToPurchase.add(new Item(type, quantity, price, name));
        }
        String cardNumber = invoice.getCardNumber();

        if (!cardRepository.validateCard(cardNumber)) {
            invoice.setError("Payment failed with card number [ " + cardNumber + " ] !");
            return false;
        }

        itemsToPurchase.forEach(i -> invoice.addItem(i));
        return true;
    }

    public void process(Inventory inventory, CardRepository cardRepository, Invoice invoice) {
        int total = 0;
        for (Item item : invoice.getItems()) {
            Item inventoryItem = inventory.getItem(item.getName());
            inventoryItem.setQuantity(inventoryItem.getQuantity() - item.getQuantity());
            inventory.addItem(inventoryItem);
            total = total + item.getQuantity() * inventoryItem.getPrice();
        }
        invoice.setTotal(total);
    }

}
