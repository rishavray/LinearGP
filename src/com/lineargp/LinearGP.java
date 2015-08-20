/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lineargp;
import com.lineargp.world.Evolve;
import com.lineargp.world.Population;
import com.lineargp.world.Program;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;


/**
 *
 * @author rish
 */
public class LinearGP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Properties pr = new Properties();
        pr.setProperty("java.util.Arrays.useLegacyMergeSort","true");
        System.setProperties(pr);
        boolean train = 
                Boolean.parseBoolean(Config.getInstance().getProp("train"));
        if(train)
        {
        System.out.println("Reading dataset");
        ArrayList<double[]> data = 
              CSVParser.readCsvFile(Config.getInstance().getProp("dataSet"));
        System.out.println("Dataset Read");
        
        System.out.println("Generating population");
        Population p = new Population();
        p.generatePopulation();
        p.calculatePopulationFitness(data);
        System.out.println("Population generated");
        
        System.out.println("Starting evolution..");
        Evolve e = new Evolve(p);
        Program best = e.evolveGeneration(data);
        
        //p.displayPopulationFitness();
        System.out.println(best.toString());
        best.display();
        
        System.out.println(best.fitness);
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream
                    (new FileOutputStream("Programs/population.txt"));
            os.writeObject(p);
            os = new ObjectOutputStream
                    (new FileOutputStream("Programs/BestProg.txt"));
            os.writeObject(best);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        finally{
            try{
                if(os != null) os.close();
            } catch (Exception ex){
                 
            }
        }
        }
        else
        {
        //p.displayPopulationFitness();
        ObjectInputStream oin;
        //FileWriter fout;
        ArrayList<double[]> data = 
              CSVParser.readCsvFile("WeatherValidate.csv");
        try {
            oin = new ObjectInputStream
                (new FileInputStream("Programs/16.7.2015/BestProg.txt"));
            //fout = new FileWriter("Validate.csv");
            Program p = (Program)oin.readObject();
            
            for (double[] data1 : data) {
                p.execute(data1);
                System.out.print(data1[0]+" "+data1[1]+" "+data1[2]+
                        " "+data1[3]+" "+data1[4]+" "+data1[5]+" "+data1[6]+" ");
                p.display();
            }
            p.updateIndividualFitness(data);
            System.out.println(p.fitness);
            //System.out.println(p.population.get(1));
                    
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* ObjectInputStream oin;
        ArrayList<double[]> data = 
              CSVParser.readCsvFile("WeatherValidate.csv");
        try {
            oin = new ObjectInputStream
                (new FileInputStream("Programs/population.txt"));
            //fout = new FileWriter("Validate.csv");
            Population p = (Population)oin.readObject();
            Evolve e = new Evolve(p);
            Program best = e.evolveGeneration(data);
            ObjectOutputStream os = new ObjectOutputStream
                    (new FileOutputStream("Programs/population.txt"));
            os.writeObject(p);
            os = new ObjectOutputStream
                    (new FileOutputStream("Programs/BestProg.txt"));
            os.writeObject(best);
            //System.out.println(p.population.get(1));
                    
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }*/
        }
                
      } 
      
        
}
