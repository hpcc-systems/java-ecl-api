package org.hpccsystems.javaecl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ClusterInfoSoap {

	ECLSoap soap;
	
	private String serverHost;
    private int serverPort;

	public ClusterInfoSoap(String serverHost,int serverPort){
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}
	
	public String buildSoapEnv (String clusterType){
		//http://192.168.80.132:8010/WsTopology/TpClusterQuery?ver_=1.18&wsdl
		
		String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
						"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
							"<soap:Body>" +
								"<TpClusterQuery xmlns=\"urn:hpccsystems:ws:wstopology\">" +
								"<Type>" + clusterType + "</Type>" +
								"</TpClusterQuery>" +
							"</soap:Body>" +
						"</soap:Envelope>";
		/*
		String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
				"<soap:Body>" +
				"<TpTargetClusterQuery xmlns=\"urn:hpccsystems:ws:wstopology\">" +
				"<Type></Type>" +
				"<Name></Name>" +
				"<ShowDetails></ShowDetails>" +
				"</TpTargetClusterQuery>" +
				"</soap:Body>" +
				"</soap:Envelope>";
		*/
		return soap;
	}
	
	public ArrayList<String> processReturn(InputStream is) throws Exception{
		ArrayList results = new ArrayList();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document dom = db.parse(is);

        Element docElement = dom.getDocumentElement();

        NodeList dsList = docElement.getElementsByTagName("TpClusters");
        if (dsList != null && dsList.getLength() > 0) {
        	
           //ArrayList dsArray = new ArrayList();

           //results = dsArray;

            for (int i = 0; i < dsList.getLength(); i++) {
            	//System.out.println("Cluster");
                Element ds = (Element) dsList.item(i);
                NodeList rowList = ds.getElementsByTagName("TpCluster");
                if (rowList != null && rowList.getLength() > 0) {

                    ArrayList rowArray = new ArrayList();

                    for (int j = 0; j < rowList.getLength(); j++) {
                        Element row = (Element) rowList.item(j);
                        
                        NodeList columnList = row.getChildNodes();
                        for (int k = 0; k < columnList.getLength(); k++) {
                            
                            
                            if(columnList.item(k).getNodeName().equals("Name")){
                                //System.out.println("Name: " + columnList.item(k).getNodeName());
                               // System.out.println("Value: " + columnList.item(k).getTextContent());
                                results.add(columnList.item(k).getTextContent());
                            }  
                        }  
                    }
                }

            }
        }
        Iterator iterator = results.iterator();
        return results;
	}
	
	/*
	 * fetchClusters
	 * 
	 * @accepts String ("ThorCluster", "RoxieCluster", "")
	 * @returns String[]
	 */
	public String[] fetchClusters(String clusterType){
		
		soap = new ECLSoap();
		soap.setHostname(serverHost);
		soap.setPort(this.serverPort);
		
		String path = "/WsTopology/TpClusterQuery";
        InputStream is = soap.doSoap(buildSoapEnv(clusterType), path);
        
		try{
			ArrayList<String> c = processReturn(is);
			String[] clusters = new String[c.size()];
		    return c.toArray(clusters);
		}catch (Exception e){
			System.out.println("error");
			System.out.println(e);
		}
		return null;
	}
	
	
	public static void main(String[] args){
		ClusterInfoSoap c = new ClusterInfoSoap("192.168.80.132", 8010);
		String[] test = c.fetchClusters("");
		System.out.println("You have " + test.length + " Clusters");
		for (int i = 0; i<test.length; i++){
			System.out.println(test[i]);
		}
		
	}
}
