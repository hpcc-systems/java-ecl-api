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
    
    private String allowOverWrite = "true";
   
    
    

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
                outputField.append(ipAddress).append("',");
                outputField.append("'").append(filePath).append("',");
                outputField.append("8192").append(",,,,'").append(clusterName).append("',");
                outputField.append("'").append(logicalFileName).append("',-1,");
                outputField.append("'http://").append(ipAddress).append(":").append(serverPort).append("/FileSpray'").append(",,").append(allowOverWrite).append(");");
            } else {
                //todo: Need to be developed
                
                outputField.append("STD.File.SprayFixed('");
                outputField.append(ipAddress).append("',");
                outputField.append("'").append(filePath).append("',");
                outputField.append(this.recordSize).append(",");
                outputField.append("group").append(",");
                
                outputField.append("'").append(logicalFileName).append("',-1,");
                outputField.append("'http://").append(ipAddress).append(":").append(serverPort).append("/FileSpray'").append(",,").append(allowOverWrite).append(");");
                
            }
        } else {
            throw new RuntimeException("Uninitialized File Type");
        }
        return outputField.toString();

    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
