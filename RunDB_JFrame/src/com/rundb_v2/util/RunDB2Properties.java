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

/**
 *
 * @author Ben McCarthy
 */
public class RunDB2Properties {

    Properties prop = new Properties();
    InputStream input = null;
    OutputStream output = null;
    private String filedir = "C:\\Eclipse\\workspace\\RunDB_v2.zip_expanded\\RunDB_v2\\src\\resources";
    private String filename = "rundb2.properties";
    
    public void loadRunProp() {
        try {
            input = new FileInputStream(filedir + "/" + filename);
            
            prop.load(input);
    
        } catch (IOException ex) {
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
        String propValue = prop.getProperty(propKey);
        return propValue;
    }
    public void updateRunProp(String propKey, String propValue) {
        try {
            output = new FileOutputStream(filedir + "/" + filename);
            
            prop.setProperty(propKey,propValue);
            prop.store(output, null);
            
        } catch (IOException ex) {
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

