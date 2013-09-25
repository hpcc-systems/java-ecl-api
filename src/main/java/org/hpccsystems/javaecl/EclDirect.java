/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
//import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import org.apache.commons.io.IOUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.hpccsystems.javaecl.ECLSoap;


/**
 *
 * @author ChalaAX
 */
public class EclDirect {

    private final String urlString;
    private final String clusterName;
    
    private String serverHost;
    private String serverPort;
    private String jobName;
    private String maxReturn;
    private String eclccInstallDir;
    private String includeML = "false";
    private String mlPath;
    private String includeSALT = "false";
    private String SALTPath;
    private String saltLib;
    private String wuid;
    private String outputName = "";
    
    private String userName = "";
    private String password = "";
    private boolean isValid = true;
    
    private String error = "";
    
    private ArrayList<String[]> files = new ArrayList();
    private String resName = "";

    private ArrayList<String> resultNames = new ArrayList();
    private ArrayList compileFlagsAL = new ArrayList();
    
    public ArrayList getCompileFlagsAL() {
		return compileFlagsAL;
	}

	public void setCompileFlagsAL(ArrayList compileFlagsAL) {
		this.compileFlagsAL = compileFlagsAL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



    public String getSaltLib() {
		return saltLib;
	}

	public void setSaltLib(String saltLib) {
		this.saltLib = saltLib;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public ArrayList getFiles() {
		return files;
	}

	public void setFiles(ArrayList files) {
		this.files = files;
	}

    public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getClusterName() {
		return clusterName;
	}
	public String getIncludeSALT() {
		return includeSALT;
	}

	public void setIncludeSALT(String includeSALT) {
		this.includeSALT = includeSALT;
	}
	
	public boolean isIncludeSALT() {
		if(includeSALT != null && includeSALT.equalsIgnoreCase("true")){
			return true;
		}else{
			return false;
		}
	}

	public String getSALTPath() {
		return SALTPath;
	}

	public void setSALTPath(String sALTPath) {
		SALTPath = sALTPath;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getWuid() {
        return wuid;
    }

    public void setWuid(String wuid) {
        this.wuid = wuid;
    }
    
    

    public String getEclccInstallDir() {
        return eclccInstallDir;
    }

    public void setEclccInstallDir(String eclccInstallDir) {
        this.eclccInstallDir = eclccInstallDir;
    }

    public String getIncludeML() {
        return includeML;
    }

    public void setIncludeML(String includeML) {
        this.includeML = includeML;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getMlPath() {
        return mlPath;
    }

    public void setMlPath(String mlPath) {
        this.mlPath = mlPath;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    

    public String getMaxReturn() {
		return maxReturn;
	}

	public void setMaxReturn(String maxReturn) {
		this.maxReturn = maxReturn;
	}

	
	public ArrayList<String> getResultNames() {
		return resultNames;
	}

	public void setResultNames(ArrayList<String> resultNames) {
		this.resultNames = resultNames;
	}



	public EclDirect(String clusterAddress, String clusterName, String clusterPort) {
      if(clusterPort.equals("")){
          //if direct
          //clusterPort = "8008";
          
          //soap uses 8010 default
          clusterPort = "8010";
          
      }
      this.serverHost = clusterAddress;
      this.serverPort = clusterPort;
      urlString = "http://" + clusterAddress + ":"+ clusterPort+"/EclDirect/RunEcl";
      
      //System.out.println("URLString: " + urlString);
      this.clusterName = clusterName;
    }

    
    
    
    public ArrayList execute(String eclCode){

       // System.out.println("ECLDirect Execute: User: " + userName + " " + password);
    	ECLSoap es = getECLSoap();
        
        
        Boolean proceed = es.executeECL(eclCode);
        this.wuid = es.getWuid();
        
        ArrayList results = null;
        try{
            if(proceed){
                InputStream is = es.ResultsSoapCall(wuid,"");
                results = es.parseResults(is);
                isValid = proceed;
            }else{
            	isValid = proceed;
               // System.out.println("ECL Failed");
            }
        }catch(Exception e){
             System.out.println(e);
             e.printStackTrace();
        }
        return results;
    }
    
    
    public Boolean execute_noResults(String eclCode){

        ECLSoap es = getECLSoap();

        Boolean proceed = es.executeECL(eclCode);
        this.wuid = es.getWuid();
        return proceed;
    }
    
    
    public ArrayList resultList(){
        ArrayList al = null;

        ECLSoap es = getECLSoap();
        InputStream is = es.InfoDetailsCall(this.wuid);
        try{
            al = es.parseResultList(is);
        }catch (Exception e){
         System.out.println(e.toString());   
        }
        
        return al;
    }
    
    
    public ArrayList executeDirect(String eclCode) {
       try {  

            String data = URLEncoder.encode("eclText", "UTF-8") + "=" + URLEncoder.encode(eclCode, "UTF-8");
            data += "&" + URLEncoder.encode("cluster", "UTF-8") + "=" + URLEncoder.encode(clusterName, "UTF-8");

            // Send data
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            return parse(conn.getInputStream());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList parse(InputStream xml) throws Exception {
        ArrayList results = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();


        Document dom = db.parse(xml);
        Element docElement = dom.getDocumentElement();

        NodeList dsList = docElement.getElementsByTagName("Dataset");
        if (dsList != null && dsList.getLength() > 0) {

            ArrayList dsArray = new ArrayList();

            results = dsArray;

            for (int i = 0; i < dsList.getLength(); i++) {
                Element ds = (Element) dsList.item(i);
                NodeList rowList = ds.getElementsByTagName("Row");

                if (rowList != null && rowList.getLength() > 0) {

                    ArrayList rowArray = new ArrayList();

                    dsArray.add(rowArray);

                    for (int j = 0; j < rowList.getLength(); j++) {
                        Element row = (Element) rowList.item(j);
                        
                        NodeList columnList = row.getChildNodes();
                        
                        ArrayList columnsArray = new ArrayList();
                        rowArray.add(columnsArray);
                        
                        for (int k = 0; k < columnList.getLength(); k++) {
                            //System.out.println(columnList.item(k).getNodeName());
                           columnsArray.add(new Column(columnList.item(k).getNodeName(), columnList.item(k).getTextContent()));
                        }
                    }
                }

            }
        }
        
        return results;
    }
    
    public ECLSoap getECLSoap(){
        ECLSoap es = new ECLSoap();
        es.setCluster(clusterName);
        es.setEclccInstallDir(eclccInstallDir);
        es.setHostname(serverHost);
        es.setJobName(jobName);
        es.setMaxReturn(maxReturn);
        es.setMlPath(mlPath); 
        es.setOutputName(outputName);
        es.setPort(Integer.parseInt(this.serverPort));
        es.setUser(userName);
        es.setPass(password);
        //System.out.println("CompileFlagAL Size in eclDirect: " + compileFlagsAL.size());
        es.setCompileFlagsAL(compileFlagsAL);
        if(this.includeML.equals("true")){
            es.setIncludeML(true);
        }else{
            es.setIncludeML(false);
        }
  
        es.setSALTPath(SALTPath); 
        es.setSaltLib(saltLib);
        es.setIncludeSALT(isIncludeSALT());
  
           
           
        return es;
    }
     public String convertInputStreamToString(InputStream ists) throws IOException {
       
        if (ists != null) {
            StringBuilder sb = new StringBuilder();
            String line;
 
            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(ists, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                ists.close();
            }
            return sb.toString();
        } else {       
            return "";
        }
    }
     
     
     
     
     public boolean execute(String eclCode, String debugLevel){
 		

 	      //decide if we need to do a pre compile check before we send it off to the server
 	      boolean validate = false;
 	      if(!debugLevel.equalsIgnoreCase("None")){
 	        validate = true;
 	      }
           
 	       ECLSoap es = getECLSoap();
           ArrayList dsList = null;
           String outStr = "";
           try{
               if(validate){

                    this.error = (es.syntaxCheck(eclCode)).trim();
                    System.out.println(this.error);
                    boolean isError = false;
                    boolean isWarning = false;
                    if(es.getErrorCount() > 0 &&  debugLevel.equalsIgnoreCase("Stop on Errors")){
                    	isError = true;
                    }
                    if(es.getWarningCount() > 0 && debugLevel.equalsIgnoreCase("Stop on Errors or Warnings")){
                     	isWarning = true;
                    }
                     
                    if((isError || isWarning) && !error.equals("")){;
                    	this.isValid = false;
                    }else{
                       this.isValid = es.executeECL(eclCode);
                       this.setWuid(es.getWuid());
                         
                       //if not isValid add error
                       if(!this.isValid){
                    	   this.error += "\r\nServer Failed to compile code please refer to ECLWatch and verify your settings\r\n";
                    	   //System.out.println(this.error);
                       }
                    }
                 }else{
                	 //System.out.println("else -- executeECL");
                	 this.isValid = es.executeECL(eclCode);
                     this.setWuid(es.getWuid());
                     if(!this.isValid){
                    	 this.error += "\r\nFailed to execute code on the cluster, please verify your settings\r\n";
                     }
                 }
       
              }catch (Exception e){
            	  this.error += "Exception occured please verify all settings.";
                  e.printStackTrace();
                  this.isValid = false;
                  System.out.println(this.error);
             }
             
             
         return this.isValid;
     }
     
     public boolean writeResultsToFile(String outputDir) throws Exception{
    	 String slash = "\\";
    	 if(this.maxReturn.equals("0")){
    		 return true;
    	 }
     	if(outputDir.contains("/") && !outputDir.contains("\\")){
     		slash = "/";
     		
     	}
     	boolean isSlash = false;
     	if(outputDir.lastIndexOf("\\") == (outputDir.length()-1) || outputDir.lastIndexOf("/") == (outputDir.length()-1)){
     		isSlash = true;
     	}
     	if(!isSlash){
 			outputDir += slash;
 		}
    	 boolean isSuccess = true;
    	// System.out.println("writing files");
    	 ECLSoap es = getECLSoap();
    	 if(isValid){// && dsList != null){
             ArrayList al = this.resultList();
             int alSize = al.size();
             for(int i = 0; i < alSize ; i++){
                 //System.out.println("-");
                 ArrayList al2 = (ArrayList)al.get(i);
                 int al2Size = al2.size();
                 //columns
                 int counter = 0;
                 for(int j = 0; j < al2Size ; j++){
                    // System.out.println("--");

                     ArrayList al3 = (ArrayList)al2.get(j);
                     int al3Size = al3.size();
                     
                     for(int r = 0; r < al3Size ; r++){

                         if(((Column)al3.get(r)).getName().equals("Name")){
                             resName = ((Column)al3.get(r)).getValue();
                             if(this.wuid != null && !this.wuid.equalsIgnoreCase("null")){
	                             InputStream is = es.ResultsSoapCall(this.getWuid(), resName);
	                             ArrayList results = es.parseResults(is);
	                             resName = resName.replace(" ", "_");
	                             String resFileName = outputDir + this.wuid + "_" + resName + ".csv";
	                             String resFileNameCurr = outputDir +  resName + ".csv";
	                             //if (System.getProperty("os.name").startsWith("Windows")) {
	                            //	 resFileName = outputDir + "\\" + resName + ".csv";
	                             //}
	                             
	                             createOutputFile(results,resFileNameCurr,counter);
	                             createOutputFile(results,resFileName,counter);
	                             String[] fileInfo = {resName, outputDir, resFileName};
	                             files.add(fileInfo);
	                             resultNames.add(resName);
	                             counter++;
	                             is.close();
                         	}
                             
                         }
                     }
                     
                 }
                     System.setProperty("fileCount", counter+"" );
             }

         }else{
        	 isSuccess = false;
         }
    	 return isSuccess;
     }
     
     public void createOutputFile(ArrayList dsList,String fileName, int count){
    	 System.out.println("Writing File New: " + fileName);
         String outStr = "";
         String header = "";
         String error = "";
         if(dsList != null){
         String newline = System.getProperty("line.separator");
         
                        for (int iList = 0; iList < dsList.size(); iList++) {
                            //"----------Outer-------------"
                            ArrayList rowList = (ArrayList) dsList.get(iList);

                            for (int jRow = 0; jRow < rowList.size(); jRow++) {
                                //"----------Row-------------"
                                ArrayList columnList = (ArrayList) rowList.get(jRow);

                           
                                for (int lCol = 0; lCol < columnList.size(); lCol++) {
                                 //"----------Column-------------"
                                    Column column = (Column) columnList.get(lCol);
                                    String val = column.getValue().replace("\\", "\\\\");
                                    val = val.replace("\"", "\\\"");
                                    
                                    if(val.contains(",") || val.contains("\"") || val.contains("\r\n") || val.contains("\n")){
                                    	outStr += "\"" + val + "\"";
                                    }else{
                                    	outStr += val;
                                    }
                                    //outStr += column.getValue();
                                    if(lCol< (columnList.size()-1)){
                                        outStr += ",";
                                    }
                                    if(jRow == 0){
                                    	if(column.getName().contains(",")){
                                    		header += "\"" + column.getName() + "\"";
                                    	}else{
                                        header += column.getName();
                                    	}
                                        //header += column.getName();
                                        if(lCol< (columnList.size()-1)){
                                            header += ",";
                                        }else{
                                            header += newline;
                                        }
                                    }
                                }
                               
                                outStr += newline;
                            }
                        }
             try {
                
                BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
                System.getProperties().getProperty("fileName");
                System.setProperty("fileName"+count, fileName);
                
                out.write(header+outStr);
                out.close();
           
            } catch (IOException e) {
               //logError("Failed to write file: " + fileName);
               error += "Failed to write ecl code file: " + fileName;
               //result.setResult(false);
                e.printStackTrace();
            }  
         }
    }
}
