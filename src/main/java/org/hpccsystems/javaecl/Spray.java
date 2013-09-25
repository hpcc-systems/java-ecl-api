/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Spray implements EclCommand {

    private String ipAddress;
    private String serverPort = "8010";
    private String filePath;
    private String logicalFileName;
    private String fileType;
    private String csvSeparator;
    private String csvTerminator;
    private String csvQuote;
    private String recordSize;
    private String clusterName;
    private String landingZoneOverrideIP = "";
    
    private String allowOverWrite = "true";
   
    
    private String groupName = "";
    
    

    public String getLandingZoneOverrideIP() {
		return landingZoneOverrideIP;
	}

	public void setLandingZoneOverrideIP(String landingZoneOverrideIP) {
		this.landingZoneOverrideIP = landingZoneOverrideIP;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

    public String getAllowOverWrite() {
		return allowOverWrite;
	}

	public void setAllowOverWrite(String allowOverWrite) {
		this.allowOverWrite = allowOverWrite;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getCsvQuote() {
        return csvQuote;
    }

    public void setCsvQuote(String csvQuote) {
        this.csvQuote = csvQuote;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLogicalFileName() {
        return logicalFileName;
    }

    public void setLogicalFileName(String logicalFileName) {
        this.logicalFileName = logicalFileName;
    }

    public String getRecordSize() {
        return recordSize;
    }

    public void setRecordSize(String recordSize) {
        this.recordSize = recordSize;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public String ecl() {
        StringBuilder outputField = new StringBuilder();
        outputField.append("import std;").append("\r\n");
        if (fileType != null) {
            if (fileType.equalsIgnoreCase("variable")) {
                outputField.append("std.file.sprayVariable('");
                
                if(this.landingZoneOverrideIP.equals("")){
                	outputField.append(this.ipAddress).append("',");//sourceIP
                }else{
                	outputField.append(this.landingZoneOverrideIP).append("',");//sourcepath
                }
                outputField.append("'").append(this.filePath).append("',");//sourcepath
                outputField.append(",");//maxrecordsize
                outputField.append("'").append(this.csvSeparator).append("',");//srcCSVseparator
                outputField.append("'").append(this.csvTerminator).append("',");//srcCSVterminator
                outputField.append("'").append(this.csvQuote).append("',");//srcCSVquote
                outputField.append("'").append(this.groupName).append("',");//destinationgroup
                outputField.append("'").append(this.logicalFileName).append("',");//destinationlogicalname
                outputField.append("-1,");//[timeout]
                outputField.append("'http://").append(this.ipAddress).append(":").append(this.serverPort).append("/FileSpray'").append(",");//espserverIPport
                outputField.append(",");//maxConnections
                outputField.append(this.allowOverWrite).append(");");//allowoverwrite
                
        	}else if (fileType.equalsIgnoreCase("xml")) {
        		//todo: need to be developed
            } else {
                //todo: Need to be fully tested
                
                outputField.append("STD.File.SprayFixed('");
                outputField.append(this.ipAddress).append("',");//sourceIP
                outputField.append("'").append(this.filePath).append("',");//sourcepath
                outputField.append(this.recordSize).append(",");//recordsize
                outputField.append("'").append(this.groupName).append("'").append(",");//destinationgroup
                outputField.append("'").append(this.logicalFileName).append("',");//destinationlogicalname
                outputField.append("-1,");//timeout
                outputField.append("'http://").append(this.ipAddress).append(":").append(this.serverPort).append("/FileSpray'").append(",");//espserverIPport
                outputField.append(",");//maxConnections
                outputField.append(this.allowOverWrite);//allowoverwrite
                outputField.append(");");
                
            }
                
        } else {
            throw new RuntimeException("Uninitialized File Type");
        }
        //System.out.println(outputField.toString());
        return outputField.toString();

    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
