package org.hpccsystems.javaecl;

public class Merge implements EclCommand {

	private String result; // Output Recordset
	private String recordsetList;
	private String recordsetSet;
	private String fieldList;
	private Boolean dedup;
	private Boolean runLocal;
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getRecordsetList() {
		return recordsetList;
	}
	
	public void setRecordsetList(String recordsetList) {
		this.recordsetList = recordsetList;
	}
	
	public String getRecordsetSet() {
		return recordsetSet;
	}
	
	public void setRecordsetSet(String recordsetSet) {
		this.recordsetSet = recordsetSet;
	}
	
	public String getFieldList() {
		return fieldList;
	}
	
	public void setFieldList(String fieldList) {
		this.fieldList = fieldList;
	}
	
	public boolean isDedup() {
		return dedup;
	}
	
	public void setDedup(boolean dedup) {
		this.dedup = dedup;
	}
	
	public String isDedupString() {
		if (dedup)
			return "true";
		return "false";
	}
	
	public void setDedupString(String dedup) {
		if (dedup.equals("true")) {
			this.dedup = true;
		} else {
			this.dedup = false;
		}
	}
	
	public boolean runLocal() {
		return runLocal;
	}
	
	public void setRunLocal(boolean runLocal) {
		this.runLocal = runLocal;
	}
	
	public String runLocalString() {
		if (runLocal)
			return "true";
		return "false";
	}
	
	public void setRunLocalString(String runLocal) {
		if (runLocal.equals("true")) {
			this.runLocal = true;
		} else {
			this.runLocal = false;
		}
	}
	
	@Override
	public String ecl() {
		//MERGE(recordsetlist  , SORTED( fieldlist ) [, DEDUP ] [, LOCAL ] )
		//MERGE(recordsetset , fieldlist , SORTED( fieldlist ) [, DEDUP ] [, LOCAL ] )
		String ecl = result + ":= MERGE(";
		if (recordsetList != null && recordsetList != "" && !recordsetList.equals("")) {
			ecl += recordsetList;
		} else if (recordsetSet != null && recordsetSet != "" && !recordsetSet.equals("")) {
			ecl += recordsetSet + ", " + fieldList;
		}
		ecl += ", SORTED(" + fieldList + ")";
		if (dedup != null && dedup) {
			ecl += ", DEDUP";
		}
		if (runLocal != null && runLocal) {
			ecl += ", LOCAL";
		}
		
		ecl += ");\r\n";
		return ecl;
	}
	
	@Override
	public CheckResult check() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
