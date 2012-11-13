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
public class ML_BuildClassify implements EclCommand {

    private String name;
    
    private String dependentVar; // 1
    private String independentVar; // 2
   
    
    private String recordsetName;
    private String classifyType;
    private String learnType;

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

    public String getClassifyType() {
        return classifyType;
    }

    public void setClassifyType(String classifyType) {
        this.classifyType = classifyType;
    }

    public String getLearnType() {
        return learnType;
    }

    public void setLearnType(String learnType) {
        this.learnType = learnType;
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
        String type = "NaiveBayes";
        String lType = "LearnD";
        
        if(classifyType.equals("NaiveBayes")){
            type = "NaiveBayes";
            ecl = type + "Mod := ML.Classify.NaiveBayes;\r\n";
        }else if(classifyType.equals("Logistic Regression")){
            type = "Logistic";
            //EXPORT Logistic(REAL8 Ridge=0.00001, REAL8 Epsilon=0.000000001, UNSIGNED2 MaxIter=200) := MODULE(DEFAULT)
            ecl = type + "Mod := ML.Classify.Logistic(" + ridge + "," + epsilon + "," + maxIter + ");\r\n";
        }else if(classifyType.equals("Perceptron")){
            type = "Perceptron";
            // EXPORT Perceptron(UNSIGNED Passes,REAL8 Alpha = 0.1) := MODULE(DEFAULT)
            ecl = type + "Mod := ML.Classify.Perceptron(" + passes + "," + alpha + ");\r\n";
        }
        //LearnC
        //LearnD
        //LearnDConcat
        //LearnCConcat
        if(learnType.equals("LearnC")){
            lType = "LearnC";
        }else if(learnType.equals("LearnD")){
            lType = "LearnD";
        }else if(learnType.equals("LearnDConcat")){
            lType = "LearnDConcat";
        }else if(learnType.equals("LearnCConcat")){
            lType = "LearnCConcat";
        }
        
        
        ecl += this.name + " := ";
        
        ecl += type+"Mod." + lType + "("+this.independentVar+","+this.dependentVar+");";
        //close out the ecl call
        ecl += "\r\n\r\n";
        
       

        
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
