/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Iterate implements EclCommand {

    private String name;
    private String transformName;
    private String recordsetName;
    private String dataset;
    private Boolean runLocal;
    private String transform;
   
    

    public String getTransform() {
		return transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getRecordsetName() {
        return recordsetName;
    }

    public void setRecordsetName(String recordsetName) {
        this.recordsetName = recordsetName;
    }

    public Boolean getRunLocal() {
        return runLocal;
    }

    public void setRunLocal(Boolean runLocal) {
        this.runLocal = runLocal;
    }

    public String getTransformName() {
        return transformName;
    }

    public void setTransformName(String transformName) {
        this.transformName = transformName;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    

    @Override
    public String ecl() {
        //ITERATE(recordset, transform [, LOCAL ] )
        
         
        String ecl = "";


        //ecl += returnType + " " + transformName + "(" + returnType + " L, " + returnType + " R) := TRANSFORM \r\n" + transform + "\r\nEND;\r\n\r\n";
        if(transformName != null && !transformName.equals("")){
            ecl += dataset + " " + transformName + "(" + dataset + " L," + dataset + " R) := TRANSFORM \r\n";
            ecl += transform;
            ecl += "\r\nEND;\r\n\r\n";
        }
        ecl += recordsetName + " := ITERATE(" + dataset + "," + transformName + "(LEFT,RIGHT)";
        //add local if its set if not its optional
        if (runLocal != null && runLocal) {
           ecl += ", local";
        }
        //close out the ecl call
        ecl += ");\r\n\r\n";
        
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
