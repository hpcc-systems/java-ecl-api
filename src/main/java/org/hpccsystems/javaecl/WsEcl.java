/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author chalaax
 */
public class WsEcl {
    
    private final String urlString;

    public WsEcl(String clusterAddress, String portNumber, String clusterName, String query) {
      urlString = "http://" + clusterAddress + ":" + portNumber + "/WsEcl/submit/query/" + clusterName + "/" + query;
    }

    public WsEcl(String clusterAddress, String clusterName, String query) {
      urlString = "http://" + clusterAddress + ":8002/WsEcl/submit/query/" + clusterName + "/" + query;
    }
    
    
    public ArrayList execute() {
       try {  
            // Construct data
            String data = URLEncoder.encode("submit_type_=xml", "UTF-8");
            data += "&" + URLEncoder.encode("S1=Submit", "UTF-8");

            // Send data
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            //return parse(conn.getInputStream());
            
            String s = convertInputStreamToString(conn.getInputStream());
            System.out.println(s);
            return null;
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
                           columnsArray.add(new Column(columnList.item(k).getNodeName(), columnList.item(k).getTextContent()));
                        }
                    }
                }

            }
        }
        
        return results;
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
}
