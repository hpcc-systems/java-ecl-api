/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Project implements EclCommand {
    private String name;
    private String parameterName;
    private boolean declareCounter;
    private String inRecordName;
    private String outRecordName;
    private String outRecordFormat;
    private String transformName;
    private String transformFormat;
    private String recordFormatString;
    
    public boolean isDeclareCounter() {
        return declareCounter;
    }

    public void setDeclareCounter(boolean declareCounter) {
        this.declareCounter = declareCounter;
    }

    public String getInRecordName() {
        return inRecordName;
    }

    public void setInRecordName(String inRecordName) {
        this.inRecordName = inRecordName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutRecordFormat() {
        return outRecordFormat;
    }

    public void setOutRecordFormat(String outRecordFormat) {
        this.outRecordFormat = outRecordFormat;
    }

    public String getOutRecordName() {
        return outRecordName;
    }

    public void setOutRecordName(String outRecordName) {
        this.outRecordName = outRecordName;
    }

    public String getTransformFormat() {
        return transformFormat;
    }

    public void setTransformFormat(String transformFromat) {
        this.transformFormat = transformFromat;
    }

    public String getTransformName() {
        return transformName;
    }

    public void setTransformName(String transformName) {
        this.transformName = transformName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    
    public String getRecordFormatString() {
        return recordFormatString;
    }

    public void setRecordFormatString(String recordFormatString) {
        this.recordFormatString = recordFormatString;
    }
    
    @Override
    public String ecl() {
        StringBuilder sb = new StringBuilder();

        sb.append(outRecordName).append(" := ").append(" record \r\n");
        sb.append(outRecordFormat).append(" \r\n");
        sb.append("end; \r\n");

        sb.append(outRecordName).append(" ").append(transformName).append("(").append(inRecordName).append(" ").append(parameterName);
        if (declareCounter) {
            sb.append(", integer count) := transform \r\n");
        } else {
            sb.append(") := transform \r\n");
        }
        sb.append(transformFormat).append(" \r\n");
        sb.append("end; \r\n");
        
        sb.append(name).append(" := project(").append(inRecordName).append(",").append(transformName).append("(left");
        if (declareCounter) {
            sb.append(", counter)); \r\n");
        } else {
            sb.append(")); \r\n");
        }
        
        return sb.toString();
        
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
