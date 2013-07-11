/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author SimmonsJA
 */
public class DeSpray implements EclCommand {

	private String name;
	private String logicalName;
	private String destinationIP;
	private String destinationPath;
	private String timeout;
	private String espserverISPport;
	private String maxConnections;
	private Boolean allowOverwrite;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogicalName() {
		return logicalName;
	}
	
	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}
	
	public String getDestinationIP() {
		return destinationIP;
	}
	
	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}
	
	public String getDestinationPath() {
		return destinationPath;
	}
	
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}
	
	public String getTimeout() {
		return timeout;
	}
	
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
	public String getEspserverISPport() {
		return espserverISPport;
	}
	
	public void setEspserverISPport(String espserverISPport) {
		this.espserverISPport = espserverISPport;
	}
	
	public String getMaxConnections() {
		return maxConnections;
	}
	
	public void setMaxConnections(String maxConnections) {
		this.maxConnections = maxConnections;
	}
	
	public Boolean isAllowOverwrite() {
		return allowOverwrite;
	}
	
	public void setAllowOverwrite(boolean allowOverwrite) {
		this.allowOverwrite = allowOverwrite;
	}
	
	public String isAllowOverwriteString() {
		if (allowOverwrite)
			return "true";
		return "false";
	}
	
	public void setAllowOverwriteString(String allowOverwrite) {
		if(allowOverwrite.equals("true"))
			this.allowOverwrite = true;
		else
			this.allowOverwrite = false;
	}
	
	@Override
	public String ecl() {
		// DeSpray(logicalName, destinationIP, destinationPath, [ ,timeout ] [ ,espserverISPport ] [ ,maxConnections ] [ ,allowOverwrite ]);
		
		String ecl = "STD.File.DeSpray(";
		
		if (logicalName != null && logicalName != "" && !logicalName.equals("")) {
			if (destinationIP != null && destinationIP != "" && !destinationIP.equals("")) {
				if (destinationPath != null && destinationPath != "" && !destinationPath.equals("")) {
					ecl += "'" + logicalName + "','" + destinationIP + "','" + destinationPath + "'";
				}
			}
		}
		ecl += ",";
		if (timeout != null && timeout != "" && !timeout.equals("")) {
			ecl += timeout;
		}
		ecl += ",";
		if (espserverISPport != null && espserverISPport != "" && !espserverISPport.equals("")) {
			ecl += "'"+espserverISPport+"'";
		}
		ecl += ",";
		if (maxConnections != null && maxConnections != "" && !maxConnections.equals("")) {
			ecl += maxConnections;
		}
		ecl += ",";
		if (allowOverwrite != null && allowOverwrite) {
			ecl += "TRUE";
		} else if (allowOverwrite != null && !allowOverwrite) {
			ecl += "FALSE";
		}
		
		ecl += ");\r\n";
		
		return ecl;
	}
	
	@Override
	public CheckResult check() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
