/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lineargp.function;
import com.lineargp.Config;
import java.io.Serializable;
import java.util.Random;
/**
 *
 * @author rish
 */
public class Exp implements Function, Serializable {
int noOfInputReg, noOfOutputReg, inReg1, outReg;
    
    public Exp()
    {
        Config c = Config.getInstance();
        this.noOfInputReg = Integer.parseInt(c.getProp("noOfInputReg"));
        this.noOfOutputReg = Integer.parseInt(c.getProp("noOfOutputReg"));
        
        Random select = new Random();
        
        inReg1 = select.nextInt(noOfInputReg+noOfOutputReg);
        outReg = select.nextInt(noOfOutputReg);
        
        
    }
    
    public double[] run(double [] inRegister, double [] outRegister)
    {
        double op1;
        if(inReg1 + 1 <= noOfInputReg )
            op1 = inRegister[inReg1];
        else
            op1 = outRegister[inReg1 - noOfInputReg];
        if(op1>32)
            outRegister[outReg] = op1+1;
        else
            outRegister[outReg] = Math.exp(op1);
        return outRegister;
    }
    
    @Override
    public String toString()
    {
        String op1;
        if(inReg1 + 1 <= noOfInputReg )
            op1 = "x["+inReg1+"]";
        else
            op1 = "r["+(inReg1 - noOfInputReg)+"]";
        
        return "r["+outReg+"] = exp("+op1+")";
    }
}