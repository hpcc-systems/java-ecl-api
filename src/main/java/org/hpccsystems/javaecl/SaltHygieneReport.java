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
    private String recordName;
    private String layout;
    private String saltLib;
    
    //private String srcField;
	private boolean includeSrcOutliers = false;
    private boolean includeClusterSrc = false;
    private boolean includeClusterCounts = false;
    private boolean includeSrcProfiles = false;
    
    private boolean outputCleanedDataset = false;

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
		this.saltLib = saltLib.replaceAll("[^A-Za-z0-9]", "");
	}

	
	
	public boolean isOutputCleanedDataset() {
		return outputCleanedDataset;
	}

	public void setOutputCleanedDataset(boolean outputCleanedDataset) {
		this.outputCleanedDataset = outputCleanedDataset;
	}

	public boolean isIncludeSrcOutliers() {
		return includeSrcOutliers;
	}

	public void setIncludeSrcOutliers(boolean includeSrcOutliers) {
		this.includeSrcOutliers = includeSrcOutliers;
	}

	public boolean isIncludeClusterSrc() {
		return includeClusterSrc;
	}

	public void setIncludeClusterSrc(boolean includeClusterSrc) {
		this.includeClusterSrc = includeClusterSrc;
	}

	public boolean isIncludeClusterCounts() {
		return includeClusterCounts;
	}

	public void setIncludeClusterCounts(boolean includeClusterCounts) {
		this.includeClusterCounts = includeClusterCounts;
	}

	public boolean isIncludeSrcProfiles() {
		return includeSrcProfiles;
	}

	public void setIncludeSrcProfiles(boolean includeSrcProfiles) {
		this.includeSrcProfiles = includeSrcProfiles;
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
    	//how do I handle this... should I duplicate this or some how detect profile exists.
    	//for now we will just execute it again
    	String ecl = "";
    	String inDataset = this.datasetName;
    	//if(outputCleanedDataset){
    		inDataset = "in_" + this.datasetName;
    		
    		String idFix = "";
    		//String idFix = "in_record := record\r\n";
    		//idFix += " " + this.datasetName + ";\r\n";
    		//idFix += " UNSIGNED INTEGER6 spoonClusterID := 0;\r\n";
    		//idFix += " UNSIGNED INTEGER6 spoonRecordID := 0;\r\n";
    		//idFix += "end;\r\n\r\n";
    		
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
    		inDataset = "out_" + this.datasetName;
    	//}else{
    	//	
    	//}
    	
        ecl += "h_hygiene_" + unique + " := " + saltLib + ".Hygiene(" + inDataset + ");\r\n";
        ecl += "p_hygiene_" + unique + " := h_hygiene_" + unique + ".AllProfiles;\r\n";
       
        //these three reports will come from salt profile
        //ecl += "OUTPUT(h_" + unique + ".Summary('SummaryReport'), NAMED('Dataprofiling_SummaryReport'), ALL);\r\n";
        //ecl += "OUTPUT(SALT25.MAC_Character_Counts.EclRecord(p_" + unique + ", '" + this.layout + "'),NAMED('Dataprofiling_OptimizedLayout'));\r\n";
        //ecl += "OUTPUT(p_" + unique + ", NAMED('Dataprofiling_AllProfiles'), ALL);\r\n";
        

        ecl += "OUTPUT(h_hygiene_" + unique + ".ValidityErrors,Named('Hygiene_ValidityErrors'),ALL);\r\n";
        
        
        //test the below line of code
        if(this.outputCleanedDataset){
        	//ecl += "OUTPUT(" + saltLib + ".specificities(" + this.datasetName + ").input_file, NAMED('" + this.datasetName +"_CleanedData'));";
        	//ecl += this.datasetName + "_CleanedData := OUTPUT(" + saltLib + ".specificities(" + inDataset + ").input_file,, '~SPOONFILES::" + this.datasetName + "_CleanedData', OVERWRITE);\r\n";
        	//ecl += "output(" + this.datasetName + "_CleanedData ,NAMED('CleanedData'));\r\n";
        	
        	//ecl += "OUTPUT(" + saltLib + ".specificities(" + this.datasetName + ").input_file,THOR);\r\n";
        	
        	//persist method
        	//ecl += this.datasetName + "_CleanedData := OUTPUT(" + saltLib + ".specificities(" + inDataset + ").input_file,NAMED('CleanedData')) : PERSIST( '~SPOONFILES::" + this.datasetName + "_CleanedData');\r\n";
        	//ecl += this.datasetName + "_CleanedData;\r\n";
        	
        	ecl += this.datasetName + "_CleanedData := " + saltLib + ".specificities(" + inDataset + ").input_file : PERSIST( '~SPOONFILES::" + this.datasetName + "_CleanedData');\r\n";
        	ecl += "output(CHOOSEN(" + this.datasetName + "_CleanedData,100),NAMED('CleanedData'));\r\n";
        }
        
        if(includeSrcOutliers){
        	ecl += "OUTPUT(h_hygiene_" + unique + ".AllOutliers,NAMED('SrcOutliers'),ALL);\r\n";// based on SOURCEFIELD
        }
        if(includeClusterSrc){
        	ecl += "OUTPUT(h_hygiene_" + unique + ".ClusterSrc,NAMED('ClusterSrc'),ALL);\r\n";//=based on idfield + SOURCEFIELD
        }
        if(includeClusterCounts){
        	ecl += "OUTPUT(h_hygiene_" + unique + ".ClusterCounts,NAMED('ClusterCounts'),ALL);\r\n";//based on idfield
        }
        if(includeSrcProfiles){
        	ecl += "OUTPUT(h_hygiene_" + unique + ".SrcProfiles,NAMED('SrcProfiles'),ALL);\r\n";
        }
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
