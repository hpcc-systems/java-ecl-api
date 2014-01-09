/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;
import java.net.*;
import java.io.*;
import java.util.*;
import sun.misc.BASE64Encoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
//import org.xml.sax.InputSource;






        
        /**
 *
 * @author ChambeJX
 */
public class ECLSoap {
	boolean isLogonFail = false;
    private String hostname = "";
    private int port = 8010;
    
    private String mlPath = "";
    //C:\\Program Files\\HPCC Systems\\HPCC\\bin\\ver_3_6\\
    private String eclccInstallDir = "";
    private String jobName = "";
    private String maxReturn = "";
    private String cluster = "";
    private boolean includeML = false;
    
    private String outputName = "";
    
    private String wuid = "";
    
    private String tempDir = "";
    
    private int errorCount = 0;
    private int warningCount = 0;
    private String errorText = "";
    
    private String user = "";
    private String pass = "";
    
    private String SALTPath = "";
    private boolean includeSALT = false;
    private String saltLib = "";
    private ArrayList<String[]> compileFlagsAL = new ArrayList();
    private String baseFileName = "SpoonEclCode.ecl";
    private String baseCheckFileName = "CheckSpoonEclCode.ecl";
    
    
    public String getBaseCheckFileName() {
		return baseCheckFileName;
	}

	public void setBaseCheckFileName(String baseCheckFileName) {
		this.baseCheckFileName = baseCheckFileName;
	}

	public String getBaseFileName() {
		return baseFileName;
	}

	public void setBaseFileName(String baseFileName) {
		this.baseFileName = baseFileName;
	}

	public ArrayList getCompileFlagsAL() {
		return compileFlagsAL;
	}

	public void setCompileFlagsAL(ArrayList compileFlagsAL) {
		this.compileFlagsAL = compileFlagsAL;
	}

	public String getMaxReturn() {
		return maxReturn;
	}

	public void setMaxReturn(String maxReturn) {
		this.maxReturn = maxReturn;
	}

	public String getSaltLib() {
		return saltLib;
	}

	public void setSaltLib(String saltLib) {
		this.saltLib = saltLib;
	}

    public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

    public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getWarningCount() {
		return warningCount;
	}

	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}

	public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName.replaceAll("[^A-Za-z0-9]", "");
    }

    
    
    public String getWuid() {
        return wuid;
    }

    public void setWuid(String wuid) {
        this.wuid = wuid;
    }
    
    
    /*Getters & Setters
     * 
     */
    
    
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public boolean isIncludeML() {
        return includeML;
    }

    public void setIncludeML(boolean includeML) {
        this.includeML = includeML;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getEclccInstallDir() {
        return eclccInstallDir;
    }

    public void setEclccInstallDir(String eclccInstallDir) {
        this.eclccInstallDir = eclccInstallDir;
    }

    public String getMlPath() {
        return mlPath;
    }

    public void setMlPath(String mlPath) {
        this.mlPath = mlPath;
    }
    //end getters and setters
    
   
    public String getSALTPath() {
		return SALTPath;
	}

	public void setSALTPath(String sALTPath) {
		SALTPath = sALTPath;
	}

	public boolean isIncludeSALT() {
		return includeSALT;
	}

	public void setIncludeSALT(boolean includeSALT) {
		this.includeSALT = includeSALT;
	}
	 //end getters and setters
	
	
	
	
    public ECLSoap() {
    	if (System.getProperty("os.name").startsWith("Windows")) {
    		this.tempDir = System.getProperty("java.io.tmpdir");
    		if(!(tempDir.endsWith("/") || tempDir.endsWith("\""))){
    			this.tempDir += "\\";
        	}
        } else {
        	this.tempDir = System.getProperty("java.io.tmpdir") + "/";
        } 
    	
        
        //System.out.println("OS Temp Dir is: " + tempDir);
    }
    public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String syntaxCheck(String ecl){
        String res = "";
        int test = 0;
        String inFile = this.outputName + baseCheckFileName;
        String inFilePath = this.tempDir + inFile;
         try {
            System.out.println("Created File (synTaxCheck): " + this.tempDir + inFile);
            BufferedWriter out = new BufferedWriter(new FileWriter(this.tempDir + inFile));
            out.write(ecl);
            out.close();

        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }   
         //System.out.println("check point");
        try{
            String logFile = this.tempDir + this.outputName.replace(' ', '_') + "_syntax_log.log";
            String c = eclccInstallDir;
            if (System.getProperty("os.name").startsWith("Windows")) {
                        c += "eclcc.exe";
            }else{
                        c += "eclcc";
            }
            String paramSalt = "";
            

            System.out.println("_________________________ECLCC SYNTAX CHECK_______________________________");

            ArrayList<String> paramsAL = new ArrayList<String>();
            paramsAL.add(c);
            paramsAL.add("-c");
            paramsAL.add("-syntax");
            
            if(this.includeSALT){
                paramSalt = "-legacy";
                paramsAL.add(paramSalt);
            }
            paramsAL.add("--logfile");
            paramsAL.add(logFile);
            
            if(this.includeML){
            	paramsAL.add("-I");
            	paramsAL.add(this.mlPath);
            }
            if(this.includeSALT){
            	paramsAL.add("-I");
            	paramsAL.add(this.SALTPath);
            }
            if(this.saltLib != null && !this.saltLib.equals("")){
            	paramsAL.add("-I");
            	paramsAL.add(this.saltLib);
            }
            if(compileFlagsAL != null && compileFlagsAL.size() > 0){
            	for(int i = 0; i<compileFlagsAL.size(); i++){
            		if(compileFlagsAL.get(i).length == 2){
            			if(!compileFlagsAL.get(i)[0].equals("")){
            				paramsAL.add(compileFlagsAL.get(i)[0]);
            			}
            			if(!compileFlagsAL.get(i)[1].equals("")){
            				paramsAL.add(compileFlagsAL.get(i)[1]);
            			}
            		}
            	}
            }
            paramsAL.add(inFilePath);
            String [] params = new String[paramsAL.size()];
            paramsAL.toArray(params);
            ProcessBuilder pb = new ProcessBuilder(params);
            System.out.println("----------Syntax Check-----------");
            System.out.println("---------------------");
            System.out.println(pb.command().toString());
           
            pb.redirectErrorStream(true); // merge stdout, stderr of process
            System.out.println(pb.command().toString());
	    	boolean pathExists = (new File(eclccInstallDir)).exists();
	    	if(!pathExists){
	    		errorCount++;
	    		res += "Unable to locate the ecl compiler, Check Global Variables\r\n";
	    		errorText += "Unable to locate the ecl compiler, Check Global Variables\r\n";
	    	}
            File path = new File(eclccInstallDir);
            pb.directory(path);
            Process p = pb.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null){
                res += cleanError(line)+"\r\n";
                errorText += cleanError(line)+"\r\n";
            }
             
            InputStream iError = p.getErrorStream();
            InputStreamReader isrError = new InputStreamReader(iError);
            BufferedReader brErr = new BufferedReader(isrError);
            String lineErr;
            while((lineErr = brErr.readLine()) != null){

                res += cleanError(lineErr)+"\r\n";
                errorText += cleanError(lineErr)+"\r\n";
            }
            
            //deleteFile(this.tempDir+inFile);
            
 
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }

        
        return res;
    }
   
    public String cleanError(String in){
    	int start = in.indexOf(".ecl");
    	if(start >= 0 ){
    		start += 4;
    		return in.substring(start);
    	}else{
    		//this is the line of
    		//1 error, 0 warning
    		//3 errors, 0 warning
    		
    		//full string            [0-9] error[s]?, [0-9] warning[s]?
    		
    		
    		String regex = "[0-9]+ error[s]?";
    		Pattern pattern = 
            Pattern.compile(regex);

            Matcher matcher = 
            pattern.matcher(in);
            if(matcher.find()){
	            String e = matcher.group();
	            
	            regex = "[0-9]+";
	    		pattern = 
	            Pattern.compile(regex);
	
	            matcher = 
	            pattern.matcher(e);
	            if(matcher.find()){
	            	String ec = matcher.group();
	            	
	            	try{
	                 	this.errorCount = Integer.parseInt(ec) + this.errorCount;
	                }catch (Exception ee){
	                 	
	                }
	            }
            }
            

            regex = "[0-9]+ warning[s]?";
    		pattern = 
            Pattern.compile(regex);

            matcher = 
            pattern.matcher(in);
            if(matcher.find()){
	            String w = matcher.group();
	            
	            regex = "[0-9]+";
	    		pattern = 
	            Pattern.compile(regex);
	
	            matcher = 
	            pattern.matcher(w);
	            if(matcher.find()){
	            	String wc = matcher.group();
	            	
	            	try{
	                	this.warningCount = Integer.parseInt(wc) + this.warningCount;
	                }catch (Exception we){
	                 	
	                }
	            }
            }

           
           
    	}
    	return in;
    }

    
    /*executeECL
     * 
     * @accepts String
     * @returns InputStream
     * 
     * Accepts the raw ecl code and runs through all steps of create, submit, 
     * and gets the data.
     * 
     * 
     */
    public Boolean executeECL(String ecl){
        ArrayList results = null;
        boolean proceed = false;
        
        String cECL = compileECL(ecl);
        if(cECL == null || cECL.equals("")){
        	//System.out.println("----------- proceed = false --------------");
        	proceed = false;
        }else{
	        
	        String wuid = this.createAndUpdateSoapCall(cECL);
	        this.wuid = wuid;
	        InputStream is = null;
	        if(wuid != null && !wuid.equals("")){
	            this.submitSoapCall(wuid);
	            try{
	                
	                proceed = this.isComplete(wuid);
	                
	                /*
	                if(proceed){
	                    is = this.ResultsSoapCall(wuid);
	                    results = this.parseResults(is);
	                }else{
	                    System.out.println("ECL Failed");
	                }
	                 * 
	                 */
	
	            }catch(Exception e){
	                 System.out.println(e);
	                 e.printStackTrace();
	                 proceed = false;
	            }
	        }
        }
        return proceed;
    }
    
    /*executeECL
     * 
     * @accepts String
     * @returns String
     * 
     * Accepts the raw ecl code and runs through all steps of create, submit, 
     * and gets the data.  Returns an XML of the results similar to Direct
     * but will require additional work to parse.
     * 
     * 
     */
    /*
    public String execute(String ecl){
        String results = null;
        
        String cECL = compileECL(ecl);

        String wuid = this.createAndUpdateSoapCall(cECL);
        InputStream is = null;
        if(wuid != null && !wuid.equals("")){
            this.wuid = wuid;
            this.submitSoapCall(wuid);
            try{
                
                boolean proceed = this.isComplete(wuid);
                if(proceed){
                    is = this.ResultsSoapCall(wuid);
                    //need to parse this out to string
                    results = fetchXML(is);
                }else{
                    System.out.println("ECL Failed");
                }

            }catch(Exception e){
                 System.out.println(e);
                 e.printStackTrace();
            }
        }
        
        return results;
    }
     * 
     */
    public static final String UTF8_BOM = "\uFEFF";
    private static String removeUTF8BOM(String s) {
        s = s.replace(UTF8_BOM, "");
        return s;
    }
    /*isComplete
     * 
     * @accepts String
     * @returns boolean
     * 
     * This is a recursive function, it calls the server until it gets a response 
     * that the job has completed or failed returning True/False based on this.
     */
    public boolean isComplete(String wuid){
        boolean complete = false;
        boolean isError = false;
        int errorCnt = 0;
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<WUWaitComplete xmlns=\"urn:hpccsystems:ws:wsworkunits\">"
                + "<Wuid>"+wuid+"</Wuid>"
                + "<Wait>9000</Wait>"
                + "<ReturnOnWait>true</ReturnOnWait>"
                + "</WUWaitComplete>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        String path = "/WsWorkunits/WUInfo";
             
        while(!complete && !isError && errorCnt<10){
        	 
	        try{
	        	InputStream is = this.doSoap(xml, path);
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            
	            StringWriter writer = new StringWriter();
	            IOUtils.copy(is, writer,"UTF-8");
	            String theXML = writer.toString();
	            theXML = removeUTF8BOM(theXML);

	            /*DEGUG STATMENTS
	             System.out.println("loop for complete check");
	             System.out.println("|" +theXML + "|");
	             System.out.println("test:" + is.toString());
	             System.out.println("-------------------------");
	             System.out.println("|" +theXML + "|");
	             System.out.println("-------------------------");
	            */
	            InputStream isClean = new ByteArrayInputStream(theXML.getBytes());
	            Document doc = dBuilder.parse(isClean);
	            
	            is.close();
	            doc.getDocumentElement().normalize();
	
	            NodeList nList = doc.getElementsByTagName("WUWaitResponse");
	            //System.out.println("-----------PARSE- " + nList.getLength() + " -----------");
	
	                    for (int temp = 0; temp < nList.getLength(); temp++) {
	                        //System.out.println("-----------"+temp+"------------");
	                       Node nNode = nList.item(temp);
	                       if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
	                          Element eElement = (Element) nNode;
	                          NodeList nl = eElement.getChildNodes();
	
	                          for (int temp1 = 0; temp1 < nl.getLength(); temp1++) {
	                             // System.out.append("parsing node --");
	                              Node node = nl.item(temp1);
	                              Element elem = (Element) node;
	                              if((node.getNodeName()).equals("StateID")){
	                                  String val = getTagValue(node.getNodeName(), eElement);
	                                System.out.println("Results Check Value: " + val);
	                                if(val.equals("3")){
	                                    complete = true;
	                                }else if(val.equals("4")){
	                                		System.out.println("Error State Reached");
		                                    complete = false;
		                                    isError = true;
		                                    //error state
	                                }else if(val.equals("1") || val.equals("2") || val.equals("11")){
	                                	System.out.println("Recursion Step:::::");
	                                	 Thread.sleep(1500);
	                                    complete = isComplete(wuid);
	                                }else{
	                                	Thread.sleep(1500);
	                                    complete = false;
	                                }
	                              }
	
	                          }
	                          
	
	                       }
	                    }
	        is.close();
	        }catch (Exception e){
	        	System.out.println("---------------Error-ECLSoap:doSoap---------------");
	            System.out.println(e);
	            e.printStackTrace();
	            isError = true;
	            errorCnt++;
	        }	
        }
        return complete;
    }
    
    
    /*
     * ResultsSoapCall
     * @accepts String
     * @returns InputStream
     * 
     * Accepts the wuid from the created job
     * Returns an InputStream that is the SOAP response
     */
    public InputStream ResultsSoapCall(String wuid, String resultName){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
                "<soap:Body>"+
                "<WUResult xmlns=\"urn:hpccsystems:ws:wsworkunits\">"+
                "<Wuid>"+wuid+"</Wuid>"+
                "<Sequence></Sequence>"+
                "<ResultName>"+resultName+"</ResultName>"+
                "<LogicalName></LogicalName>"+
                "<Cluster></Cluster>"+
                "<Start></Start>"+
                "<Count>"+maxReturn+"</Count>"+
                "</WUResult>"+
                "</soap:Body>"+
                "</soap:Envelope>";
        //System.out.println("XML for Resutls:" + xml);
        //String path = "/WsWorkunits/WUInfo";
        String path = "/WsWorkunits/WUResult?ver_=1.38";
        InputStream is = this.doSoap(xml, path);
        return is;
    }
    

    /*
     * createAndUpdateSoapCall
     * @accepts String
     * @returns String
     * 
     * Accepts the ecl query and creates a job on the cluster
     * Returns A String consisting of the WUID for this new job
     * the WUID is needed for all aditional soap calls related to this
     * job
     */
    public String createAndUpdateSoapCall(String query){
        
        String wuid = "";
         
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + 
          "<soapenv:Body>" + 
             "<WUCreateAndUpdate xmlns=\"urn:hpccsystems:ws:wsworkunits\">" + 
                "<Jobname>" + this.jobName + "</Jobname>" + 
                "<QueryText>" + query + "</QueryText>" + 
                "<SubmitID>" + user + "</SubmitID>" + 
               " <ApplicationValues>" + 
                   "<ApplicationValue>" + 
                      "<Application>org.hpccsystems.spoon</Application>" + 
                      "<Name>path</Name>" +
                      "<Value>/Spoon" + outputName + "/Spoon" + outputName + ".ecl</Value>" + 
                   "</ApplicationValue>" + 
                   	"<ApplicationValue>" + 
                   		"<Application>org.hpccsystems.spoon</Application>" + 
                   		"<Name>owner</Name>" +
                   		"<Value>" + user + "</Value>" + 
                   	"</ApplicationValue>" + 
                "</ApplicationValues>" + 
                "<DebugValues>" +
                	"<DebugValue>" +
                		"<Name>created_for</Name>" +
                		"<Value>" + user + "</Value>" +
                	"</DebugValue>" +
                "</DebugValues>" +
             "</WUCreateAndUpdate>" + 
          "</soapenv:Body>" + 
        "</soapenv:Envelope>";
        String path = "/WsWorkunits/WUCreateAndUpdate";
        InputStream is = this.doSoap(xml, path);
        
        try{
            Map response = this.parse(is);
            wuid = (String)response.get("Wuid");
         }catch (Exception e){
            System.out.println(e);
         }
        
        return wuid;
    }
    
    /*
     * submitSoapCall
     * @accepts String
     * @returns void
     * 
     * executes the submit Soap Call
     * returns an nothing, this is a blind call must use ResultsSoapCall
     * to discover the status of the submit
     */
    public void submitSoapCall(String wuid){
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
               "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
                  "<soapenv:Body>"+
                     "<WUSubmit xmlns=\"urn:hpccsystems:ws:wsworkunits\">"+
                        "<Wuid>" + wuid + "</Wuid>"+
                        "<Cluster>" + this.cluster + "</Cluster>"+
                     "</WUSubmit>"+
                  "</soapenv:Body>"+
               "</soapenv:Envelope>";
        
        String path = "/WsWorkunits/WUSubmit";
        InputStream is2 = this.doSoap(xml, path);
        
        //need to check for errors here
    }
    
    /*
     * InfoSoapCall
     * 
     * @accepts String
     * @returns InputStream
     * 
     * Calls the Thor clustor to get Info, not currently utilized
     */
    public InputStream InfoSoapCall(String wuid){
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
               "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+
                  "<soapenv:Body>"+
                     "<WUInfo xmlns=\"urn:hpccsystems:ws:wsworkunits\">"+
                        "<Wuid>" + wuid + "</Wuid>"+
                        "<IncludeGraphs>true</IncludeGraphs>"+
            "<IncludeSourceFiles>true</IncludeSourceFiles>"+
            "<IncludeResults>true</IncludeResults>"+
            "<IncludeResultsViewNames>true</IncludeResultsViewNames>"+
            "<IncludeApplicationValues>true</IncludeApplicationValues>"+
            "<SuppressResultSchemas>false</SuppressResultSchemas>"+
                     "</WUInfo>"+
                  "</soapenv:Body>"+
               "</soapenv:Envelope>";
        
        String path = "/WsWorkunits/WUInfo";
        InputStream is = this.doSoap(xml, path);
        return is;
    }
    
    
    
     /*
     * InfoSoapCall
     * 
     * @accepts String
     * @returns InputStream
     * 
     * Calls the Thor clustor to get Info
     */
    public InputStream InfoDetailsCall(String wuid){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<WUInfoDetails xmlns=\"urn:hpccsystems:ws:wsworkunits\">"
                + "<Wuid>" + wuid + "</Wuid>"
                + "<TruncateEclTo64k></TruncateEclTo64k>"
                + "<Type></Type>"
                + "<IncludeExceptions></IncludeExceptions>"
                + "<IncludeGraphs></IncludeGraphs>"
                + "<IncludeSourceFiles></IncludeSourceFiles>"
                + "<IncludeResults></IncludeResults>"
                + "<IncludeResultsViewNames></IncludeResultsViewNames>"
                + "<IncludeVariables></IncludeVariables>"
                + "<IncludeTimers></IncludeTimers>"
                + "<IncludeDebugValues></IncludeDebugValues>"
                + "<IncludeApplicationValues></IncludeApplicationValues>"
                + "<IncludeWorkflows></IncludeWorkflows>"
                + "<SuppressResultSchemas></SuppressResultSchemas>"
                + "<ThorSlaveIP></ThorSlaveIP>"
                + "</WUInfoDetails>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        
        String path = "/WsWorkunits/WUInfoDetails";
        InputStream is = this.doSoap(xml, path);
        return is;
    }
    
     public static ArrayList parseResultList(InputStream is) throws Exception {
        ArrayList results = new ArrayList();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document dom = db.parse(is);

        Element docElement = dom.getDocumentElement();

        NodeList dsList = docElement.getElementsByTagName("Results");
        if (dsList != null && dsList.getLength() > 0) {

           //ArrayList dsArray = new ArrayList();

           //results = dsArray;

            for (int i = 0; i < dsList.getLength(); i++) {
                Element ds = (Element) dsList.item(i);
                NodeList rowList = ds.getElementsByTagName("ECLResult");

                if (rowList != null && rowList.getLength() > 0) {

                    ArrayList rowArray = new ArrayList();
                   // dsArray.add(rowArray);

                    for (int j = 0; j < rowList.getLength(); j++) {
                        Element row = (Element) rowList.item(j);
                        
                        NodeList columnList = row.getChildNodes();
                        
                        //ArrayList columnsArray = new ArrayList();
                        //rowArray.add(columnsArray);
                        //System.out.println("test");
                        ArrayList columnsArray = new ArrayList();
                        for (int k = 0; k < columnList.getLength(); k++) {
                            
                            
                            if(columnList.item(k).getNodeName().equals("Name")){
                                //System.out.println("Name: " + columnList.item(k).getNodeName());
                                //System.out.println("Value: " + columnList.item(k).getTextContent());
                                columnsArray.add(new Column(columnList.item(k).getNodeName(), columnList.item(k).getTextContent()));
                            }
                           
                           
                        }
                         rowArray.add(columnsArray);
                       
                    }
                     results.add(rowArray);
                }

            }
        }
        Iterator iterator = results.iterator();
        return results;
     }
    
     /*
     * parseDirect
     * @accepts InputStream
     * returns ArrayList
     * 
     * This function is copied from ECLDirect it tranlates the xml results
     * into a arraylist.
     */
    public static ArrayList parseDirect(String xml) throws Exception {
        ArrayList results = null;
        xml = "<?xml version=\"1.0\"?><root>" + xml + "</root>";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Document dom = db.parse(is);

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
                            //System.out.println("Name: " + columnList.item(k).getNodeName());
                           // System.out.println("Value: " + columnList.item(k).getTextContent());
                            columnsArray.add(new Column(columnList.item(k).getNodeName(), columnList.item(k).getTextContent()));
                           
                           
                        }
                    }
                }

            }
        }
        Iterator iterator = results.iterator();
        return results;
    }
    
    public static String fixXML(String in){
        int index = in.indexOf("<Dataset");
        
        return in.substring(index,in.length());

        
    }
    
    
    
    
    
    /*
     * parseResults 
     * @accepts InputStream
     * returns ArrayList
     * 
     * Parses out the data from the results soap call, the string is the xml 
     * that is the same as returned from the eclDirect
     */
    public ArrayList parseResults(InputStream xml) throws Exception {
        ArrayList results = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
       
        Document dom = db.parse(xml);

        Element docElement = dom.getDocumentElement();
        
        NodeList dsList = docElement.getElementsByTagName("WUResultResponse");
        if (dsList != null && dsList.getLength() > 0) {

            ArrayList dsArray = new ArrayList();

            results = dsArray;

            for (int i = 0; i < dsList.getLength(); i++) {
                //System.out.println("here");
                Element ds = (Element) dsList.item(i);
                NodeList rowList = ds.getElementsByTagName("Result");

                if (rowList != null && rowList.getLength() > 0) {


                    for (int j = 0; j < rowList.getLength(); j++) {
                        Element row = (Element) rowList.item(j);
                       // System.out.println(j);
                        NodeList columnList = row.getChildNodes();

                        
                        for (int k = 0; k < columnList.getLength(); k++) {
                          // System.out.println("colName: " +  columnList.item(k).getNodeName());
                          //  System.out.println("colVal: " + columnList.item(k).getTextContent());
                            if(columnList.item(k).getNodeName().equals("#text")){
                                
                                results = parseDirect(columnList.item(k).getTextContent());
                              
                            }
                        }
                    }
                }

            }
        }
        
        return results;
    }
    
     /*
     * parseResults 
     * @accepts InputStream
     * returns ArrayList
     * 
     * Parses out the data from the results soap call, the string is the xml 
     * that is the same as returned from the eclDirect
     */
    public static String fetchXML(InputStream xml) throws Exception {
        String results = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
       
        Document dom = db.parse(xml);

        Element docElement = dom.getDocumentElement();
        
        NodeList dsList = docElement.getElementsByTagName("WUResultResponse");
        if (dsList != null && dsList.getLength() > 0) {

            for (int i = 0; i < dsList.getLength(); i++) {
               // System.out.println("here");
                Element ds = (Element) dsList.item(i);
                NodeList rowList = ds.getElementsByTagName("Result");

                if (rowList != null && rowList.getLength() > 0) {


                    for (int j = 0; j < rowList.getLength(); j++) {
                        Element row = (Element) rowList.item(j);
                       // System.out.println(j);
                        NodeList columnList = row.getChildNodes();

                        
                        for (int k = 0; k < columnList.getLength(); k++) {
                          // System.out.println("colName: " +  columnList.item(k).getNodeName());
                           // System.out.println("colVal: " + columnList.item(k).getTextContent());
                            if(columnList.item(k).getNodeName().equals("#text")){
                                
                                results = columnList.item(k).getTextContent();
                              
                            }
                        }
                    }
                }

            }
        }
        
        return results;
    }

    /*
     * parse
     * @accepts InputStream
     * @returns Map
     * 
     * Currently takes the createandupdate calls response and places the data 
     * in a Map to be returned.  This may make sense to refactor so it just returns 
     * the wuid
     */
     public  Map parse(InputStream xml) throws Exception {
        ArrayList results = new ArrayList();

        Map<String, String> map = new HashMap<String, String>();
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);

        doc.getDocumentElement().normalize();
        
        NodeList nList = doc.getElementsByTagName("Workunit");
       // System.out.println("-----------PARSE- " + nList.getLength() + " -----------");
 
		for (int temp = 0; temp < nList.getLength(); temp++) {
                 //   System.out.println("-----------"+temp+"------------");
		   Node nNode = nList.item(temp);
		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
		      Element eElement = (Element) nNode;
                      NodeList nl = eElement.getChildNodes();
                      
                      for (int temp1 = 0; temp1 < nl.getLength(); temp1++) {
                          //System.out.append("parsing node --");
                          Node node = nl.item(temp1);
                          Element elem = (Element) node;
                          if(node.getNodeName() != null)
                         // System.out.println("Node Name: " + node.getNodeName());
                         // System.out.println("Node Value: " + getTagValue(node.getNodeName(), eElement));
                          map.put(node.getNodeName(), getTagValue(node.getNodeName(), eElement));
                         // System.out.println("MAP PUT");
                      }
                      
                     
                      //System.out.println("TEST MAP");
		      //System.out.println("WUID : " + map.get("Wuid") );
                      // System.out.println("WUID : " + getTagValue("Wuid", eElement));
		      results.add(getTagValue("Wuid", eElement));
 
		   }
		}
        
        return map;
    }
     
     
    /*
      * getTagValue
      * @accepts String, Element
      * @returns String
      * 
      * Accepts the tag and element from the xml tree and returns its value.
      * this is a helper function for the other xml parse functions
      */
    private  String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        String out = "";
        if(nlList != null){
            Node nValue = (Node) nlList.item(0);
            if(nValue != null){
               
                out = nValue.getNodeValue();
                
            }
            
        }
        return out;
    }
    
    /*
     * doSoap
     * @accepts String, String
     * @returns InputStream
     * 
     * Accepts two strings xmldata and path to soap call (hostname is a global variable)
     * returns InputStream from the  URLConnection
     */
    public InputStream doSoap(String xmldata, String path){
       ArrayList response = new ArrayList();
       String xml = "";
       URLConnection conn = null;
       boolean isError = false;
       boolean isSuccess = false;
      
       int errorCnt = 0;
       InputStream is = null;
       while(errorCnt < 5 && !isSuccess && !isLogonFail){
	       try {
	
	
	    	   	//System.out.println("ECLSoap doSoap -- User:"+user+ " " + "Pass:" + pass);
	            
	    	   	ECLAuthenticator eclauth = new ECLAuthenticator(user,pass);
	    	   	
	    	   	
	    	   	Authenticator.setDefault(eclauth);
	             
	          
	            //String encoding = new sun.misc.BASE64Encoder().encode ((user+":"+pass).getBytes());
	            String host = "http://"+hostname+":"+port+path;
	            //System.out.println("HOST: " + host);
	            URL url = new URL(host);
	            
	            
	             // Send data
	            conn = url.openConnection();
	            conn.setDoOutput(true);
	            //added back in since Authenticator isn't allways called and the user wasn't passed if the server didn't require auth
	            if(!user.equals("")){
	            	String authStr = user + ":" + pass;
	            	//System.out.println("USER INFO: " + authStr);
	            	BASE64Encoder encoder = new BASE64Encoder();
	            	String encoded = encoder.encode(authStr.getBytes());
	            	
	            	
	            	conn.setRequestProperty("Authorization","Basic "+encoded);
	            }
	            
	            conn.setRequestProperty("Post", path + " HTTP/1.0");
	            conn.setRequestProperty("Host", hostname);
	            conn.setRequestProperty("Content-Length", ""+xmldata.length() );
	            conn.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
	
	            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	            wr.write(xmldata);
	            wr.flush();
	            //wr.close();
	           // if(conn.get)
	            if(conn instanceof HttpURLConnection){
	            	HttpURLConnection httpConn = (HttpURLConnection)conn;
	            	int code = httpConn.getResponseCode();
	            	System.out.println("Connection code: " + code);
	            	if(code == 200){
	            		is =  conn.getInputStream();
	            		isSuccess = true;
	            		System.out.println("Connection success code 200 ");
	            	}else if (code == 401){
	            		isSuccess = false;
	            		isLogonFail = true;
	            		System.out.println("Permission Denied");
	            	}
	            }
	            //return conn.getInputStream();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            errorCnt++;
	        }finally{
	        	if(conn != null){
	        		
	        	}
	        }
	        if(!isSuccess){
	        	try{
	        		Thread.sleep(3500);
	        	}catch (Exception e){
	        		System.out.println("couldn't sleep thread");
	        	}
	        }
       }
       // return new HashMap<String, String>();
          return is;
    }
    

   /*
     * compileECL
     * 
     * @accepts String
     * @returns String
     * 
     * Accepts the ECL code and calls the command line eclcc to compile the code
     * Currently it pulls in the the ML library by default
     * Requres that the ecl IDE be installed and the ML library
     * 
     */
    private String compileECL(String ecl){
    	
        String inFile =  this.outputName + baseFileName;
        String outFile = this.outputName + baseFileName;
       
        
        String inFilePath = this.tempDir + inFile;
        String outFilePath = this.tempDir + outFile;
        
        
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.tempDir + inFile));
            out.write(ecl);
            out.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }   
        
        try{

            
            String logFile = this.tempDir + this.outputName + "_log.log";
            
            String c = eclccInstallDir;
            if (System.getProperty("os.name").startsWith("Windows")) {
                c += "eclcc.exe";
            }else{
                c += "eclcc";
            }
            String paramSalt = "";

            System.out.println("_________________________ECLCC_______________________________");
            
            boolean pathExists = (new File(eclccInstallDir)).exists();
	    	if(!pathExists){
	    		errorCount++;
	    		errorText += "Unable to locate the ecl compiler, Check Global Variables\r\n";
	    	}
	    	
            ArrayList<String> paramsAL = new ArrayList<String>();
            paramsAL.add(c);
            paramsAL.add("-E");
            paramsAL.add("-v");
            
            if(this.includeSALT){
                paramSalt += "-legacy";
                paramsAL.add(paramSalt);
            }
            paramsAL.add("--logfile");
            paramsAL.add(logFile);
            
            if(this.includeML){
            	paramsAL.add("-I");
            	paramsAL.add(this.mlPath);
            }
            if(this.includeSALT){
            	paramsAL.add("-I");
            	paramsAL.add(this.SALTPath);
            }
            if(this.saltLib != null && !this.saltLib.equals("")){
            	paramsAL.add("-I");
            	paramsAL.add(this.saltLib);
            }
            //System.out.println("Check for Custom Flags | ECLSoap ");
           // System.out.println("compileFlagsAL size eclsoap: " + compileFlagsAL.size());
            if(compileFlagsAL != null && compileFlagsAL.size() > 0){
            	//System.out.println(" -- has Custom Flags | parsing for process builder -- ");
            	for(int i = 0; i<compileFlagsAL.size(); i++){
            		//System.out.println(" -- loop iteration " + i);
            		if(compileFlagsAL.get(i).length == 2){
            			//System.out.println(" -- -- has array of 2");
            			if(!compileFlagsAL.get(i)[0].equals("")){
            				//System.out.println(" -- -- -- Key: " + compileFlagsAL.get(i)[0]);
            				paramsAL.add(compileFlagsAL.get(i)[0]);
            			}
            			if(!compileFlagsAL.get(i)[1].equals("")){
            				//System.out.println(" -- -- -- Val: " + compileFlagsAL.get(i)[1]);
            				paramsAL.add(compileFlagsAL.get(i)[1]);
            			}
            		}
            	}
            }
            //"-o", outFilePath, inFilePath
            paramsAL.add("-o");
            paramsAL.add(outFilePath);
            paramsAL.add(inFilePath);
            String [] params = new String[paramsAL.size()];
            paramsAL.toArray(params);
            //String[] params = (String[]) paramsAL.toArray();
            ProcessBuilder pb = new ProcessBuilder(params);
            
           // System.out.println("+++++++++++++++++++++");
           // System.out.println("+++++++++++++++++++++");
           // System.out.println("+++++++++++++++++++++");
            //System.out.println("++++++++++Compile ECLSOAP+++++++++++");
           // System.out.println("+++++++++++++++++++++");
            System.out.println(pb.command().toString());
            pb.redirectErrorStream(true); // merge stdout, stderr of process

            File path = new File(eclccInstallDir);
            pb.directory(path);
            Process p = pb.start();
            
            InputStream iError = p.getErrorStream();
            InputStreamReader isrError = new InputStreamReader(iError);
            BufferedReader brErr = new BufferedReader(isrError);
            String lineErr;
            while((lineErr = brErr.readLine()) != null){
                //System.out.println("#####"+lineErr);
                errorText += lineErr + "\r\n";
            }
            
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while((line = br.readLine()) != null){
                //System.out.println(line);
            	
            }

            

            String compiled_ecl = openFile(this.tempDir+outFile);
            //deleteFile(this.tempDir+outFile);
            //deleteFile(this.tempDir+inFile);
            
            return compiled_ecl;
            
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
        
        return null;
        //return ecl;
    }
    
    public static void main(String[] args){
    	System.out.println("Test Compile");
    	ECLSoap es = new ECLSoap();
    	String eclccInstallDir = "C:\\Program Files\\HPCC Systems\\HPCC\\bin\\ver_3_0\\";
    	String tempDir = "";
    	if (System.getProperty("os.name").startsWith("Windows")) {
    		tempDir = System.getProperty("java.io.tmpdir");
        } else {
        	tempDir = System.getProperty("java.io.tmpdir") + "/";
        } 
    	String outputName = "Execute";
   	 	String inFile =  outputName + es.baseFileName;
   	 	String outFile = outputName + es.baseFileName;

   	 	String inFilePath = tempDir + inFile;
   	 	String outFilePath = tempDir + outFile;
     
   	 	//C:\DOCUME~1\CHAMBE~1.RIS\LOCALS~1\Temp\ExecuteSpoonEclCode.ecl
    	   try{
               //call eclcc
               //need to modify -I to include path...
               String includeML = "";
               String includeSalt = "";              
               String includeSaltLib = "";
               
               
               
               String logFile = tempDir + outputName + "_log.log";
             
             
               String c = eclccInstallDir;
               if (System.getProperty("os.name").startsWith("Windows")) {
                   // use eclcc.exe
                           c += "eclcc.exe";
               }else{
                           c += "eclcc";
                           //logFile = "";//don’t use log file for linux
               }
               String paramSalt = "";
               
               //c += logFile + "-E -v" + include + " -o " + outFilePath + " " + inFilePath;
               
               //System.out.println("_________________________ECLCC_______________________________");

               ArrayList<String> al = new ArrayList<String>();
               al.add(c);
               al.add("-E");
               al.add("-v");
               al.add("--logfile");
               al.add(logFile);
               al.add("-o");
               al.add(outFilePath);
               al.add(inFilePath);
               String [] x = new String[al.size()];
               al.toArray(x);
               ProcessBuilder pb = new ProcessBuilder(x);
               System.out.println(pb.command().toString());
               pb.redirectErrorStream(true); // merge stdout, stderr of process

               File path = new File(eclccInstallDir);
               pb.directory(path);
               Process p = pb.start();
               
               InputStream iError = p.getErrorStream();
               InputStreamReader isrError = new InputStreamReader(iError);
               BufferedReader brErr = new BufferedReader(isrError);
               String lineErr = "";
               while((lineErr = brErr.readLine()) != null){
                   System.out.println("#####"+lineErr);
                   
               }
               
              
               
               InputStream is = p.getInputStream();
               InputStreamReader isr = new InputStreamReader(is);
               BufferedReader br = new BufferedReader(isr);
               String line;
               

               //System.out.println(c);
               

               while((line = br.readLine()) != null){
                   System.out.println(line);
               }
               
               
               
           }catch (Exception e){
               System.out.println(e.toString());
               e.printStackTrace();
           }
    	   
    	   
    	   es.setCluster("mythor");
    	   es.setHostname("10.239.227.6");
    	   es.setEclccInstallDir(eclccInstallDir);
    	   es.setJobName("test");
    	   es.executeECL("output('hi');");
    	   
    }
    
    /*
     * deleteFile
     * @accepts String
     * @returns String
     * 
     * Opesn a file and returns its contents as a string
     */
    private static void deleteFile(String filePath){
        File f = new File(filePath);
        f.delete();
    }
    
    /*
     * openFile
     * @accepts String
     * @returns String
     * 
     * Opesn a file and returns its contents as a string
     */
    private static String openFile(String filePath){
        StringBuffer fileData = new StringBuffer(1000);
        //System.out.println("++++++++++++++++ Open File: " + filePath);
         try{
        
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
         }catch (Exception e){//Catch exception if any
        System.err.println("Error: " + e.getMessage());
     }
        return fileData.toString();
    }

    /*
     * ECLAuthenticator
     * 
     * Hnadles the http authentication for the soap request
     */
    static class ECLAuthenticator extends Authenticator {
        public String user;
        public String pass;
        String hostname = getRequestingHost();
        
        public ECLAuthenticator(String kuser,String kpass){
            //System.out.println("_________Hostname_______"+hostname);
            user=kuser;
            pass=kpass;
        }
        public PasswordAuthentication getPasswordAuthentication() {
            // I haven't checked getRequestingScheme() here, since for NTLM
            // and Negotiate, the usrname and password are all the same.
           // System.err.println("Feeding username and password for " + getRequestingScheme() + " " + user + ":" + pass +"@"+hostname);
            PasswordAuthentication p = new PasswordAuthentication(user, pass.toCharArray());
           // System.out.println("_________Hostname_______"+hostname);
            return p;
        }
    }
 
 
 }