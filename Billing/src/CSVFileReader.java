import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CSVFileReaderBuilder {
    public String filename;
    public String splitBy;

    public CSVFileReaderBuilder() {
        this.filename = "";
        this.splitBy = "";
    }

    public CSVFileReaderBuilder withFileName(String filename) {
        this.filename = filename;
        return this;
    }

    public CSVFileReaderBuilder withSplitBy(String splitBy) {
        this.splitBy = splitBy;
        return this;
    }

    public CSVFileReader build() {
        CSVFileReader file = new CSVFileReader(this);
        return file;
    }
}

public class CSVFileReader {
    public List<String> Keys;
    public Map<String, ArrayList<String>> Values;
    private String filename;
    private String splitBy;

    public CSVFileReader(CSVFileReaderBuilder builder) {
        this.filename = builder.filename;
        this.splitBy = builder.splitBy;
        this.readCSV();
    }

    public void readCSV() {
        String line = "";
        this.Keys = new ArrayList<String>();
        this.Values = new HashMap<String, ArrayList<String>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            while ((line = br.readLine()) != null) {
                if (this.Keys.size() == 0) {
                    this.Keys.addAll(Arrays.asList(line.trim().split(splitBy)));
                    this.Keys.forEach(key -> this.Values.put(key, new ArrayList<String>()));
                } else {
                    String[] split = line.trim().split(splitBy, -1);
                    for (int i = 0; i < this.Keys.size(); i++) {
                        String key = this.Keys.get(i);
                        String value = split[i].trim();
                        ArrayList<String> current = this.Values.get(key);
                        current.add(value);
                        this.Values.put(key, current);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
        }
    }
}