import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class OutpuWriterFactory {

    public static final String OUTPUT_TABLE = "OUTPUT_TABLE";
    public static final String OUTPUT_CSV = "OUTPUT_CSV";
    public static final String OUTPUT_ERROR = "OUTPUT_ERROR";

    public OutputWriter getOutputWriterFactory(String type) {
        switch (type) {
            case (OutpuWriterFactory.OUTPUT_TABLE):
                return new TableOutputWriter();
            case (OutpuWriterFactory.OUTPUT_CSV):
                return new CSVOutputWriter();
            case (OutpuWriterFactory.OUTPUT_ERROR):
                return new ErrorOutputWriter();

        }
        return null;
    }

}

abstract public class OutputWriter {
    abstract void toFileName(String name);

    abstract void print(Adapter adapter);
}

class CSVOutputWriter extends OutputWriter {
    private String filename;

    public void toFileName(String name) {
        this.filename = name;
    }

    @Override
    public void print(Adapter adapter) {
        System.out.println("\nWriting [ " + adapter.getTableName() + " ] to [ " + this.filename + " ]...");

        ArrayList<ArrayList<String>> rows = adapter.getValues();
        if (rows.size() == 0) {
            System.out.println("No Data");
            return;
        }
        try (PrintWriter writer = new PrintWriter(this.filename)) {
            StringBuilder result = new StringBuilder();

            for (List<String> row : rows) {
                for (int i = 0; i < row.size(); ++i) {
                    if (i > 0)
                        result.append(",");
                    result.append(row.get(i));
                }
                result.append("\n");
            }
            writer.write(result.toString());
            System.out.println("Write Success");

        } catch (FileNotFoundException e) {
            System.out.println("Error writing to file [ " + this.filename + " ] " + e.getMessage());
        }
    }
}

class TableOutputWriter extends OutputWriter {

    @Override
    void print(Adapter adapter) {
        System.out.println("\nDisplaying [ " + adapter.getTableName() + " ] Contents:");

        ArrayList<ArrayList<String>> rows = adapter.getValues();
        if (rows.size() == 0) {
            System.out.println("No Data");
            return;

        }
        int[] maxLengths = new int[rows.get(0).size()];
        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
            }
        }

        StringBuilder formatBuilder = new StringBuilder();
        for (int maxLength : maxLengths) {
            formatBuilder.append("%-").append(maxLength + 2).append("s");
        }
        String format = formatBuilder.toString();

        StringBuilder result = new StringBuilder();
        for (List<String> row : rows) {
            result.append(String.format(format, row.toArray())).append("\n");
        }
        System.out.println('\n' + result.toString());
    }

    @Override
    void toFileName(String name) {
    }

}

class ErrorOutputWriter extends OutputWriter {
    private String filename;

    @Override
    void toFileName(String name) {
        this.filename = name.split("\\.")[0] + ".txt";
    }

    @Override
    public void print(Adapter adapter) {
        System.out.println("\nWriting [ " + adapter.getTableName() + " ] to [ " + this.filename + " ]...");

        ArrayList<ArrayList<String>> rows = adapter.getValues();
        if (rows.size() == 0) {
            System.out.println("No Data");
            return;
        }
        try (PrintWriter writer = new PrintWriter(this.filename)) {
            StringBuilder result = new StringBuilder();

            result.append("Error");
            result.append("\n");
            result.append(adapter.getError());

            writer.write(result.toString());
            System.out.println("Write Success");

        } catch (FileNotFoundException e) {
            System.out.println("Error writing to file [ " + this.filename + " ] " + e.getMessage());
        }
    }

}
