/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Rollup implements EclCommand {

    private String name;
    private String recordset;
    private String recordFormat;
    private String condition;
    private String transformName;
    private String transform;
    private String fieldlist;
    private String group;
    //private String outputFormat;
    private Boolean runLocal;//optional
    
    
   // public String getOutputFormat() {
	//	return outputFormat;
	//}

	//public void setOutputFormat(String outputFormat) {
	//	this.outputFormat = outputFormat;
	//}

	



    public String getRecordFormat() {
		return recordFormat;
	}

	public void setRecordFormat(String recordFormat) {
		this.recordFormat = recordFormat;
	}

	public String getTransformName() {
        return transformName;
    }

    public void setTransformName(String transformName) {
        this.transformName = transformName;
    }

    
    
    public String getGroup() {
        return group;
    }

    public void setGroup(String GROUP) {
        this.group = GROUP;
    }

    public Boolean getRunLocal() {
        return runLocal;
    }

    public void setRunLocal(Boolean LOCAL) {
        this.runLocal = LOCAL;
    }



    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFieldlist() {
        return fieldlist;
    }

    public void setFieldlist(String fieldlist) {
        this.fieldlist = fieldlist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecordset() {
        return recordset;
    }

    public void setRecordset(String recordset) {
        this.recordset = recordset;
    }

    public String getTransform() {
        return transform;
    }

    public void setTransform(String transform) {
        this.transform = transform;
    }

    

    @Override
    public String ecl() {
        
        String ecl = "";

        
       // ecl += transformName + "RECNAME := record \r\n"; 
       // ecl += this.getOutputFormat() + "\r\n end;\r\n\r\n";
        
        ecl += recordFormat + " " + transformName + "(" + recordFormat + " L," + recordFormat + " R) := TRANSFORM \r\n" + transform + "\r\n" + "END; \r\n";
        
        ecl += name + ":=ROLLUP(" + recordset;
        //add local if its set if not its optional
        //ROLLUP(recordset, condition, transform [, LOCAL] )
        
        String transformCall = transformName + "(LEFT,RIGHT)";
        if(condition != null && !condition.equals("")){
            ecl+= "," +condition;

            ecl += "," + transformCall;
            if (runLocal != null && runLocal) {
                    ecl += ", local";
                }
        
        }else{
            if (group != null && !group.equals("")) {
                //ROLLUP(recordset, GROUP, transform )
                ecl += "," + group;
                    ecl += "," + transformCall;
            }else{
                //ROLLUP(recordset, transform, fieldlist [, LOCAL] )
                    ecl += "," + transformCall;
                    if (fieldlist != null && !fieldlist.equals("")) {
                       ecl += "," + fieldlist;

                        if (runLocal != null && runLocal) {
                            ecl += ", local";
                        }
                    }
            }
        }
        //close out the ecl call
        ecl += ");\r\n";
        
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
