/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rundb_v2.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Ben McCarthy
 */
public class GetSQL {

    private RunDB2Properties prp;

    public GetSQL() {
        init();
    }

    public final void init() {
        prp = new RunDB2Properties();
        prp.loadRunProp();
    }

    public String getString(String filename) {
        String command = new String();
        try {
        String dir = prp.getRunProp("rundb2.sql.dir");
        command = new String(Files.readAllBytes(Paths.get(dir + "/" + filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return command;
    }
}
