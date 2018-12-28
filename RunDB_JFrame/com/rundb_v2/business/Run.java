/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rundb_v2.business;

import com.rundb_v2.util.DbConnect;
import com.rundb_v2.util.GetSQL;
import com.rundb_v2.util.RunDB2Properties;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ben McCarthy
 */
public class Run {
	private Logger logger = LogManager.getLogger(Run.class);

    public Statement stmt;
    private int runId;
    private int runCrse;
    private String runDate;
    private String runTime;
    private RunDB2Properties prp;
    private GetSQL getSql;

    public Run() {
    	logger.info("RunDB_2 Application Run() constructor 001 - Logging INFO");
        init();
    }

    public final void init() {
    	logger.debug("RunDB_2 Application init() method 001 - Logging DEBUG");
        prp = new RunDB2Properties();
        prp.loadRunProp();
        getSql = new GetSQL();
    }
    
    public void addRun(int crse, String runDate, String runTime) {
    	logger.debug("RunDB_2 Application addRun() method 001 - Logging DEBUG");
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement();

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("AddRun_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crse).toString()).replaceAll("VAR2", runDate).replaceAll("VAR3", runTime);

            stmt.executeQuery(sql);
        }
        catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
    }
    public void deleteRun(int runId) {
    	logger.debug("RunDB_2 Application deleteRun() method 001 - Logging DEBUG");
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement();

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("DeleteRun_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)runId).toString());

            stmt.executeQuery(sql);
            dbcon.closeConnection();
        }
        catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
    }

    public void updateRun(int runId, int runCrse, String runDate, String runTime) {
    	logger.debug("RunDB_2 Application updateRun() method 001 - Logging DEBUG");
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement();

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("UpdateRun_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)runId).toString()).replaceAll("VAR2", ((Integer)runCrse).toString()).replaceAll("VAR3", runDate).replaceAll("VAR4", runTime);

            stmt.executeQuery(sql);
            dbcon.closeConnection();
        }
        catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
    }
}
