/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author SimmonsJA
 */
public class Fail implements EclCommand {
	
	private String name = "";
	private String errormessage = "";
	private String errorcode = "";

	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	

	@Override
	public String ecl() {
		// name := GROUP(recordset [, breakcriteria [,ALL] ] [,LOCAL])
		String ecl = "";
		boolean buildStatement = false;
		if(!name.equals("")){
			ecl += name + " := ";
		}
		ecl += "FAIL(";
		if(errormessage != null && !errormessage.equals("") && errorcode != null &&!errorcode.equals("")){
			ecl += errorcode + ", '" + errormessage + "'";
			buildStatement = true;
		}else if (errormessage != null && !errormessage.equals("")){
			ecl += "'" + errormessage + "'";
			buildStatement = true;
		}else if(errorcode != null){
			ecl += errorcode;
			buildStatement = true;
		}
		
		ecl += ")";
		if(!name.equals("")){
			ecl += ";\r\n";
		}
		if(buildStatement){
			return ecl;
		}else{
			return "";
		}
				
	}
	
	@Override
	public CheckResult check() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
