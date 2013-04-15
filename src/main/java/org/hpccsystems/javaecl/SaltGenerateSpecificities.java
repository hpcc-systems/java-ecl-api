/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class SaltGenerateSpecificities implements EclCommand {

    private String name;
    private String datasetName;
    private String recordName;
    //private String layout;
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

	public String getSaltLib() {
		return saltLib;
	}

	public void setSaltLib(String saltLib) {
		this.saltLib = saltLib;
	}
	
	

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	@Override
    public String ecl() {
		//String regex = "[ ]";
    	String unique = this.name.replaceAll("[^A-Za-z0-9]", "");//this.name.replace(" ", "_");
    	String ecl = "";
    	String inDataset = this.datasetName;
		inDataset = "in_" + this.datasetName;
		
		String idFix = "";
		
		idFix += this.saltLib + ".layout_" + this.recordName + " SpoonTransform(" + this.datasetName +" L) := TRANSFORM\r\n";
		idFix += " SELF.spoonClusterID := 0;\r\n";
		idFix += " SELF.spoonRecordID := 0;\r\n";
		idFix += " SELF := L;\r\n";
		idFix += "END;\r\n\r\n";
		idFix += inDataset + " := project(" + this.datasetName + ",SpoonTransform(LEFT));\r\n\r\n";
		
		
		idFix += this.saltLib + ".layout_" + this.recordName + " AddIds("+ inDataset +" L,"+ inDataset +" R) := TRANSFORM\r\n";
		idFix += " SELF.spoonClusterID := L.spoonRecordID + 1;\r\n";
		idFix += " SELF.spoonRecordID := L.spoonRecordID + 1;\r\n";
		idFix += " SELF := R;\r\n";
		idFix += "END;\r\n\r\n";
		idFix += "out_" + this.datasetName + " := ITERATE("+inDataset+",AddIds(LEFT,RIGHT));\r\n\r\n";
		
		ecl += idFix;
    	
    	ecl += "OUTPUT(" + saltLib + ".specificities(out_" + this.datasetName + ").Specificities,Named('Specificities'));\r\n";
    	ecl += "OUTPUT(" + saltLib + ".specificities(out_" + this.datasetName + ").SpcShift,Named('SpcShift'));\r\n";
    	//because of OSS compatability can't autogen the specificities here must fetch the output and do it our self
    	
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
