/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rundb_v2.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ben McCarthy
 */
public class RunDB2Properties {

	private Logger logger = LogManager.getLogger(RunDB2Properties.class);

    Properties prop = new Properties();
    InputStream input = null;
    OutputStream output = null;
/*    private String filedir = "C:\\Eclipse\\workspace\\RunDB_v2.zip_expanded\\RunDB_JFrame\\src\\resources"; */
    private String filedir = "C:\\RunDB_2\\deploy\\resources";
/* rundb2.properties for Prod; rundb2Test.properties for Testing  */
    private String filename = "rundb2.properties";
    
    public void loadRunProp() {
    	logger.debug("RunDB_2 Application loadRunProp() method 001 - Logging DEBUG");
        try {
            input = new FileInputStream(filedir + "/" + filename);
            
            prop.load(input);
    
        } catch (IOException ex) {
        	logger.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getRunProp(String propKey) {
    	logger.debug("RunDB_2 Application getRunProp() method 001 - Logging DEBUG");
        String propValue = prop.getProperty(propKey);
        return propValue;
    }
    public void updateRunProp(String propKey, String propValue) {
    	logger.debug("RunDB_2 Application updateRunProp() method 001 - Logging DEBUG");
        try {
            output = new FileOutputStream(filedir + "/" + filename);
            
            prop.setProperty(propKey,propValue);
            prop.store(output, null);
            
        } catch (IOException ex) {
        	logger.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

