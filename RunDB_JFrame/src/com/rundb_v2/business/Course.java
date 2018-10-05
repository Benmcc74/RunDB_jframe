/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rundb_v2.business;

import com.rundb_v2.util.DbConnect;
import com.rundb_v2.util.GetSQL;
import com.rundb_v2.util.RunDB2Properties;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ben McCarthy
 */
public class Course {
    public Statement stmt;
    private int crseId;
    private String description;
    private Double mileage;
    private RunDB2Properties prp;
    private GetSQL getSql;

    public Course() {
        init();
    }

    public final void init() {
        prp = new RunDB2Properties();
        prp.loadRunProp();
        getSql = new GetSQL();
    }
    
    public void addCourse(String desc, Double mileage) {
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement();

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("AddCourse_sql.txt");
            String sql = sqlString.replaceAll("VAR1", desc).replaceAll("VAR2", mileage.toString());

            stmt.executeQuery(sql);
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
        
    }
    public void deleteCourse(int crseId) {
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement();

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("DeleteCourse_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crseId).toString());

            stmt.executeQuery(sql);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
    }

    public void updateCourse(int crseId, String desc, Double mileage) {
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement();

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("UpdateCourse_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crseId).toString()).replaceAll("VAR2", desc).replaceAll("VAR3", mileage.toString());

            stmt.executeQuery(sql);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
    }
    
    public ArrayList<ArrayList<String>> getCrseList() {

        ResultSet rs1;
        ArrayList<ArrayList<String>> table;
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement();

            /*Get SQL from file, and replace VARs, before executing */
            String sql = getSql.getString("CourseList_sql.txt");

            rs1 = stmt.executeQuery(sql);
            int columnCount = rs1.getMetaData().getColumnCount();
        
            table = new ArrayList<>(rs1.getRow());
            
            for(ArrayList<String> row; rs1.next(); table.add(row)) {
                row = new ArrayList<> (columnCount);
            
                for(int c = 1; c <= columnCount; ++c) 
                    row.add(rs1.getString(c).intern());
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
            table = null;
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
        return table;
    }    
}
