/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lineargp.world;

import com.lineargp.Config;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author rish
 */
public class Evolve {
    int maxGeneration, crossoverRate, tournamentSize, mutationRate;
    Population p;
    
    public Evolve(Population p)
    {
        Config c = Config.getInstance();
        this.tournamentSize = Integer.parseInt(c.getProp("tournamentSize"));
        this.crossoverRate = Integer.parseInt(c.getProp("crossoverRate"));
        this.maxGeneration = Integer.parseInt(c.getProp("maxGeneration"));
        this.mutationRate = Integer.parseInt(c.getProp("mutationRate"));
        this.p = p;
        
    }
    
    public Program evolveGeneration(ArrayList<double[]> trainingSet)
    {
        if(tournamentSize > p.population.size()/2)
        {
            System.out.println("Tournament size is greater than the half"
                    + "of population. Aborting!");
            return null;
        }
        Random r = new Random();
        Program currBest = (p.population.get(0)).deepCopy();
        for(int gen=0; gen<=maxGeneration;++gen)
        {
            ArrayList<Program> t1 = new ArrayList<>();
            ArrayList<Program> t2 = new ArrayList<>();
            //System.out.println(p.population.size());
            //
            Collections.shuffle(p.population);
        
        //Creating tournaments
            //System.out.print("Pop size "+p.population.size());
            
            for(int i = 0;i<tournamentSize;++i)
            {
                t1.add(p.population.get(0));
                p.population.remove(0);
            }
            
            Collections.shuffle(p.population);
            
            //System.out.println("Pop size "+p.population.size());
            for(int i = 0;i<tournamentSize-1;++i)
            {
                t2.add(p.population.get(0));
                p.population.remove(0);
            }

            //Finding tournament winner

            Collections.sort(t1);
            Collections.sort(t2);

            Program winner1 = t1.get(0).deepCopy();
            Program winner2 = t2.get(0).deepCopy();
            t1.remove(0);
            t2.remove(0);
            Program offspring1 = new Program();
            Program offspring2 = new Program();



            //Implementing Reproduction
            com.lineargp.Config c = com.lineargp.Config.getInstance();
            int maxLength = Integer.parseInt(c.getProp("maxLength"));
            int minLength = Integer.parseInt(c.getProp("minLength"));
            int childlength, parent1start=0, parent1stop=0,
                    parent2start=0, parent2stop=0;
            //boolean haveWinner = false;
            if (r.nextFloat()*100<crossoverRate)
            {
                do
                {
                    parent1start = r.nextInt(winner1.program.size());
                    //System.out.println((winner1.program.size()-parent1start)+1);
                    parent1stop = parent1start + 
                            r.nextInt((winner1.program.size()-parent1start)+1);
                    parent2start = r.nextInt(winner2.program.size());
                    parent2stop = parent2start +
                            r.nextInt((winner2.program.size()-parent2start)+1);
                    childlength = (parent1stop - parent1start) + 
                            (parent2stop - parent2start);
                    //System.out.println(childlength);
                }while(childlength<=minLength || childlength>=maxLength);
                //System.out.println(winner1.toString());
                for(int i = parent1start;i<parent1stop;++i)
                {
                    offspring1.program.add(winner1.program.get(i));
                }
                for(int i = parent2start;i<parent2stop;++i)
                {
                    offspring1.program.add(winner2.program.get(i));
                }

                do
                {
                    parent1start = r.nextInt(winner1.program.size());
                    parent1stop = parent1start + 
                            r.nextInt((winner1.program.size()-parent1start)+1);
                    parent2start = r.nextInt(winner2.program.size());
                    parent2stop = parent2start + 
                            r.nextInt((winner2.program.size()-parent2start)+1);
                    childlength = (parent1stop - parent1start) + 
                            (parent2stop - parent2start);
                    //System.out.println(childlength);
                }while(childlength<=minLength || childlength>=maxLength);
                
                for(int i = parent2start;i<parent2stop;++i)
                {
                    offspring2.program.add(winner2.program.get(i));
                }
                for(int i = parent1start;i<parent1stop;++i)
                {
                    offspring2.program.add(winner1.program.get(i));
                }
                
                
                offspring1.updateIndividualFitness(trainingSet);
                offspring2.updateIndividualFitness(trainingSet);
                Program temp = t1.get(t1.size()-1);
                if(offspring1.fitness< temp.fitness)
                {
                    t1.remove(t1.size()-1);
                    t1.add(offspring1);
                }
                temp = t2.get(t2.size()-1);
                if(offspring2.fitness< temp.fitness)
                {
                    t2.remove(t2.size()-1);
                    t2.add(offspring2);
                }
                
            }

            //Implementing Mutation
            boolean m = false;
            if(r.nextFloat()*100<=mutationRate)
            {
                m = true;
                winner1.mutate();
                winner2.mutate();
            }

            if(m)
            {
                winner1.updateIndividualFitness(trainingSet);
                winner2.updateIndividualFitness(trainingSet);
            }
            Iterator it = t1.iterator();
            while(it.hasNext())
                p.population.add((Program) it.next());
            it = t2.iterator();
            while(it.hasNext())
                p.population.add((Program) it.next());
            p.population.add(winner1);
            p.population.add(winner2);
            Collections.sort(p.population);
            Program newBest = (p.population.get(0)).deepCopy();
            if(newBest.fitness > currBest.fitness)
            {
                p.population.remove(0);
                p.population.add(currBest);
                Collections.sort(p.population);
            }
            else
                currBest = newBest.deepCopy();
            System.out.println("Pass "+gen+" Raw fitness "+
                    p.population.get(0).fitness);
        }
        
        
        return p.population.get(0);
        
    }
}
