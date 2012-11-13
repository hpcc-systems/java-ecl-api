/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Distribute implements EclCommand {
    
    private String name;
    private String datasetName;
    private String expression;
    private String index;
    private String joinCondition;
    private String skew;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getJoinCondition() {
        return joinCondition;
    }

    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }
    
    

    public String getSkew() {
        return skew;
    }

    public void setSkew(String skew) {
        this.skew = skew;
    }
    
    
    @Override
    public String ecl() {
        StringBuilder sb = new StringBuilder();
        
        if (expression != null && expression.length() > 0) {
            sb.append(name).append(" := ").append("distribute(").append(datasetName).append(",").append(expression).append("); \r\n") ;
        } else if (index != null && index.length() > 0) {
            sb.append(name).append(" := ").append("distribute(").append(datasetName).append(",").append(index);
            if (joinCondition != null && joinCondition.length() > 0) {
                sb.append(",").append(joinCondition).append("); \r\n");
            } else {
                sb.append("); \r\n");
            }
        } else if (skew != null && skew.length() > 0) {
            sb.append(name).append(" := ").append("distribute(").append(datasetName).append(",").append(skew).append("); \r\n") ;
        }
        
        return sb.toString();
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
