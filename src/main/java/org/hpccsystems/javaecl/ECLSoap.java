/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;
import java.net.*;
import java.io.*;
import java.util.*;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
    
    private String hostname = "192.168.80.130";
    private int port = 8010;
    
    private String mlPath = "";
    private String eclccInstallDir = "C:\\Program Files\\HPCC Systems\\HPCC\\bin\\ver_3_6\\";
    private String jobName = "Spoon-job";
    private String cluster = "hthor";
    private boolean includeML = false;
    
    private String outputName = "";
    
    private String wuid = "";
    
    private String tempDir = "";
    
    private int errorCount = 0;
    private int warningCount = 0;
    
    

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
        this.outputName = outputName;
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
    
    public ECLSoap() {
        this.tempDir = System.getProperty("java.io.tmpdir");
        //System.out.println("OS Temp Dir is: " + tempDir);
    }
    public String syntaxCheck(String ecl){
        String res = "";
        int test = 0;
        String inFile = this.outputName + "-check-spoon-eclCode.ecl";

         //write ecl to file
        
        //String inFilePath = "\"" + eclccInstallDir + inFile + "\"";
        String inFilePath = "\"" + this.tempDir + inFile + "\"";
         try {
            //System.out.println("Created File (synTaxCheck): " + eclccInstallDir + inFile);
            //BufferedWriter out = new BufferedWriter(new FileWriter(eclccInstallDir + inFile));
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
            //call eclcc
            //need to modify -I to include path...
            String include = "";
            if(this.includeML){
                include = " -I \"" + this.mlPath +"\"";
            }
            String logFile = "--logfile \"" + this.tempDir + this.outputName + "_syntax_log.log\" ";
           // System.out.println("LogFIle: " + this.tempDir + this.outputName + "_syntax_log.log");
            String c = "\"" + eclccInstallDir + "eclcc\" " + logFile + "-c -syntax" + include + " " + inFilePath;


            ProcessBuilder pb = new ProcessBuilder(c);
            pb.redirectErrorStream(true); // merge stdout, stderr of process
            //System.out.println("Start Process Builder");
            File path = new File(eclccInstallDir);
            //System.out.println("1");
            pb.directory(path);
           // System.out.println("2");
            Process p = pb.start();
            //System.out.println("3");
            
           // int pStatus = p.waitFor();
           // System.out.println("4");
            
           // System.out.println("InputStream");
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            
           // System.out.println("STATUS: " + pStatus);
            while((line = br.readLine()) != null){
            	//System.out.println("#####<InputStream> "+line);
                //res += "<InputStream> " + line+"\r\n";
                res += cleanError(line)+"\r\n";
            }
            System.out.println("Finished InputStream");
            
            InputStream iError = p.getErrorStream();
            InputStreamReader isrError = new InputStreamReader(iError);
            BufferedReader brErr = new BufferedReader(isrError);
            String lineErr;
            while((lineErr = brErr.readLine()) != null){
                //System.out.println("#####<ErrorStream> "+lineErr);
                //res += "<ErrorStream> " + lineErr+"\r\n";
                res += cleanError(lineErr)+"\r\n";
            }
            //System.out.println("Finished ErrorStream");
                        
           
            
            /*

            InputStream is2 = p.getErrorStream();
            int pStatus2 = p.waitFor();
            InputStreamReader isr2 = new InputStreamReader(is2);
            BufferedReader br2 = new BufferedReader(isr2);
            String line2;

            while((line2 = br2.readLine()) != null){
                //System.out.println("****"+line2);
                res += line2 +"\r\n";
            }
			*/


            //deleteFile(eclccInstallDir+inFile);
            deleteFile(this.tempDir+inFile);
            
          //  System.out.println("Finished compile check");
            
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
	                 	this.errorCount = Integer.parseInt(ec);
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
	                	this.warningCount = Integer.parseInt(wc);
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
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<WUWaitComplete xmlns=\"urn:hpccsystems:ws:wsworkunits\">"
                + "<Wuid>"+wuid+"</Wuid>"
                + "<Wait>500000</Wait>"
                + "<ReturnOnWait>true</ReturnOnWait>"
                + "</WUWaitComplete>"
                + "</soap:Body>"
                + "</soap:Envelope>";
        String path = "/WsWorkunits/WUInfo";
        InputStream is = this.doSoap(xml, path);        
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

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
                              System.out.append("parsing node --");
                              Node node = nl.item(temp1);
                              Element elem = (Element) node;
                              if((node.getNodeName()).equals("StateID")){
                                  String val = getTagValue(node.getNodeName(), eElement);
                               // System.out.println("Node Value: " + val);
                                if(val.equals("3")){
                                    complete = true;
                                }else if(val.equals("1") || val.equals("2") || val.equals("11")){
                                    //Thread.sleep(500);
                                    complete = isComplete(wuid);
                                }else{
                                    complete = false;
                                }
                              }

                          }


                       }
                    }
        
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
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
                "<Count></Count>"+
                "</WUResult>"+
                "</soap:Body>"+
                "</soap:Envelope>";
        
        String path = "/WsWorkunits/WUInfo";
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
               " <ApplicationValues>" + 
                   "<ApplicationValue>" + 
                      "<Application>org.hpccsystems.eclide</Application>" + 
                      "<Name>path</Name>" +
                      "<Value>/HelloWorld/HelloWorld.ecl</Value>" + 
                   "</ApplicationValue>" + 
                "</ApplicationValues>" + 
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
     * Calls the Thor clustor to get Info, not currently utilized
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
                          System.out.append("parsing node --");
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
       try {

              String user = "hpccdemo";
              String pass = "hpccdemo";

              Authenticator.setDefault(new ECLAuthenticator(user,pass));
             
          
            String encoding = new sun.misc.BASE64Encoder().encode ((user+":"+pass).getBytes());
            String host = "http://"+hostname+":"+port+path;
            //System.out.println("HOST: " + host);
            URL url = new URL(host);
            
            
             // Send data
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization","Basic "+encoding);
            conn.setRequestProperty("Post", path + " HTTP/1.0");
            conn.setRequestProperty("Host", hostname);
            conn.setRequestProperty("Content-Length", ""+xmldata.length() );
            conn.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(xmldata);
            wr.flush();
            //wr.close();
            
             
            return conn.getInputStream();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       // return new HashMap<String, String>();
          return null;
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
        String inFile =  this.outputName + "-spoon-eclCode.ecl";
        String outFile = this.outputName + "-spoon-eclOut.ecl";
       
         //write ecl to file
        
        //String inFilePath = "\"" + eclccInstallDir + inFile + "\"";
        //String outFilePath = "\"" + eclccInstallDir + outFile + "\"";
        
        String inFilePath = "\"" + this.tempDir + inFile + "\"";
        String outFilePath = "\"" + this.tempDir + outFile + "\"";
        
        
        try {
            //System.out.println("Created File (compileECL): " + eclccInstallDir + inFile);
            //BufferedWriter out = new BufferedWriter(new FileWriter(eclccInstallDir + inFile));
            
            //System.out.println("Created File (compileECL): " + this.tempDir + inFile);
            BufferedWriter out = new BufferedWriter(new FileWriter(this.tempDir + inFile));
            out.write(ecl);
            out.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }   
        
        try{
            //call eclcc
            //need to modify -I to include path...
            String include = "";
            if(this.includeML){
                include = " -I \"" + this.mlPath +"\"";
            }else{
            	//System.out.println("NO ML LIBRARY INCLUDED!");
            }
            String logFile = "--logfile " + this.tempDir + this.outputName + "_log.log ";
            String c = "\"" + eclccInstallDir + "eclcc\" " + logFile + "-E -v" + include + " -o " + outFilePath + " " + inFilePath;
            
           // System.out.println("_________________________ECLCC_______________________________");
           // System.out.println(c);
            ProcessBuilder pb = new ProcessBuilder(c);
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
                
            }
            
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            

            //System.out.println(c);
            

            while((line = br.readLine()) != null){
                //System.out.println(line);
            }

            
            //String compiled_ecl = openFile(eclccInstallDir+outFile);
            //deleteFile(eclccInstallDir+outFile);
            //deleteFile(eclccInstallDir+inFile);
            String compiled_ecl = openFile(this.tempDir+outFile);
            deleteFile(this.tempDir+outFile);
            deleteFile(this.tempDir+inFile);
            
            //System.out.println("finished compileECL");
            //load file as string
            return compiled_ecl;
            
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
        
        return null;
        //return ecl;
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
            System.err.println("Feeding username and password for " + getRequestingScheme());
            PasswordAuthentication p = new PasswordAuthentication(user, pass.toCharArray());
           // System.out.println("_________Hostname_______"+hostname);
            return p;
        }
    }
 
 
 }