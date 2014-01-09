package org.hpccsystems.javaecl;

public class Limit implements EclCommand {

	private String result;
	private String recordset;
	private String maxRecs;
	private String failClause;
	private Boolean keyed;
	private Boolean count;
	private Boolean skip;
	private String onFailTransform;
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getRecordset() {
		return recordset;
	}
	
	public void setRecordset(String recordset) {
		this.recordset = recordset;
	}
	
	public String getMaxRecs() {
		return maxRecs;
	}
	
	public void setMaxRecs(String maxRecs) {
		this.maxRecs = maxRecs;
	}
	
	public String getFailClause() {
		return failClause;
	}
	
	public void setFailClause(String failClause) {
		this.failClause = failClause;
	}
	
	public boolean getKeyed() {
		return keyed;
	}
	
	public void setKeyed(boolean keyed) {
		this.keyed = keyed;
	}
	
	public String getKeyedString() {
		if (keyed) {
			return "true";
		} else {
			return "false";
		}
	}
	
	public void setKeyedString(String keyed) {
		if (keyed.equals("true")) {
			this.keyed = true;
		} else {
			this.keyed = false;
		}
	}
	
	public boolean getCount() {
		return count;
	}
	
	public void setCount(boolean count) {
		this.count = count;
	}
	
	public String getCountString() {
		if (count) {
			return "true";
		} else {
			return "false";
		}
	}
	
	public void setCountString(String count) {
		if (count.equals("true")) {
			this.count = true;
		} else {
			this.count = false;
		}
	}
	
	public boolean getSkip() {
		return skip;
	}
	
	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	
	public String getSkipString() {
		if (skip) {
			return "true";
		} else {
			return "false";
		}
	}
	
	public void setSkipString(String skip) {
		if (skip.equals("true")) {
			this.skip = true;
		} else {
			this.skip = false;
		}
	}
	
	public String getOnFailTransform() {
		return onFailTransform;
	}
	
	public void setOnFailTransform(String onFailTransform) {
		this.onFailTransform = onFailTransform;
	}
	
	@Override
	public String ecl() {
		// LIMIT(recset, maxrecs [, failclause ] [, KEYED [, COUNT ] ] [, SKIP ] )
		// LIMIT(recset, maxrecs [, ONFAIL(transform) ] [, KEYED [, COUNT ] ] 
		boolean hasFailClause = false;
		String ecl = result + " := LIMIT(" + recordset + ", " + maxRecs;
		if (failClause != null && failClause != "" && !failClause.equals("")) {
			ecl += ", " + failClause + "";
			hasFailClause = true;
		}
		if (!hasFailClause && onFailTransform != null && onFailTransform != "" && !onFailTransform.equals("")) {
			ecl+= ", ONFAIL(" + onFailTransform + ")";
		}
		if (keyed != null && keyed) {
			ecl += ", KEYED";
			if (count != null && count) {
				ecl += ", COUNT";
			}
		}
		if (hasFailClause && skip != null && skip) {
			ecl += ", SKIP";
		}
		
		ecl += ");\r\n";
		return ecl;
	}
	
	@Override
	public CheckResult check() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
