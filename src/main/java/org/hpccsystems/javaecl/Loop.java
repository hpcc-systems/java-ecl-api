/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author SimmonsJA
 */
public class Loop implements EclCommand {

	private String result;
	private String recordset; // "dataset"
	private String loopCount;
	private String loopBody;
	private String iterations;
	private String iterationList;
	private String dfault;
	private String loopFilter;
	private String loopCondition;
	private String rowFilter;
	
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
	
	public String getLoopCount() {
		return loopCount;
	}
	
	public void setLoopCount(String loopCount) {
		this.loopCount = loopCount;
	}
	
	public String getLoopBody() {
		return loopBody;
	}
	
	public void setLoopBody(String loopBody) {
		this.loopBody = loopBody;
	}
	
	public String getIterations() {
		return iterations;
	}
	
	public void setIterations(String iterations) {
		this.iterations = iterations;
	}
	
	public String getIterationList() {
		return iterationList;
	}
	
	public void setIterationList(String iterationList) {
		this.iterationList = iterationList;
	}
	
	public String getDefault() {
		return dfault;
	}
	
	public void setDefault(String dfault) {
		this.dfault = dfault;
	}
	
	public String getLoopFilter() {
		return loopFilter;
	}
	
	public void setLoopFilter(String loopFilter) {
		this.loopFilter = loopFilter;
	}
	
	public String getLoopCondition() {
		return loopCondition;
	}
	
	public void setLoopCondition(String loopCondition) {
		this.loopCondition = loopCondition;
	}
	
	public String getRowFilter() {
		return rowFilter;
	}
	
	public void setRowFilter(String rowFilter) {
		this.rowFilter = rowFilter;
	}
	
	
	@Override
	public String ecl() {
		//result := LOOP( dataset, loopcount, loopbody  [, PARALLEL( iterations | iterationlist [, default ] ) ] )
		//result := LOOP( dataset, loopcount, loopfilter, loopbody  [, PARALLEL( iterations | iterationlist [, default ] ) ] )
		//result := LOOP( dataset, loopfilter, loopbody )
		//result := LOOP( dataset, loopcondition, loopbody )
		//result := LOOP( dataset, loopcondition, rowfilter, loopbody )
		String ecl = result + " := LOOP(" + recordset;
		if (loopCount != null && loopCount != "" && !loopCount.equals("")) {
			ecl += ", " + loopCount;
			if (loopFilter != null && loopFilter != "" && !loopFilter.equals("")) {
				ecl += ", " + loopFilter;
			}
			ecl += ", " + loopBody;
			if (iterations != null && iterations != "" && !iterations.equals("")) {
				ecl += ", PARALLEL(" + iterations;
				if (dfault != null && dfault != "" && !dfault.equals("")) {
					ecl += ", " + dfault;
				}
				ecl += ")";
			} else if (iterationList != null && iterationList != "" && !iterationList.equals("")) {
				ecl += ", PARALLEL(" + iterationList;
				if (dfault != null && dfault != "" && !dfault.equals("")) {
					ecl += ", " + dfault;
				}
				ecl += ")";
			}
		} else if (loopFilter != null && loopFilter != "" && !loopFilter.equals("")) {
			ecl += ", " + loopFilter + ", " + loopBody;
		} else if (loopCondition != null && loopCondition != "" && !loopCondition.equals("")) {
			ecl += ", " + loopCondition;
			if (rowFilter != null && rowFilter != "" && !rowFilter.equals("")) {
				ecl += ", " + rowFilter;
			}
			ecl += ", " + loopBody;
		}
		ecl += ");\r\n";
		
		return ecl;
	}
	
	@Override
	public CheckResult check() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
