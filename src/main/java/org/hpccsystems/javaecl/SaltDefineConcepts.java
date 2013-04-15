/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

import java.util.ArrayList;

/**
 *
 * @author ChalaAX
 */
public class SaltDefineConcepts implements EclCommand {

    //private String name;
   // private String datasetName;
    
    private String conceptName;
    private String effectOnSpecificity;
    private String threshold;
    private boolean useBagOfWords;
    private String reOrderType;
    private String segmentType;
    private String scale;
    private String specificity;
    private String switchValue;
    private ArrayList fields;//string for null allow -- string+ for non nulls
    

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public String getEffectOnSpecificity() {
		return effectOnSpecificity;
	}

	public void setEffectOnSpecificity(String effectOnSpecificity) {
		this.effectOnSpecificity = effectOnSpecificity;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public boolean isUseBagOfWords() {
		return useBagOfWords;
	}

	public void setUseBagOfWords(boolean useBagOfWords) {
		this.useBagOfWords = useBagOfWords;
	}

	public String getReOrderType() {
		return reOrderType;
	}

	public void setReOrderType(String reOrderType) {
		this.reOrderType = reOrderType;
	}

	public String getSegmentType() {
		return segmentType;
	}

	public void setSegmentType(String segmentType) {
		this.segmentType = segmentType;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getSpecificity() {
		return specificity;
	}

	public void setSpecificity(String specificity) {
		this.specificity = specificity;
	}

	public String getSwitchValue() {
		return switchValue;
	}

	public void setSwitchValue(String switchValue) {
		this.switchValue = switchValue;
	}

	public ArrayList getFields() {
		return fields;
	}

	public void setFields(ArrayList fields) {
		this.fields = fields;
	}

	@Override
    public String ecl() {
		//String regex = "[ ]";
    	//String unique = this.name.replaceAll("[^A-Za-z0-9]", "");//this.name.replace(" ", "_");
		/*
    	String ecl = "";
    	ecl += "CONCEPT:"+conceptName;
    	for (int i = 0; i<fields.size(); i++){
    		//add children
    		ecl += fields.get(i);
    	}
    	//optional field
    	if(!segmentType.equals("")){
    		ecl += ":SEGTYPE(" + segmentType + ")";
    	}
    	//optional field
    	if(!scale.equals("")){
    		ecl += ":SCALE(" + scale + ")";
    	}
    	//optional field
    	if(useBagOfWords){
    		ecl += ":BAGOFWORDS";
    	}
    	//required field set to 0 if ""
    	if(specificity.equals("")){
    		ecl += ":0";
    	}else{
    		ecl += ":" + specificity;
    	}
    	//required field set to 0 if ""
    	if(switchValue.equals("")){
    		ecl += ",0";
    	}else{
    		ecl += "," + switchValue;
    	}
    	
    	//String inDataset = this.datasetName;
    	*/
        return "";
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
