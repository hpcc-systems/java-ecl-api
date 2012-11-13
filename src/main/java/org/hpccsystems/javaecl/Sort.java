/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Sort implements EclCommand {

    private String name;
    private String datasetName;
    private String fields;//Comma separated list of fieldNames. a "-" prefix to the field name will indicate descending order
    private boolean runLocal;

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRunLocal() {
        return runLocal;
    }

    public void setRunLocal(boolean runLocal) {
        this.runLocal = runLocal;
    }

    @Override
    public String ecl() {
        String ecl = name + " := " + "sort(" + datasetName + ", " + fields;
        if (runLocal) {
            return ecl + ", local); \r\n";
        } else {
            return ecl + "); \r\n";
        }
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
