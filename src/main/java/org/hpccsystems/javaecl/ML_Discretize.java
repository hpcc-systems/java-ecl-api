/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

import java.io.*;


/**
 *
 * @author ChalaAX
 */
public class ML_Discretize implements EclCommand {

     private String name = "";
    private String recordName = "";
    private String cooccur = "";
    private String routine = "";
    private String recordsetName = "";

    public String getCooccur() {
        return cooccur;
    }

    public void setCooccur(String cooccur) {
        this.cooccur = cooccur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordsetName() {
        return recordsetName;
    }

    public void setRecordsetName(String recordsetName) {
        this.recordsetName = recordsetName;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }
    
  



    
    @Override
    public String ecl() {
        //ITERATE(recordset, transform [, LOCAL ] )
        
         
        String ecl = this.recordsetName + ":=";
        
        //need to define and rework all the imports
      
        ecl += "ML.Discretize."+this.routine+"("+this.recordName+","+this.cooccur+");";

       
        //close out the ecl call
        ecl += "\r\n\r\n";
        
       

        
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
