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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ben McCarthy
 */
public class CourseRecord {
	private Logger logger = LogManager.getLogger(CourseRecord.class);

    private int crseId;
    private String crseBestTime;
    private String crseWorstTime;
    private String crseAvgTime;
    private ArrayList<ArrayList<String>> crseData;
    private Statement stmt;
    private RunDB2Properties prp;
    private GetSQL getSql;
    
    public CourseRecord() {
    	logger.info("RunDB_2 Application CourseRecord() constructor 001 - Logging INFO");
        init();
    }

    public final void init() {
    	logger.debug("RunDB_2 Application init() method 001 - Logging DEBUG");
        prp = new RunDB2Properties();
        prp.loadRunProp();
        getSql = new GetSQL();
    }
    
    public ArrayList<String> calcCrseBestTime(int crseId) {
    	logger.debug("RunDB_2 Application calcCrseBestTime() method 001 - Logging DEBUG");
        ResultSet rs1;
        String bestTime = null;
        String bestDate = null;
        ArrayList<String> bestRecs = new ArrayList<String>();
        DbConnect dbcon = new DbConnect();
        
        try {
            stmt = dbcon.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("BestCourseRec_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crseId).toString());

            rs1 = stmt.executeQuery(sql);
            if(rs1.first()){
                bestDate = rs1.getString("FastestDate");
                bestTime = rs1.getString("FastestTime");
                if(bestTime != null && !bestTime.isEmpty()) {
                } else {
                    bestTime = "NoData";
                    bestDate = "NoData";
                }
            } else {
                bestTime = "NoData";
                bestDate = "NoData";
            }
        } catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
            if (bestDate != null) {
                bestRecs.add(bestDate);
                bestRecs.add(bestTime);
            }
            return bestRecs;
        }
        
    }

    public ArrayList<String> calcCrseWorstTime(int crseId) {
    	logger.debug("RunDB_2 Application calcCrseWorstTime() method 001 - Logging DEBUG");
        ResultSet rs2;
        String worstTime = null;
        String worstDate = null;
        ArrayList<String> worstRecs = new ArrayList<String>();
        DbConnect dbcon = new DbConnect();
        
        try {
            stmt = dbcon.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("WorstCourseRec_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crseId).toString());
            
            rs2 = stmt.executeQuery(sql);
            if(rs2.first()){
                worstDate = rs2.getString("WorstDate");
                worstTime = rs2.getString("WorstTime");
                if(worstTime != null && !worstTime.isEmpty()) {
                } else {
                    worstTime = "NoData";
                    worstDate = "NoData";
                }
            } else {
                worstTime = "NoData";
                worstDate = "NoData";
            }
        } catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
            if (worstDate != null) {
                worstRecs.add(worstDate);
                worstRecs.add(worstTime);
            }
            return worstRecs;
        }

    }


    public String calcCrseAvgTime(int crseId) {
    	logger.debug("RunDB_2 Application calcCrseAvgTime() method 001 - Logging DEBUG");
        ResultSet rs3;
        String crseAvg = null;
        DbConnect dbcon = new DbConnect();

        try {
            stmt = dbcon.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("AvgCourseRec_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crseId).toString());

            rs3 = stmt.executeQuery(sql);
            if(rs3.first()){
                crseAvg = rs3.getString("AvgTime");
                if(crseAvg != null && !crseAvg.isEmpty()) {
                } else {
                    crseAvg = "NoData";
                }
            } else {
                crseAvg = "NoData";
            }
        } catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
            return crseAvg;
        }

    }

    public ArrayList<ArrayList<String>> getCrseData(int crseId) {
    	logger.debug("RunDB_2 Application getCrseData() method 001 - Logging DEBUG");

        ResultSet rs4;
        ArrayList<ArrayList<String>> table = null;
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement();

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("CourseData_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crseId).toString());

            rs4 = stmt.executeQuery(sql);
            int columnCount = rs4.getMetaData().getColumnCount();
        
            table = new ArrayList<>(rs4.getRow());
            
            for(ArrayList<String> row; rs4.next(); table.add(row)) {
                row = new ArrayList<> (columnCount);
            
                for(int c = 1; c <= columnCount; ++c) 
                    row.add(rs4.getString(c).intern());
            }
        } catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
            return table;
        }
        
    }
    
    public String calcTotalRuns(int crseId) {
    	logger.debug("RunDB_2 Application calcTotalRuns() method 001 - Logging DEBUG");
        ResultSet rs5;
        String crseTtl;
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("CourseTtlRuns_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crseId).toString());

            rs5 = stmt.executeQuery(sql);
            if(rs5.first()){
                crseTtl = rs5.getString("TtlRuns");
                if(crseTtl != null && !crseTtl.isEmpty()) {
                } else {
                    crseTtl = "NoData";
                }
            } else {
                crseTtl = "NoData";
            }
        } catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
        return crseTtl;
    }
    public String getCourseDesc(int crseId) {
    	logger.debug("RunDB_2 Application getCourseDesc() method 001 - Logging DEBUG");
        ResultSet rs6;
        String crseDesc;
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("CourseDesc_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crseId).toString());

            rs6 = stmt.executeQuery(sql);
            if(rs6.first()){
                crseDesc = rs6.getString("Description");
                if(crseDesc != null && !crseDesc.isEmpty()) {
                } else {
                    crseDesc = "NoData";
                }
            } else {
                crseDesc = "NoData";
            }
        } catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
        return crseDesc;
    }

    public String getCourseMileage(int crseId) {
    	logger.debug("RunDB_2 Application getCourseMileage() method 001 - Logging DEBUG");
        ResultSet rs7;
        String crseMiles;
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            /*Get SQL from file, and replace VARs, before executing */
            String sqlString = getSql.getString("CourseMiles_sql.txt");
            String sql = sqlString.replaceAll("VAR1", ((Integer)crseId).toString());

            rs7 = stmt.executeQuery(sql);
            if(rs7.first()){
                crseMiles = rs7.getString("Mileage");
                if(crseMiles != null && !crseMiles.isEmpty()) {
                } else {
                    crseMiles = "NoData";
                }
            } else {
                crseMiles = "NoData";
            }
        } catch (SQLException err) {
        	logger.error(err.getMessage());
            System.out.println(err.getMessage());
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
        return crseMiles;
        
    }
}
