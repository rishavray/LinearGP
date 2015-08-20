/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lineargp;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author rish
 */
public class CSVParser {
    private static final String [] HEADER = {"Date","MinTemp",
        "MaxTemp","Humidity","WindSpeed","Sunshine","Dew","Evaporation"};
    
    public static ArrayList readCsvFile(String fileName) {
        
        ArrayList<double[]> data = new ArrayList<>();
        
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = 
                    CSVFormat.EXCEL.withHeader().parse(in);
            for (CSVRecord record : records) {
                double d [] = new double[7];
                d[0] = Double.parseDouble(record.get(HEADER[1]));
                //System.out.println(d[0]);
                d[1] = Double.parseDouble(record.get(HEADER[2]));
                d[2] = Double.parseDouble(record.get(HEADER[3]));
                d[3] = Double.parseDouble(record.get(HEADER[4]));
                d[4] = Double.parseDouble(record.get(HEADER[5]));
                d[5] = Double.parseDouble(record.get(HEADER[6]));
                d[6] = Double.parseDouble(record.get(HEADER[7]));
                data.add(d);
            } 
        } 
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}