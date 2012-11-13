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
public class ML_BuildNaiveBayes implements EclCommand {

    private String name;
    
    private String dependentVar; // 1
    private String independentVar; // 2
   
    
    private String recordsetName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRecordsetName() {
        return recordsetName;
    }

    public void setRecordsetName(String recordsetName) {
        this.recordsetName = recordsetName;
    }



    public String getDependentVar() {
        return dependentVar;
    }

    public void setDependentVar(String dependentVar) {
        this.dependentVar = dependentVar;
    }

    public String getIndependentVar() {
        return independentVar;
    }

    public void setIndependentVar(String independentVar) {
        this.independentVar = independentVar;
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
        
         
        String ecl = this.name + " := ";
        
        //need to define and rework all the imports
      
        ecl += "ML.Classify.NaiveBayes.LearnD("+this.independentVar+","+this.dependentVar+");";

       
        //close out the ecl call
        ecl += "\r\n\r\n";
        
       

        
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
