/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lineargp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author rish
 */
public class Config {
    private static Config instance = null;

    private Properties props = null;

    private Config() {
         props = new Properties();
    	try {
	    FileInputStream fis = new FileInputStream("gp.prop");
	    props.load(fis);
    	}
    	catch (Exception e) {
    	    // catch Configuration Exception right here
    	}
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    // get property value by name
    public String getProp(String key) {
        String value = null;
        if (props.containsKey(key))
            value = (String) props.get(key);
        return value;
    }
    
    public void writeProp(String key, String value)
    {
        try {
	    FileOutputStream fis = new FileOutputStream("gp.prop");
	    props.setProperty(key,value);
            props.store(fis, null);
            fis.close();
    	}
    	catch (Exception e) {
    	    // catch Configuration Exception right here
    	}
    }
}