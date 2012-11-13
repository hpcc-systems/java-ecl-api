/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class CheckResult {
    private String message;
    private boolean isValid;

    public CheckResult(String message, boolean isValid) {
        this.message = message;
        this.isValid = isValid;
    }

    
    
    public boolean isIsValid() {
        return isValid;
    }

    public String getMessage() {
        return message;
    }
    
    
}
