package clustering;

import java.io.*;
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
    private ToStringHelper help = new ToStringHelper();
    
    public DataReader() {
        
    }

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
        initialise();
    }
    
    public void writeFile(String fileName) throws IOException {
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileName));
        FileWriter writer = new FileWriter(fileName);
        String outputLine = "";
        for(ArrayList<String> dataRow : store) {
            for(String value : dataRow) {
                outputLine = outputLine + value + ", "; 
            }
            outputLine = outputLine.trim() + "/n";
            writer.write(outputLine);
        }
    }
    
    private void initialise() {
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
    
    public void addRow(ArrayList<String> newRow) {
        store.add(newRow);
        initialise();
    }
    
    public void printAll() {
        for(ArrayList<String> row : store) {
            System.out.println(help.toString(row));
        }
    }
}
