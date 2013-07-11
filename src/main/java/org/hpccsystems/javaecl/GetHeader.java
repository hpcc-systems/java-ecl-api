/**
 * 
 */
package org.hpccsystems.javaecl;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Iterator;



public class GetHeader {
	
	//private final static Log log = LogFactory.getLog(GetHeader.class);
	ECLSoap soap;
	
	private String serverHost;
	private int serverPort;
	private String user;
	private String pass;
	public boolean isLogonFail = false;
	
	public GetHeader(String serverHost,int serverPort,String user, String pass){
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.user=user;
		this.pass=pass;
	}
	public GetHeader(){
		
	}
	public List<Header> retrieveHeaderInformation(String fileName) {
				
		List<Header> headers = new ArrayList<Header>();
		
		try
		{
			InputStream is = buildSoapRequest(fileName);
			headers = parseHeaderData(is);						
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
		return headers;
		
	}
	
	public InputStream buildSoapRequest (String fileName){

		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
							"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
								"<soap:Body>" +
									"<DFUSearchData xmlns=\"urn:hpccsystems:ws:wsdfu\">" +
										"<OpenLogicalName>" + fileName + "</OpenLogicalName>" +
									"<StartIndex>0</StartIndex><EndIndex>100</EndIndex>" +
									"<SchemaOnly>0</SchemaOnly>" +
									"</DFUSearchData>" +
								"</soap:Body>" +
							"</soap:Envelope>";
		
		String path = "/WsDfu/DFUSearchData?ver_=1.2";
		ECLSoap soap = new ECLSoap();
		soap.setHostname(serverHost);
		soap.setPort(this.serverPort);
		soap.setUser(this.user);
		soap.setPass(this.pass);
		InputStream is = soap.doSoap(xml, path);
		isLogonFail = soap.isLogonFail;
		return is;
	}

	

	public List<Header> parseHeaderData(InputStream is) throws Exception {
		
		List<Header> results = new ArrayList<Header>();
		
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();       
        Document dom = db.parse(is);
        

        Element docElement = dom.getDocumentElement();
        NodeList dfuResponse = docElement.getElementsByTagName("DFUSearchDataResponse");       
        if (dfuResponse != null && dfuResponse.getLength() > 0) 
        {        	
            for (int i = 0; i < dfuResponse.getLength(); i++) 
            {                     	
                Element dfuResponseElement = (Element) dfuResponse.item(i);
                NodeList resultResponse = dfuResponseElement.getElementsByTagName("Result");
                if (resultResponse != null && resultResponse.getLength() > 0) 
                {
                    for (int j = 0; j < resultResponse.getLength(); j++) 
                    {                                      	
                    	Element resultResponseElement = (Element) resultResponse.item(j);                   	
                    	String responseStr = resultResponseElement.getTextContent();
                    	//System.out.println(responseStr);
                    	//String lineStr = StringUtils.substringBetween(responseStr, "<line>", "</line>");
                    	int start = responseStr.indexOf("<line>") + 6;
                    	//System.out.println(start);
                    	int end = responseStr.indexOf("</line>");
                    	String lineStr = responseStr.substring(start,end);
                    	//log.debug("Line String: " + lineStr);
                    	String[] parts = lineStr.split("[,\\-:\\|]");
                    	
                        for (int k = 0; k < parts.length; k++) 
                        {      
                        	Header entry = new Header();
                        	entry.setColumnName(parts[k]);
                        	entry.setDataType("String");
                        	results.add(entry);
                        }                	                      	                                   	
                        
                    }
                }

            }
        }
      
        return results;
	}
	
	public static void main(String[] args) {

		String hostname = "10.239.227.6";
		int port = 8010;
		String fileName = "headerparse::emp";
		
		List<Header> headers = new ArrayList<Header>();
		
		GetHeader header = new GetHeader(hostname,port,"","");		
		headers = header.retrieveHeaderInformation(fileName);
		
		for (Iterator<Header> iter = headers.iterator(); iter.hasNext();) 
		{
			Header entry = (Header) iter.next();
			System.out.println("Column Name: " + entry.getColumnName());		
			System.out.println("Data Type: " + entry.getDataType());
		}
					
	}
	    
}
