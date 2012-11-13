/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author SimmonsJA
 */
public class Group implements EclCommand {
	
	private String name;
	private String recordset;
	private String breakCriteria;
	private Boolean isAll;
	private Boolean runLocal;
	
	
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
	
	public String getBreakCriteria() {
		return breakCriteria;
	}
	
	public void setBreakCriteria(String breakCriteria) {
		this.breakCriteria = breakCriteria;
	}
	
	public boolean getIsAll() {
		return isAll;
	}
	
	public String getIsAllString() {
		if (this.isAll)
			return "true";
		return "false";
	}
	
	public void setIsAll(boolean isAll) {
		this.isAll = isAll;
	}
	
	public void setIsAllString(String isAll) {
		if (isAll.equals("true"))
			this.isAll = true;
		else
			this.isAll = false;
	}
	
	public boolean getIsRunLocal() {
		return runLocal;
	}
	
	public String getIsRunLocalString() {
		if (this.runLocal)
			return "true";
		return "false";
	}
	
	public void setRunLocal(boolean runLocal) {
		this.runLocal = runLocal;
	}
	
	public void setRunLocalString(String runLocal) {
		if (runLocal.equals("true"))
			this.runLocal = true;
		else
			this.runLocal = false;
	}
	
	
	@Override
	public String ecl() {
		// name := GROUP(recordset [, breakcriteria [,ALL] ] [,LOCAL])
		String ecl = name + " := GROUP(" + recordset;
		
		if (breakCriteria != null && breakCriteria != "" && !breakCriteria.equals("")) {
			ecl += "," + breakCriteria;
			if (isAll != null && isAll) {
				ecl += ", ALL";
			}
		}
		
		if (runLocal != null && runLocal)
			ecl += ", LOCAL";
		
		ecl += ");\r\n";
		
		return ecl;
				
	}
	
	@Override
	public CheckResult check() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
