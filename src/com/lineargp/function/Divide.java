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
public class Divide implements Function, Serializable {
    
    int noOfInputReg, noOfOutputReg, inReg1, inReg2, outReg;
    
    public Divide()
    {
        Config c = Config.getInstance();
        this.noOfInputReg = Integer.parseInt(c.getProp("noOfInputReg"));
        this.noOfOutputReg = Integer.parseInt(c.getProp("noOfOutputReg"));
        
        Random select = new Random();
        
        inReg1 = select.nextInt(noOfInputReg+noOfOutputReg);
        inReg2 = select.nextInt(noOfInputReg+noOfOutputReg);
        outReg = select.nextInt(noOfOutputReg);
        
        
    }
    
    public double[] run(double [] inRegister, double [] outRegister)
    {
        double op1,op2;
        if(inReg1 + 1 <= noOfInputReg )
            op1 = inRegister[inReg1];
        else
            op1 = outRegister[inReg1 - noOfInputReg];
        if(inReg2 + 1 <= noOfInputReg )
            op2 = inRegister[inReg2];
        else
            op2 = outRegister[inReg2 - noOfInputReg];
        
        if(op2!=0)
            outRegister[outReg] = op1 / op2;
        else
            outRegister[outReg] = op1/ (op2+1);
        return outRegister;
    }
    
    @Override
    public String toString()
    {
        String op1, op2;
        if(inReg1 + 1 <= noOfInputReg )
            op1 = "x["+inReg1+"]";
        else
            op1 = "r["+(inReg1 - noOfInputReg)+"]";
        if(inReg2 + 1 <= noOfInputReg )
            op2 = "x["+inReg2+"]";
        else
            op2 = "r["+(inReg2 - noOfInputReg)+"]";
        
        return "r["+outReg+"] = "+op1+"/"+op2;
    }  
}
