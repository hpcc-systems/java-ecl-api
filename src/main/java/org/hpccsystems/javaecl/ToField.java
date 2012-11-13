/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class ToField implements EclCommand {
    private String name;
    private String inDS;
    private String outDS;
    
    private String idColumn;
    private String parseToNumeric;

    public String getInDS() {
        return inDS;
    }

    public void setInDS(String inDS) {
        this.inDS = inDS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutDS() {
        return outDS;
    }

    public void setOutDS(String outDS) {
        this.outDS = outDS;
    }

    public String getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
    }

    public String getParseToNumeric() {
        return parseToNumeric;
    }

    public void setParseToNumeric(String parseToNumeric) {
        this.parseToNumeric = parseToNumeric;
    }

   
    
    @Override
    public String ecl() {
        String rStr = "ml.ToField(" + inDS + "," + outDS;
        
        if(!idColumn.equals("")){
            rStr += "," + idColumn;
        }
        if(!parseToNumeric.equals("")){
            rStr += ", '" + parseToNumeric + "'" ;
        }
        
        rStr += ");\r\n";
        
        return rStr;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
