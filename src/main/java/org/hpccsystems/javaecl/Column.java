/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Column {
    private String name;
    private String value;

    public Column(String name, String value) {
        this.name = name;
        this.value = value;
    }

    
    
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
   
    
}
