/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lineargp.world;
import com.lineargp.function.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author rish
 */
public class Program implements Comparable, Serializable{
    public double [] outputRegister;
    ArrayList<Function> program;
    private final int maxLength, minLength, noOfOutputReg;
    public double fitness;
    final String [] functions = {"Add","Cosine","Divide","Exp","Log",
          "Multiply","Sine","Subtract","Tan","Pow","Sqrt"};
    Random r;
    
    public Program()
    {
        com.lineargp.Config c = com.lineargp.Config.getInstance();
        this.program = new ArrayList<>();
        this.maxLength = Integer.parseInt(c.getProp("maxLength"));
        this.minLength = Integer.parseInt(c.getProp("minLength"));
        this.noOfOutputReg = Integer.parseInt(c.getProp("noOfOutputReg"));
        this.outputRegister = new double[noOfOutputReg];
        this.fitness = 0;
        this.r = new Random();
    }
    
    public void generateProgram()
    {
        
        
        float stopChance = (float) (1.0/(maxLength - minLength + 1));
        
        //float stopChance = r.nextFloat();
        //System.out.println(stopChance);
        while(program.size()<minLength || 
                (r.nextFloat()> stopChance && program.size()<maxLength))
        {
            try {
              Class c = Class.forName("com.lineargp.function."
                      +functions[r.nextInt(functions.length)]);
              program.add((Function)c.newInstance());
            }
            catch (ClassNotFoundException | 
                    SecurityException | InstantiationException 
                    | IllegalAccessException | IllegalArgumentException 
                    ex) {
              ex.printStackTrace();
            }
        }
    }
    
    public void execute(double [] inRegister)
    {
        for(int i = 0; i<outputRegister.length;++i)
        {
            this.outputRegister[i] = 0.0;
        }
        Iterator<Function> it = program.iterator();
        while(it.hasNext())
        {
            outputRegister = it.next().run(inRegister,outputRegister);
            //display();
        }
        
        for(int i = 0; i<outputRegister.length;++i)
        {
            Double d = outputRegister[i];
            if(d.isNaN())
                outputRegister[i] = 0.0;
        }
    }
    
    public void mutate()
    {
        int pos = r.nextInt(this.program.size());
        this.program.remove(pos);
        
        try {
              Class c = Class.forName("com.lineargp.function."
                      +functions[r.nextInt(functions.length)]);
              program.add(pos,(Function)c.newInstance());
            } catch (ClassNotFoundException | 
                    SecurityException | InstantiationException 
                    | IllegalAccessException | IllegalArgumentException 
                    ex) {
              ex.printStackTrace();
            }
    }
    
    @Override
    public String toString()
    {
        String out = "";
        Iterator<Function> it = program.iterator();
        int count = 0;
        while(it.hasNext())
        {
            out = out + it.next().toString()+"\n";
            count++;
        }
        return out+count;
    }
    public void display()
    {
        System.out.print("r[ ");
        for(int i = 0;i<noOfOutputReg;++i)
            System.out.print(outputRegister[i]+" ");
        System.out.println("]");
    }
    
    public double evaluateFitness(double[]expectedOutput)
    {
        double fit = 0.0;
        for(int i = 0;i<expectedOutput.length;++i)
            fit += Math.pow((outputRegister[i] - expectedOutput[i]),2);
        
        Double r = fit/expectedOutput.length;
        if(r.isNaN())
        {
           System.out.println("Flag "+fit); 
           display();
        }
        return r;
    }
    
    public void setFitness(double fit)
    {
        this.fitness = fit;
    }
    
    public void updateIndividualFitness(ArrayList<double[]>expectedOutput)
    {
            double fit = 0.0;
            for(int i = 0;i<expectedOutput.size()-1;++i)
            {
                this.execute(expectedOutput.get(i));
                fit += this.evaluateFitness(expectedOutput.get(i+1));
            }
            
            this.setFitness(fit/expectedOutput.size());
    }
    @Override
    public int compareTo(Object p) {
        if(this.fitness < ((Program)p).fitness)
            return -1;
        else if (this.fitness > ((Program)p).fitness)
            return 1;
        else
            return 0;
        //return (int)(this.fitness - ((Program)p).fitness);
    }
    
    public Program deepCopy()
    {
        Program ret = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(this);
            os.flush();
            os.close();
            byte [] data = bos.toByteArray();
            ByteArrayInputStream bin = new ByteArrayInputStream(data);
            ret = (Program) new ObjectInputStream(bin).readObject();
            
        } catch (IOException | ClassNotFoundException ex) {
            
        }
        
        return ret;
        
    }

    /*@Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
    
}
