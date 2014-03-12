package org.hpccsystems.javaecl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class ImportSaltProfileAsProject {
	
	//class vars to know where the files are
	private String optimizedLayoutFileName = "";//fileBase + "\\Dataprofiling_OptimizedLayout.csv";
	private String optimizedJobFile = "";//fileBase + "\\optimizedLayoutJob.kjb";
	private String profileJob = "";//"C:\\Spoon Demos\\tim-eda-demo\\dataprofile.kjb";
	
	//helper class vars no need to expose external to the script
	final static Charset ENCODING = StandardCharsets.UTF_8;

	private String mapperRecList = "";
	private String recordList = "";
	
	public ImportSaltProfileAsProject(String optimizedJobFile, String profileJob, String optimizedLayoutFileName){
		this.optimizedLayoutFileName = optimizedLayoutFileName;
		this.optimizedJobFile = optimizedJobFile;
		this.profileJob = profileJob;
	}
	
	public static void main(String args[]){
		
		System.out.println("Read in File");
		String fileBase = "C:\\Spoon Demos\\tim-eda-demo\\dataprofileOut";
		String optimizedLayoutFileName = fileBase + "\\Dataprofiling_OptimizedLayout.csv";
		String optimizedJobFile = fileBase + "\\optimizedLayoutJob.kjb";
		String profileJob = "C:\\Spoon Demos\\tim-eda-demo\\dataprofile.kjb";
		ImportSaltProfileAsProject ispap = new ImportSaltProfileAsProject(optimizedJobFile, profileJob, optimizedLayoutFileName);
		try{	
			ispap.processRecLayoutFile(optimizedLayoutFileName);
			ispap.parseProfileJob();
		}catch (Exception e){
			System.out.println("error");
			e.printStackTrace();
		}
		System.out.println("Finished");
	}
	
	public  void parseProfileJob() throws IOException, SAXException, ParserConfigurationException{
		String projectXML = buildProject();
		String outputXML = buildOutput();
		String newHopXML = buildHops();
		InputStream is = new FileInputStream(profileJob);
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db;
	        Document dom = null;
			try {
				db = dbf.newDocumentBuilder();

				try {
					dom = db.parse(is);
				} catch (SAXException e) {
					e.printStackTrace();
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        Element docElement = dom.getDocumentElement();
	       
	        
	        NodeList eList = docElement.getElementsByTagName("entry");

	        int nodeIndex = 0;
	        int datasetIndex = 0;
	        if (eList != null && eList.getLength() > 0) {
	        	
	        	for (int i = 0; i < eList.getLength(); i++) {
	        		Element row = (Element) eList.item(i);
	        		NodeList columnList = row.getChildNodes();
	        		for (int c = 0; c < columnList.getLength(); c++) {
	        			String nodeName = columnList.item(c).getNodeName();
	        			String nodeValue = columnList.item(c).getTextContent();
	        			if(nodeName.equalsIgnoreCase("type") && nodeValue.equalsIgnoreCase("SALTDataProfiling")){
	        				nodeIndex = i;
	        			}
	        			
	        			if(nodeName.equalsIgnoreCase("type") && nodeValue.equalsIgnoreCase("ECLDataset")){
	        				datasetIndex = i;
	        			}
	        		}
	        	}
	        }

	        
	        //insert new node here
	        Node oldChild = docElement.getElementsByTagName("entry").item(nodeIndex);
	        Node dsChild = docElement.getElementsByTagName("entry").item(datasetIndex);
	       
	        Document doc =  dbf.newDocumentBuilder()
	                .parse(new InputSource(new StringReader(projectXML)));
	        Node newChild = doc.getElementsByTagName("entry").item(0);
	        
	        Document doc2 =  dbf.newDocumentBuilder()
	                .parse(new InputSource(new StringReader(outputXML)));
	        Node outputChild = doc2.getElementsByTagName("entry").item(0);

	      //  newHopXML
	        Document docHop =  dbf.newDocumentBuilder()
            .parse(new InputSource(new StringReader(newHopXML)));
            Node hopChild = docHop.getElementsByTagName("hop").item(0);
    
	        NodeList dsChildList = dsChild.getChildNodes();
	       String recordName = "";
	       String datasetName = "";
	       String logicalFileName = "";
	        for (int i = 0; i <dsChildList.getLength(); i++) {
	        	if(dsChildList.item(i).getNodeName().equals("record_name")){
	        		recordName = dsChildList.item(i).getTextContent();
	        	}
	        	if(dsChildList.item(i).getNodeName().equals("dataset_name")){
	        		datasetName = dsChildList.item(i).getTextContent();
	        	}
	        	if(dsChildList.item(i).getNodeName().equals("logical_file_name")){
	        		logicalFileName = dsChildList.item(i).getTextContent();
	        	}
	        }
	        
	       
	        NodeList oldChildList = oldChild.getChildNodes();
	        String xLoc = "";
	        String yLoc = "";
	        String profileName = "";
	        String inRecName = "";
	        for (int i = 0; i < oldChildList.getLength(); i++) {
	        	if(oldChildList.item(i).getNodeName().equals("xloc")){
	        		xLoc = oldChildList.item(i).getTextContent();
	        	}
	        	if(oldChildList.item(i).getNodeName().equals("yloc")){
	        		yLoc = oldChildList.item(i).getTextContent();
	        	}
	        	if(oldChildList.item(i).getNodeName().equals("name")){
	        		profileName = oldChildList.item(i).getTextContent();
	        	}
	        	if(oldChildList.item(i).getNodeName().equals("datasetName")){
	        		inRecName = oldChildList.item(i).getTextContent();
	        	}
	        }
	        NodeList newChildList = newChild.getChildNodes();
	        for (int i = 0; i < newChildList.getLength(); i++) {
	        	if(newChildList.item(i).getNodeName().equals("xloc")){
	        		newChildList.item(i).setTextContent(xLoc);
	        	}
	        	if(newChildList.item(i).getNodeName().equals("yloc")){
	        		newChildList.item(i).setTextContent(yLoc);
	        	}
	        	if(newChildList.item(i).getNodeName().equals("inRecordName")){
	        		newChildList.item(i).setTextContent(inRecName);
	        	}
	        	if(newChildList.item(i).getNodeName().equals("outRecordName")){
	        		newChildList.item(i).setTextContent(recordName+"Optimized");
	        	}
	        	if(newChildList.item(i).getNodeName().equals("recordset_name")){
	        		newChildList.item(i).setTextContent(datasetName+"Optimized");
	        	}
	        }
	        
	        NodeList newOutputList = outputChild.getChildNodes();
	        for (int i = 0; i < newChildList.getLength(); i++) {
	        	if(newOutputList.item(i).getNodeName().equals("recordset")){
	        		newOutputList.item(i).setTextContent(datasetName+"Optimized");
	        	}
	        	if(newOutputList.item(i).getNodeName().equals("file")){
	        		newOutputList.item(i).setTextContent(logicalFileName+"Optimized");
	        	}
	        	
	        }
	        
	        Node newChildNode = dom.importNode(newChild, true);
	        Node newOutputNode = dom.importNode(outputChild, true);
	        Node newHopNode = dom.importNode(hopChild, true);
	        
	        docElement.getElementsByTagName("entries").item(0).replaceChild(newChildNode, oldChild);
	        docElement.getElementsByTagName("entries").item(0).appendChild(newOutputNode);
	        docElement.getElementsByTagName("hops").item(0).appendChild(newHopNode);
	        NodeList hList = docElement.getElementsByTagName("hop");
	       
	        if (hList != null && hList.getLength() > 0) {
	        	
	        	for (int i = 0; i < hList.getLength(); i++) {
	        		Element row = (Element) hList.item(i);
	        		NodeList columnList = row.getChildNodes();
	        		for (int c = 0; c < columnList.getLength(); c++) {
	        			String nodeName = columnList.item(c).getNodeName();
	        			String nodeValue = columnList.item(c).getTextContent();
	        			if(nodeName.equalsIgnoreCase("from") && nodeValue.equalsIgnoreCase(profileName)){
	        				columnList.item(c).setTextContent("Output");
	        			}
	        			if(nodeName.equalsIgnoreCase("to") && nodeValue.equalsIgnoreCase(profileName)){
	        				columnList.item(c).setTextContent("Project");
	        			}
	        		}
	        	}
	        }

	       //write the file out
	        try {
	            
	            Transformer transformer = TransformerFactory.newInstance().newTransformer();
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	            //initialize StreamResult with File object to save to file
	            StreamResult result = new StreamResult(new StringWriter());
	            DOMSource source = new DOMSource(dom);
	            transformer.transform(source, result);

	            String xmlString = result.getWriter().toString();
	            writeFile(optimizedJobFile,xmlString);
	            
	        }catch (Exception e){
	        	e.printStackTrace();
	        }
	        
	}
void writeFile(String outFile, String xml) throws IOException{
	Path path = Paths.get(outFile);
	List<String> lines =  new ArrayList<String>();
	lines.add(xml);
    Files.write(path, lines, ENCODING);
}

String buildProject(){
	String xml = "<entry>"+
"      <name>Project</name>"+
"      <description/>"+
"      <type>ECLProject</type>"+
"		<declareCounter><![CDATA[no]]></declareCounter>"+
"		<recordset_name eclIsDef=\"true\" eclType=\"dataset\"><![CDATA[]]></recordset_name>"+
"		<inRecordName><![CDATA[]]></inRecordName>"+
"		<outRecordName clIsDef=\"true\" eclType=\"record\"><![CDATA[]]></outRecordName>"+
"		<outRecordFormat><![CDATA[]]></outRecordFormat>"+
"		<transformName><![CDATA[projectLayoutFromSaltLayout]]></transformName>"+
"		<transformFormat><![CDATA[]]></transformFormat>"+
"		<recordList><![CDATA[" + recordList + "]]></recordList>"+
"		<mapperRecList><![CDATA[" + mapperRecList + "]]></mapperRecList>"+
"      <parallel>N</parallel>"+
"      <draw>Y</draw>"+
"      <nr>0</nr>"+
"      <xloc>273</xloc>"+
"      <yloc>178</yloc>"+
"      </entry>";
	
	return xml;
}

String buildHops(){
	String xml = "<hop>"+
"      <from>Project</from>"+
"      <to>Output</to>"+
"      <from_nr>0</from_nr>"+
"      <to_nr>0</to_nr>"+
"      <enabled>Y</enabled>"+
"      <evaluation>Y</evaluation>"+
"      <unconditional>N</unconditional>"+
"    </hop>";

	return xml;
}
String buildOutput(){
	String xml = "<entry>"+
 "     <name>Output</name>"+
 "     <description/>"+
 "     <type>ECLOutput</type>"+
"		<recordset><![CDATA[]]></recordset>"+
"        <isDef><![CDATA[No]]></isDef>"+
"        <outputType><![CDATA[File]]></outputType>"+
"        <includeFormat><![CDATA[]]></includeFormat>"+
"        <inputType><![CDATA[Recordset]]></inputType>"+
"        <outputFormat><![CDATA[]]></outputFormat>"+
"        <expression><![CDATA[]]></expression>"+
"        <file><![CDATA[]]></file>"+
"        <typeOptions><![CDATA[]]></typeOptions>"+
"        <fileOptions><![CDATA[]]></fileOptions>"+
"        <named><![CDATA[]]></named>"+
"        <extend><![CDATA[]]></extend>"+
"        <returnAll><![CDATA[]]></returnAll>"+
"        <thor><![CDATA[No]]></thor>"+
"        <cluster><![CDATA[]]></cluster>"+
"        <encrypt><![CDATA[]]></encrypt>"+
"        <compressed><![CDATA[]]></compressed>"+
"        <overwrite><![CDATA[]]></overwrite>"+
"        <expire><![CDATA[]]></expire>"+
"        <repeat><![CDATA[]]></repeat>"+
"        <pipeType><![CDATA[]]></pipeType>"+
"      <parallel>N</parallel>"+
"      <draw>Y</draw>"+
"      <nr>0</nr>"+
"      <xloc>510</xloc>"+
"      <yloc>189</yloc>"+
"      </entry>";
return xml;
}


public void processRecLayoutFile(String aFileName) throws IOException {
      Path path = Paths.get(aFileName);

      BufferedReader reader = Files.newBufferedReader(path, ENCODING);
   
      String line = null;
      int c = 0;
      while ((line = reader.readLine()) != null) {
        //process each line in some way
        //log(line);
    	  if(c>=2 && !line.contains("END;")){
    		  line = line.trim().replace(";", "");
    		  String[] parts = line.split(" ");
    		  
    		  String rawType = parts[0].trim();
    		  String name = parts[1].trim();
    		  String type = rawType.replaceAll("[0-9][_]*[0-9]*$", "");
    		  String size = rawType.replace(type, "");
    		  
    		  if(type.equalsIgnoreCase("SALT25.StrType")){
    			  type = "STRING";
    		  }

    		  if(mapperRecList.length()>0){
    			  mapperRecList += "|";
    		  }
    		  mapperRecList += "self."+name +",("+type+")input."+name;
    		  
    		  if(recordList.length()>0){
    			  recordList += "|";
    		  }
    		  recordList += name+","+type+","+size+",,ASCENDING";
    	  }
    	  c++;
      }


  }

}