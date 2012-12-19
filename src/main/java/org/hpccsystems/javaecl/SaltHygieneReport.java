/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class SaltHygieneReport implements EclCommand {

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
    	String unique = this.name.replace(" ", "_");
    	//how do I handle this... should I duplicate this or some how detect profile exists.
    	//for now we will just execute it again
        String ecl = "h_hygiene_" + unique + " := " + saltLib + ".Hygiene(" + this.datasetName + ");\r\n";
        ecl += "p_hygiene_" + unique + " := h_hygiene_" + unique + ".AllProfiles;\r\n";
       
        //these three reports will come from salt profile
        //ecl += "OUTPUT(h_" + unique + ".Summary('SummaryReport'), NAMED('Dataprofiling_SummaryReport'), ALL);\r\n";
        //ecl += "OUTPUT(SALT25.MAC_Character_Counts.EclRecord(p_" + unique + ", '" + this.layout + "'),NAMED('Dataprofiling_OptimizedLayout'));\r\n";
        //ecl += "OUTPUT(p_" + unique + ", NAMED('Dataprofiling_AllProfiles'), ALL);\r\n";
        
        ecl += "OUTPUT(h_hygiene_" + unique + ".ValidityErrors,Named('Hygiene_ValidityErrors'),ALL);";
        
       
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
