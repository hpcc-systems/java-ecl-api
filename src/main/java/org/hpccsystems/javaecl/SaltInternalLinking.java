/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class SaltInternalLinking implements EclCommand {

    private String name;
    private String datasetName;
    //private String layout;
    private String saltLib;
    private String iteration;
    private Boolean doAgain = false;

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

	
	public String getIteration() {
		return iteration;
	}

	public void setIteration(String iteration) {
		this.iteration = iteration;
	}
	
	

	public Boolean getDoAgain() {
		return doAgain;
	}

	public void setDoAgain(Boolean doAgain) {
		this.doAgain = doAgain;
	}

	@Override
    public String ecl() {
		//String regex = "[ ]";
    	String unique = this.name.replaceAll("[^A-Za-z0-9]", "");//this.name.replace(" ", "_");
    	//how do I handle this... should I duplicate this or some how detect profile exists.
    	//for now we will just execute it again
        
    	String ecl = saltLib + ".Proc_Iterate('" + this.iteration + "')";
    	if(doAgain){
    		ecl += ".DoAllAgain;\r\n";
    	}else{
    		ecl += ".DoAll;\r\n";
    	}
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
