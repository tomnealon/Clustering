package clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author tom
 */
public class DataReader {
    private int noRows;
    private int noCols;
    private ArrayList<ArrayList> store = new ArrayList<>();
    //private ToStringHelper help = new ToStringHelper();

    public DataReader(String fileName) throws IOException {
        System.out.println("Reader created using: " + fileName);
        BufferedReader CSVFile = new BufferedReader(new FileReader(fileName));
        String dataRow = CSVFile.readLine();
        while (dataRow != null) {
            String[] stringArray = dataRow.split(",");
            ArrayList<String> rowList = new ArrayList<>();
            rowList.addAll(Arrays.asList(stringArray));
            store.add(rowList);
            dataRow = CSVFile.readLine();
        }
        CSVFile.close();
        this.noRows = store.size();
        this.noCols = store.get(0).size();
    }
    
    public int getNoRows() {
        return noRows;
    }

    public int getNoCols() {
        return noCols;
    }
    
    public ArrayList<String> getRow(int rowNumber) {
        return store.get(rowNumber);
    }
    
    public ArrayList<Double> geRowAsDoubles(int rowNumber) {
        ArrayList<Double> doubleRow = new ArrayList<>();
        for(String eachString : getRow(rowNumber)) {
            doubleRow.add(Double.parseDouble(eachString));
        }
        return doubleRow;
    }
}
