/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rundb_v2.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ben McCarthy
 */
public class FieldValidator {

	private Logger logger = LogManager.getLogger(FieldValidator.class);

    private RunDB2Properties prp;
    private Pattern datePattern;
    private Matcher dateMatcher;
    private Pattern timePattern;
    private Matcher timeMatcher;
    private Pattern milesPattern;
    private Matcher milesMatcher;

    private static String DATE_PATTERN;
    private static String TIME_PATTERN;
    private static String MILES_PATTERN;
    
    
    public FieldValidator() {
    	logger.info("RunDB_2 Application FieldValidator() constructor 001 - Logging INFO");
        init();
    }
    
    public final void init() {
    	logger.debug("RunDB_2 Application init() method 001 - Logging DEBUG");
        prp = new RunDB2Properties();
        prp.loadRunProp();
    }
    
    public boolean validateDate(final String dateField) {
    	logger.debug("RunDB_2 Application validateDate() method 001 - Logging DEBUG");
        DATE_PATTERN = prp.getRunProp("date.regex");
        datePattern = Pattern.compile(DATE_PATTERN);
        dateMatcher = datePattern.matcher(dateField);
        return dateMatcher.matches();
    }

    public boolean validateTime(final String timeField) {
    	logger.debug("RunDB_2 Application validateTime() method 001 - Logging DEBUG");
        TIME_PATTERN = prp.getRunProp("time.regex");
        timePattern = Pattern.compile(TIME_PATTERN);
        timeMatcher = timePattern.matcher(timeField);
        return timeMatcher.matches();
    }

    public boolean validateMiles(final String milesField) {
    	logger.debug("RunDB_2 Application validateMiles() method 001 - Logging DEBUG");
        MILES_PATTERN = prp.getRunProp("miles.regex");
        milesPattern = Pattern.compile(MILES_PATTERN);
        milesMatcher = milesPattern.matcher(milesField);
        return milesMatcher.matches();
    }

}
