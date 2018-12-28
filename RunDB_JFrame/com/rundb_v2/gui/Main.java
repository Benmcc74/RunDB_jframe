package com.rundb_v2.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ben McCarthy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
	
	private static Logger logger = LogManager.getLogger(Main.class);
	
    public static void main(String[] args) {
    	logger.info("                                                    ");
    	logger.info("****************************************************");
    	logger.info("                                                    ");
    	logger.info("RunDB_2 Application main() method 001 - Logging INFO");
    	logger.debug("RunDB_2 Application main() method 002 - Logging DEBUG");

    	RunGui form = new RunGui();
        form.setVisible(true);
    }
    
}
