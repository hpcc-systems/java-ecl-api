/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Table implements EclCommand {

    private String name;
    private String recordset;
    private String format;
    private String expression;
    
    private String size; //FEW,MANY
    private Boolean isUnsorted = false;
    private Boolean runLocal = false;
    private Boolean isKeyed = false;
    private Boolean isMerge = false;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getIsKeyed() {
        return isKeyed;
    }

    public void setIsKeyed(Boolean isKeyed) {
        this.isKeyed = isKeyed;
    }

    public Boolean getRunLocal() {
        return runLocal;
    }

    public void setRunLocal(Boolean LOCAL) {
        this.runLocal = LOCAL;
    }

    public Boolean getisMerge() {
        return isMerge;
    }

    public void setIsMerge(Boolean MERGE) {
        this.isMerge = MERGE;
    }

    public Boolean getIsUnsorted() {
        return isUnsorted;
    }

    public void setIsUnsorted(Boolean UNSORTED) {
        this.isUnsorted = UNSORTED;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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
    



    

    @Override
    public String ecl() {
        //TABLE(recordset, format [,expression [,FEW | MANY] [, UNSORTED]] [,LOCAL] [, KEYED ] [, MERGE ] )
       
        String formatECL = name+"Format := RECORD \r\n";
        formatECL += format;
        formatECL += "\r\n";
        formatECL += "END; \r\n";
        
        
        String ecl = formatECL + name + " := TABLE(" + recordset + "," + name+"Format";
        //add optional fields if its set
        if (expression != null && !expression.equals("")) {
           ecl += "," + expression;
            if(size != null && !size.equals("")){
                ecl += "," + size;
            }
            if (isUnsorted) {
                ecl += ", UNSORTED";
                 
            }
        }
        if (runLocal) {
            ecl += ", LOCAL";

         }
        if (isKeyed) {
            ecl += ", KEYED";

         }
        if (isMerge) {
            ecl += ", MERGE";
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
