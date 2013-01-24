/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class SaltDataProfiling implements EclCommand {

    private String name;
    private String datasetName;
    private String layout;
    private String saltLib;


    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}
	
	

	public String getSaltLib() {
		return saltLib;
	}

	public void setSaltLib(String saltLib) {
		this.saltLib = saltLib;
	}

	@Override
    public String ecl() {
		//String regex = "[ ]";
		String unique = this.name.replaceAll("[^A-Za-z0-9]", "");//String unique = this.name.replace(" ", "_");
        String ecl = "h_" + unique + " := " + saltLib + ".Hygiene(" + this.datasetName + ");\r\n";
        ecl += "p_" + unique + " := h_" + unique + ".AllProfiles;\r\n";
       
        ecl += "OUTPUT(h_" + unique + ".Summary('SummaryReport'), NAMED('Dataprofiling_SummaryReport'), ALL);\r\n";
        ecl += "OUTPUT(SALT25.MAC_Character_Counts.EclRecord(p_" + unique + ", '" + this.layout + "'),NAMED('Dataprofiling_OptimizedLayout'));\r\n";
        ecl += "OUTPUT(p_" + unique + ", NAMED('Dataprofiling_AllProfiles'), ALL);\r\n";
        
       
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
