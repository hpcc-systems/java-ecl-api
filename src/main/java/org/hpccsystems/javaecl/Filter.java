/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Filter implements EclCommand {
    private String name;
    private String inDataset;
    private String filterStatement;


    public String getInDataset() {
        return inDataset;
    }

    public void setInRecordName(String inDSName) {
        this.inDataset = inDSName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
   
    public String getTransformFormat() {
        return filterStatement;
    }

    public void setFilterStatement(String filterStatement) {
        this.filterStatement = filterStatement;
    }

   
    
    @Override
    public String ecl() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(name).append(" := ").append(this.inDataset).append("(");
        sb.append(this.filterStatement);
        sb.append(");\r\n");
        return sb.toString();
        
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
