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
public class Sqrt implements Function, Serializable{
 int noOfInputReg, noOfOutputReg, inReg1, outReg;
    
    public Sqrt()
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
            op1 = Math.abs(inRegister[inReg1]);
        else
            op1 = Math.abs(outRegister[inReg1 - noOfInputReg]);
        
        outRegister[outReg] = Math.sqrt(op1);
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
        
        return "r["+outReg+"] = sqrt("+op1+")";
    }
}