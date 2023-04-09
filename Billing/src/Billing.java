import java.util.Scanner;

public class Billing {
        private Inventory inventory;
        private CardRepository cardRepository;
        private InvoiceValidator invoiceValidator;

        public Billing() {
                this.addInventory();
                this.addCardRepository();
                this.addNewInvoiceValidator();
        }

        private void addInventory() {
                CSVFileReader inventoryCSVReader = new CSVFileReaderBuilder()
                                .withFileName("inventory.csv")
                                .withSplitBy(",")
                                .build();
                this.inventory = new InventoryBuilder().build();
                this.inventory.loadDataFromCSV(inventoryCSVReader);
        }

        private void addCardRepository() {
                CSVFileReader cardRepositoryCSVReader = new CSVFileReaderBuilder()
                                .withFileName("cards.csv")
                                .withSplitBy(",")
                                .build();
                this.cardRepository = new CardRepositoryBuilder().build();
                this.cardRepository.loadDataFromCSV(cardRepositoryCSVReader);
        }

        private void addNewInvoiceValidator() {
                this.invoiceValidator = new InvoiceValidatorBuilder()
                                .withEssentialLimit(3)
                                .withLuxuryLimit(4)
                                .withMiscLimit(6)
                                .build();
        }

        private Invoice readNewInvoice(String filename) {
                CSVFileReader inputCSVReader = new CSVFileReaderBuilder()
                                .withFileName(filename)
                                .withSplitBy(",")
                                .build();

                Invoice invoice = new Invoice();
                invoice.loadDataFromCSV(inputCSVReader);
                return invoice;
        }

        private boolean process(Invoice invoice) {
                boolean success = this.invoiceValidator.validate(this.inventory, this.cardRepository, invoice);
                System.out.println("Validation " + (!success ? "Failed" : "Success"));
                if (success) {
                        this.invoiceValidator.process(this.inventory, this.cardRepository, invoice);
                }
                return success;
        }

        public static void main(String[] args) {
                Billing billing = new Billing();

                OutputWriter outputWriter = new OutpuWriterFactory()
                                .getOutputWriterFactory(OutpuWriterFactory.OUTPUT_TABLE);

                OutputWriter csvOutputWriter = new OutpuWriterFactory()
                                .getOutputWriterFactory(OutpuWriterFactory.OUTPUT_CSV);

                OutputWriter errorOutputWriter = new OutpuWriterFactory()
                                .getOutputWriterFactory(OutpuWriterFactory.OUTPUT_ERROR);

                Scanner sc = new Scanner(System.in);
                System.out.println("Enter Invoice filename (ex. input1.csv) :");
                String filename = sc.nextLine().trim();
                try {
                        Invoice invoice = billing.readNewInvoice(filename);
                        csvOutputWriter.toFileName("Output_" + filename);
                        errorOutputWriter.toFileName("Output_" + filename);

                        billing.inventory.display(outputWriter);
                        // billing.cardRepository.display(outputWriter); Dont Display Card Numbers
                        invoice.displayInitial(outputWriter);
                        boolean success = billing.process(invoice);
                        invoice.display(outputWriter);
                        invoice.display(success ? csvOutputWriter : errorOutputWriter);
                } catch (Exception e) {
                        System.out.println("Make sure file exists !");
                }

                sc.close();
                System.out.println("Thank You !");
        }

}
