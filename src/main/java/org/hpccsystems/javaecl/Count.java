/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author SimmonsJA
 */
public class Count implements EclCommand {

	private String name;
	private String recordset;
	private String expression;
	private String keyed;
	private String valuelist;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRecordSet() {
		return recordset;
	}
	
	public void setRecordSet(String recordset) {
		this.recordset = recordset;
	}
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public String getKeyed() {
		return keyed;
	}
	
	public void setKeyed(String keyed) {
		this.keyed = keyed;
	}
	
	public String getValueList() {
		return valuelist;
	}
	
	public void setValueList(String valuelist) {
		this.valuelist = valuelist;
	}
	
	@Override
	public String ecl() {
		// name := COUNT(recordset [, expression] [, keyed])
		// name := COUNT(valuelist)
		String ecl = name + " := COUNT(";
		
		if (recordset != null && recordset != "" && !recordset.equals("")) {
			ecl += recordset;
			if (expression != null && expression != "" && !expression.equals("")) {
				ecl += ", " + expression;
			}
			if (keyed != null && keyed != "" && !keyed.equals("")) {
				ecl += ", " + keyed;
			}
		}
		
		else if (valuelist != null && valuelist != "" && !valuelist.equals("")) {
			ecl += valuelist;
		}
		
		ecl += ");\r\n";
		
		return ecl;
	}
	
	@Override
	public CheckResult check() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
