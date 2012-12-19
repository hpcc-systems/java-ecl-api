/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

import java.io.BufferedReader;
import java.io.FileReader;


/**
 *
 * @author ChalaAX
 */
public class ML_kmeans implements EclCommand {

    private String name;
    private String record;
    private String recordName;
   
    private String record2;
    private String record2Name;
    
    private String iterations;
    private String threshold;
    
    private String fieldType;
    private String fieldType2;
    private String mlFunction;
   
    
    private String recordsetName;

    public String getIterations() {
        return iterations;
    }

    public void setIterations(String iterations) {
        this.iterations = iterations;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }
    
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getRecord2() {
        return record2;
    }

    public void setRecord2(String record2) {
        this.record2 = record2;
    }

    public String getRecord2Name() {
        return record2Name;
    }

    public void setRecord2Name(String record2Name) {
        this.record2Name = record2Name;
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

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldType2() {
        return fieldType2;
    }

    public void setFieldType2(String fieldType2) {
        this.fieldType2 = fieldType2;
    }

    public String getMlFunction() {
        return mlFunction;
    }

    public void setMlFunction(String mlFunction) {
        this.mlFunction = mlFunction;
    }


    private static String openFile(String filePath){
        StringBuffer fileData = new StringBuffer(1000);
        System.out.println("++++++++++++++++ Open File: " + filePath);
         try{
        
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
         }catch (Exception e){//Catch exception if any
        System.err.println("Error: " + e.getMessage());
     }
        return fileData.toString();
    }


    
    @Override
    public String ecl() {
        //ITERATE(recordset, transform [, LOCAL ] )
        
         
        String ecl = "";
        
        //need to define and rework all the imports
        

        
       // ecl += recordName + " := DATASET([\r\n" + record + "]," + fieldType + ");\r\n\r\n";
      //  ecl += record2Name + " := DATASET([\r\n" + record2 + "]," + fieldType2 + ");\r\n\r\n";
        ecl += recordsetName + " := " + "KMeans" + "(" + recordName + "," + record2Name;
        
        if(iterations != null && iterations.length()>0)
            ecl += "," + iterations;
        
        if(threshold != null && threshold.length()>0)
            ecl += "," + threshold;
        
        ecl += ");\r\n\r\n";

       
        //close out the ecl call
        ecl += "\r\n\r\n";
        
       

        
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
