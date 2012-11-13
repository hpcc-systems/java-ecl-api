/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Execute implements EclCommand {
    String definitionName;

    public String getDefinitionName() {
        return definitionName;
    }

    public void setDefinitionName(String attribute) {
        this.definitionName = attribute;
    }

    @Override
    public String ecl() {
        return "";
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
