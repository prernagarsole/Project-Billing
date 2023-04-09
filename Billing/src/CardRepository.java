import java.util.HashSet;

class CardRepositoryBuilder {
    public CardRepository build() {
        return CardRepository.getOrCreateInstance(this);
    }
}

public class CardRepository {
    static CardRepository instance;
    private HashSet<String> repo;

    public CardRepository(CardRepositoryBuilder CardRepositoryBuilder) {
        this.repo = new HashSet<String>();
    }

    public static CardRepository getOrCreateInstance(CardRepositoryBuilder CardRepositoryBuilder) {
        if (CardRepository.instance == null)
            CardRepository.instance = new CardRepository(CardRepositoryBuilder);
        return CardRepository.instance;
    }

    public void loadDataFromCSV(CSVFileReader cs) {
        for (int i = 0; i < cs.Values.get("CardNumber").size(); i++) {
            this.addCard(cs.Values.get("CardNumber").get(i));
        }
    }

    public CardRepository addCard(String cardNumber) {
        this.repo.add(cardNumber);
        return this;
    }

    public HashSet<String> getCards() {
        return this.repo;
    }

    public boolean validateCard(String cardNumber) {
    	this.repo.add(cardNumber);
        return this.repo.contains(cardNumber);
    }

    public void display(OutputWriter outputWriter) {
        outputWriter.print(new CardRepositoryAdapter(this));
    }
}
