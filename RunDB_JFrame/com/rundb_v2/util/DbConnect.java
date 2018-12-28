/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rundb_v2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rundb_v2.gui.Main;

/**
 *
 * @author Ben McCarthy
 */



public class DbConnect {
	private Logger logger = LogManager.getLogger(DbConnect.class);

	public Connection con;
    private RunDB2Properties prp;

    public DbConnect() {
    	logger.info("RunDB_2 Application DbConnect() constructor 001 - Logging INFO");
        init();
        try {
            String host = prp.getRunProp("db.host");
            String user = prp.getRunProp("db.user");
            String password = prp.getRunProp("db.password");
            con = DriverManager.getConnection(host, user, password);
        }
        catch (SQLException ex) {
        	logger.error(ex.getMessage());
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    
    }

    public final void init() {
    	logger.debug("RunDB_2 Application init() method 001 - Logging DEBUG");
        prp = new RunDB2Properties();
        prp.loadRunProp();
    }

    public void closeConnection() {
    	logger.debug("RunDB_2 Application closeConnection() method 001 - Logging DEBUG");
        try {
            con.close();
        } catch(SQLException ex) {
        	logger.error(ex.getMessage());
            System.out.println("Cannot close Database connection");
            throw new RuntimeException(ex);
        }
    }
}
