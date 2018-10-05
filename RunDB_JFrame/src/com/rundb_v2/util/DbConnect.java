/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rundb_v2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ben McCarthy
 */

public class DbConnect {
    public Connection con;
    private RunDB2Properties prp;

    public DbConnect() {
        init();
        try {
            String host = prp.getRunProp("db.host");
            String user = prp.getRunProp("db.user");
            String password = prp.getRunProp("db.password");
            con = DriverManager.getConnection(host, user, password);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    
    }

    public final void init() {
        prp = new RunDB2Properties();
        prp.loadRunProp();
    }

    public void closeConnection() {
        try {
            con.close();
        } catch(SQLException ex) {
            System.out.println("Cannot close Database connection");
            throw new RuntimeException(ex);
        }
    }
}
