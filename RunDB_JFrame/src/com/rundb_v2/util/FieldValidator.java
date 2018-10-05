/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rundb_v2.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ben McCarthy
 */
public class FieldValidator {

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
        init();
    }
    
    public final void init() {
        prp = new RunDB2Properties();
        prp.loadRunProp();
    }
    
    public boolean validateDate(final String dateField) {
        DATE_PATTERN = prp.getRunProp("date.regex");
        datePattern = Pattern.compile(DATE_PATTERN);
        dateMatcher = datePattern.matcher(dateField);
        return dateMatcher.matches();
    }

    public boolean validateTime(final String timeField) {
        TIME_PATTERN = prp.getRunProp("time.regex");
        timePattern = Pattern.compile(TIME_PATTERN);
        timeMatcher = timePattern.matcher(timeField);
        return timeMatcher.matches();
    }

    public boolean validateMiles(final String milesField) {
        MILES_PATTERN = prp.getRunProp("miles.regex");
        milesPattern = Pattern.compile(MILES_PATTERN);
        milesMatcher = milesPattern.matcher(milesField);
        return milesMatcher.matches();
    }

}
