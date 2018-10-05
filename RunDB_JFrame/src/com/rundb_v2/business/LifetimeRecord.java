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
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Ben McCarthy
 */
public class LifetimeRecord {
    private Statement stmt;
    private RunDB2Properties prp;
    private GetSQL getSql;
    
    public LifetimeRecord() {
        init();
    }

    public final void init() {
        prp = new RunDB2Properties();
        prp.loadRunProp();
        getSql = new GetSQL();
    }

    public String getTotalRuns() {
        ResultSet rs1;
        String ttlRuns;
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /*Get SQL from file, and replace VARs, before executing */
            String sql = getSql.getString("LifeTtlRuns_sql.txt");

            rs1 = stmt.executeQuery(sql);
            if(rs1.first()){
                ttlRuns = rs1.getString("TotalRuns");
                if(ttlRuns != null && !ttlRuns.isEmpty()) {
                } else {
                    ttlRuns = "NoData";
                }
            } else {
                ttlRuns = "NoData";
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            ttlRuns = null;
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
        return ttlRuns;
    }
    
    public String getTotalMiles() {
        ResultSet rs2;
        String ttlMiles;
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /*Get SQL from file, and replace VARs, before executing */
            String sql = getSql.getString("LifeTtlMiles_sql.txt");

            rs2 = stmt.executeQuery(sql);

            if(rs2.first()){
                ttlMiles = rs2.getString("TotalMiles");
                if(ttlMiles != null && !ttlMiles.isEmpty()) {
                } else {
                    ttlMiles = "NoData";
                }
            } else {
                ttlMiles = "NoData";
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            ttlMiles = null;
            throw new RuntimeException(err);
        } finally {
            if (dbcon != null) {
                dbcon.closeConnection();
            }
        }
        return ttlMiles;
    }
    public ArrayList<ArrayList<String>> getLifetimeData() {
        ResultSet rs3;
        ArrayList<ArrayList<String>> table;
        DbConnect dbcon = new DbConnect();
        try {
            stmt = dbcon.con.createStatement();

            /*Get SQL from file, and replace VARs, before executing */
            String sql = getSql.getString("LifeData_sql.txt");

            rs3 = stmt.executeQuery(sql);
            int columnCount = rs3.getMetaData().getColumnCount();
        
            table = new ArrayList<>(rs3.getRow());
            
            for(ArrayList<String> row; rs3.next(); table.add(row)) {
                row = new ArrayList<> (columnCount);
            
                for(int c = 1; c <= columnCount; ++c) 
                    row.add(rs3.getString(c).intern());
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

    public ArrayList<ArrayList<String>> getLifetimeRecs(ArrayList<ArrayList<String>> dataTable) {
        ArrayList<ArrayList<String>> recsTable = new ArrayList<>();
        ArrayList<Integer> uniqueIds = new ArrayList<>();
        String[] tempCrseData = {"00","00:00:00","99991231","00:00:00","99991231","0","00:00:00","",""};
        Integer ttlCtr = 0;
        Long ttlSum = 0L;
        String crseDesc = "";
        String crseMiles = "";
        
        
        //get all distinct ID's from dataTable to use in for loop
        for(int i = 0; i < dataTable.size(); i++) {
            if(!uniqueIds.contains(Integer.valueOf(dataTable.get(i).get(1))))
                uniqueIds.add(Integer.valueOf(dataTable.get(i).get(1)));
        }
        Collections.sort(uniqueIds);
        
        for (Integer uniqueId : uniqueIds) {
            for (ArrayList<String> dataRow : dataTable) {
                if(uniqueId.equals(Integer.valueOf(dataRow.get(1)))) {
                    ttlCtr +=1;
                    ttlSum += stringToDate(dataRow.get(2).substring(0,8));
                    tempCrseData[0] = uniqueId.toString();
                    if(tempCrseData[1].equals("00:00:00")) {
                        tempCrseData[1] = dataRow.get(2).substring(0,8);
                        tempCrseData[2] = dataRow.get(0);
                        tempCrseData[3] = dataRow.get(2).substring(0,8);
                        tempCrseData[4] = dataRow.get(0);
                    } else {
                        if(stringToDate(dataRow.get(2).substring(0,8)).compareTo(stringToDate(tempCrseData[1])) < 0) {
                            tempCrseData[1] = dataRow.get(2).substring(0,8);
                            tempCrseData[2] = dataRow.get(0);
                        } else {
                            if(stringToDate(dataRow.get(2).substring(0,8)).compareTo(stringToDate(tempCrseData[3])) > 0) {
                                tempCrseData[3] = dataRow.get(2).substring(0,8);
                                tempCrseData[4] = dataRow.get(0);
                            }        
                        }
                    } 
                    tempCrseData[7] = dataRow.get(3);
                    tempCrseData[8] = dataRow.get(4);
                }
            }
            
            tempCrseData[5] = ttlCtr.toString();
            tempCrseData[6] = calcAvgTime(ttlSum, ttlCtr);
            recsTable.add(new ArrayList<>(Arrays.asList(tempCrseData)));
            tempCrseData[0] = "00";
            tempCrseData[1] = "00:00:00";
            tempCrseData[2] = "99991231";
            tempCrseData[3] = "00:00:00";
            tempCrseData[4] = "99991231";
            tempCrseData[5] = "0";
            tempCrseData[6] = "00:00:00";
            tempCrseData[7] = "";
            tempCrseData[8] = "";
            ttlCtr = 0;
            ttlSum = 0L;
        }
        return recsTable;
    }
    
    private Long stringToDate(String str) {
        Long milliSecs;
        milliSecs = Duration.between(LocalTime.MIN,LocalTime.parse(str)).toMillis();
        return milliSecs;
    }

    private String calcAvgTime(long ttlMilliSecs, int freq) {
        if(freq == 0) {
            return "00:00:00";
        } else {
            long avgMilliSecs;
            avgMilliSecs = ttlMilliSecs/freq;
            String hms = String.format("%02d:%02d:%02d", 
                    avgMilliSecs/(3600*1000),
                    avgMilliSecs/(60*1000) % 60,
                    avgMilliSecs/1000 % 60 );
            return hms;
        }
    }
    
}
