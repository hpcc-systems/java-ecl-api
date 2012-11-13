/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Join implements EclCommand {
    private String name;
    private String joinType;
    private String rightRecordSet;
    private String leftRecordSet;
    private String joinCondition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    public String getJoinCondition() {
        return joinCondition;
    }

    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getLeftRecordSet() {
        return leftRecordSet;
    }

    public void setLeftRecordSet(String leftRecordSet) {
        this.leftRecordSet = leftRecordSet;
    }

    public String getRightRecordSet() {
        return rightRecordSet;
    }

    public void setRightRecordSet(String rightRecordSet) {
        this.rightRecordSet = rightRecordSet;
    }

    
    @Override
    public String ecl() {
        return name + " := join(" + leftRecordSet + "," + rightRecordSet + "," + joinCondition + ","+ joinType + "); \r\n";
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
