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
public class Dataset implements EclCommand {
    private String name;
    private String logicalFileName;
    private String recordName;
    private String fileType;
    private ArrayList recordFormatList;
    private String recordFormatString;
    
    private String recstruct;
    private String recordSet;
    
    private String recordDef = "";
    private String datasetDef = "";
    
    private Boolean hasHeaderRow = false;
    private String csvSeparator = "";
    private String csvTerminator = "";
    private String csvQuote = "";

    public String getRecordSet() {
        return recordSet;
    }

    public void setRecordSet(String recordSet) {
        this.recordSet = recordSet;
    }

    public String getRecstruct() {
        return recstruct;
    }

    public void setRecstruct(String recstruct) {
        this.recstruct = recstruct;
    }
    

    public String getLogicalFileName() {
        return logicalFileName;
    }

    public void setLogicalFileName(String logicalFileName) {
        this.logicalFileName = logicalFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getRecordFormatList() {
        return recordFormatList;
    }

    public void setRecordFormatList(ArrayList recordDefinition) {
        this.recordFormatList = recordDefinition;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getRecordFormatString() {
        return recordFormatString;
    }

    public void setRecordFormatString(String recordFormatString) {
        this.recordFormatString = recordFormatString;
    }
    
    
    
    
    public String getRecordDef() {
		return recordDef;
	}

	public void setRecordDef(String recordDef) {
		this.recordDef = recordDef;
	}

	public String getDatasetDef() {
		return datasetDef;
	}

	public void setDatasetDef(String datasetDef) {
		this.datasetDef = datasetDef;
	}

	public Boolean getHasHeaderRow() {
		return hasHeaderRow;
	}

	public void setHasHeaderRow(Boolean hasHeaderRow) {
		this.hasHeaderRow = hasHeaderRow;
	}

	public String getCsvSeparator() {
		return csvSeparator;
	}

	public void setCsvSeparator(String csvSeparator) {
		this.csvSeparator = csvSeparator;
	}

	public String getCsvTerminator() {
		return csvTerminator;
	}

	public void setCsvTerminator(String csvTerminator) {
		this.csvTerminator = csvTerminator;
	}

	public String getCsvQuote() {
		return csvQuote;
	}

	public void setCsvQuote(String csvQuote) {
		this.csvQuote = csvQuote;
	}

	@Override
    public String ecl() {
        String recordFmt = "";
        
        if (recordFormatString != null && recordFormatString.length() > 0)  {
            recordFmt = recordFormatString;
        } else if (recordFormatList != null && recordFormatList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < recordFormatList.size(); i++) {
                sb.append(recordFormatList.get(i)).append(";");
            }
            recordFmt =  (sb.toString()).trim();             
        }
        // attr := DATASET( file, struct, filetype );
        //attr := DATASET( dataset, file, filetype );
        //attr := DATASET( WORKUNIT( [ wuid , ] namedoutput ), struct );
        //[ attr := ] DATASET( recordset [, recstruct ] );
        
        //this one is for file inputs
        
        String csvFormat = "";
        if(this.fileType.equalsIgnoreCase("CSV")){
        	//dsStateFips := DATASET('~seer:tatefipscode', recStateFipsCode, CSV(HEADING(1)));
        	System.out.println("Has Header Row: " + this.hasHeaderRow);
        	if(this.hasHeaderRow){
        		csvFormat = "HEADING(1)";
        	}
        	if(!this.csvTerminator.equals("")){
        		if(!csvFormat.equals("")){csvFormat += ",";}
        		csvFormat += "TERMINATOR(" + this.csvTerminator + ")";
        	}
        	if(!this.csvSeparator.equals("")){
        		if(!csvFormat.equals("")){csvFormat += ",";}
        		csvFormat += "SEPARATOR("+ this.csvSeparator + ")";
        	}
        	if(!this.csvQuote.equals("")){
        		if(!csvFormat.equals("")){csvFormat += ",";}
        		csvFormat += "QUOTE(" + this.csvQuote + ")";
        	}
        	if(!csvFormat.equals("")){
        		fileType = "CSV(" + csvFormat + ")";
        		
        	}
        }
        
        if(logicalFileName != null && logicalFileName.length() > 0){
            System.out.println("regular dataset |" + logicalFileName +"|");
            if(recordFmt != null && recordFmt.length() > 0){
                recordDef = recordName + ":= record \r\n" + recordFmt + "\r\nend; \r\n";
            }
            datasetDef = name + ":= dataset('" + logicalFileName + "'," + recordName + "," +  fileType + "); \r\n";
        }else{
            System.out.println("ml dataset");
            //this is for recordset (in-line inputs
            
            if(recordFmt != null && recordFmt.length() > 0){
                recordDef = recordName + ":= record \r\n" + recordFmt + "\r\nend; \r\n";
                datasetDef = name + ":= dataset([" + recordSet + "]," + recordName + "); \r\n";
            }else{
                datasetDef = name + ":= dataset([" + recordSet + "]," + recordName + "); \r\n";
            }
            
        }
        return recordDef + datasetDef;
    }
    
    
   /*
    public String ecl2() {
        String recordFmt;
        if (recordFormatString != null && recordFormatString.length() > 0)  {
            recordFmt = recordFormatString;
        } else {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recordFormatList.size(); i++) {
            sb.append(recordFormatList.get(i)).append(";");
        }
        recordFmt =  sb.toString();             
        }
        
        String recordDef = recordName + ":= record \r\n" + recordFmt + "\r\nend; \r\n";
        String datasetDef = name + ":= dataset('" + logicalFileName + "'," + recordName + "," +  fileType + "); \r\n";
        return recordDef + datasetDef;
    }
     * 
     */
    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
    
}
