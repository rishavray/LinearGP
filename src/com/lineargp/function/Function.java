/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lineargp.function;

/**
 *
 * @author rish
 */
public interface Function {
    @Override
    public String toString();
    public double[] run(double [] inRegister, double[] outRegister);
    
}
