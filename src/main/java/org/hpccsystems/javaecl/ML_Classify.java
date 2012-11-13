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
public class ML_Classify implements EclCommand {

    private String name;
    
    private String independentVar; // 1
    private String model; // 2
    private String classifyType;
    private String dataType;
   
    
    private String recordsetName;
    
    private String ridge;
    private String epsilon;
    private String maxIter;
    
    private String passes;
    private String alpha;

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



    public String getIndependentVar() {
        return independentVar;
    }

    public void setIndependentVar(String independentVar) {
        this.independentVar = independentVar;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getClassifyType() {
        return classifyType;
    }

    public void setClassifyType(String classifyType) {
        this.classifyType = classifyType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    public String getRidge() {
		return ridge;
	}

	public void setRidge(String ridge) {
		this.ridge = ridge;
	}

	public String getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(String epsilon) {
		this.epsilon = epsilon;
	}

	public String getMaxIter() {
		return maxIter;
	}

	public void setMaxIter(String maxIter) {
		this.maxIter = maxIter;
	}

	public String getPasses() {
		return passes;
	}

	public void setPasses(String passes) {
		this.passes = passes;
	}

	public String getAlpha() {
		return alpha;
	}

	public void setAlpha(String alpha) {
		this.alpha = alpha;
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
        String type = "ClassifyD";
        if(dataType.equals("ClassifyD")){
            type = "ClassifyD";
        }else if(dataType.equals("ClassifyC")){
            type = "ClassifyC";
        }
        
        if(classifyType.equals("NaiveBayes")){
            ecl += "NaiveBayesMod := ML.Classify.NaiveBayes;\r\n";
            ecl += this.name + " := ";
            ecl += "NaiveBayesMod."+type+"("+this.independentVar+ "," + this.model + ");";

        }else if(classifyType.equals("Logistic Regression")){
        	//EXPORT Logistic(REAL8 Ridge=0.00001, REAL8 Epsilon=0.000000001, UNSIGNED2 MaxIter=200) := MODULE(DEFAULT)
            
            ecl += "LogisticMod := ML.Classify.Logistic(" + ridge + "," + epsilon + "," + maxIter + "); \r\n";
            ecl += this.name + " := ";
            ecl += "LogisticMod."+type+"("+this.independentVar+ "," + this.model + ");";

        }else if(classifyType.equals("Perceptron")){
        	// EXPORT Perceptron(UNSIGNED Passes,REAL8 Alpha = 0.1) := MODULE(DEFAULT)
            ecl += "PerceptronMod := ML.Classify.Perceptron(" + passes + "," + alpha + "); \r\n";
            ecl += this.name + " := ";
            ecl += "PerceptronMod."+type+"("+this.independentVar+ "," + this.model + ");";

        }
        
        
        
      
        //Perception
       
        //close out the ecl call
        ecl += "\r\n\r\n";
        
       

        
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
