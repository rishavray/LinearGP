/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lineargp.world;

import com.lineargp.Config;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author rish
 */
public class Population implements Serializable{
    
    private final int populationSize;
    public ArrayList<Program> population;
    
    public Population()
    {
        Config c = Config.getInstance();
        this.populationSize = Integer.parseInt(c.getProp("populationSize"));
        this.population = new ArrayList<>();
    }
    
    public void generatePopulation()
    {
        for(int i = 0;i<populationSize;++i)
        {
            Program p = new Program();
            p.generateProgram();
            population.add(p);
        }
            
    }
    
    public void calculatePopulationFitness(ArrayList<double[]>expectedOutput)
    {
        Iterator<Program> prog = population.iterator();
        
        while(prog.hasNext())
        {
            prog.next().updateIndividualFitness(expectedOutput);  
        }
    }
    public void displayPopulationFitness()
    {
        Iterator<Program> prog = population.iterator();
        int i=1;
        
        while(prog.hasNext())
        {
            System.out.println((i++)+". "+prog.next().fitness);  
        }
    } 
}
