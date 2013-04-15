package org.hpccsystems.javaecl;

import java.util.ArrayList;

public class HPCCServerInfo {

	    private String clusterName;
	    private String serverHost;
	    private int serverPort;

	public HPCCServerInfo(String serverHost,int serverPort){
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}
	public String[] fetchTargetClusters(){
		//http://192.168.80.132:8010/WsTopology/TpTargetClusterQuery?ver_=1.18&wsdl
		ClusterInfoSoap c = new ClusterInfoSoap(serverHost,serverPort);
		String[] clusters = c.fetchClusters("");
		
		return clusters;
		
	}
	
	
	public String[] fetchFileList(){
		FileInfoSoap c = new FileInfoSoap(serverHost,serverPort);
		String[] files = c.fetchFiles();
		
		return files;
	}
	
	public ArrayList<String[]> fetchFileDetails(String fileName){
		FileInfoSoap c = new FileInfoSoap(serverHost,serverPort);
		ArrayList<String[]> file = c.fetchFileMeta(fileName);
		if(file != null && file.size()==1){
			if(file.get(0)[0].equals("line")){
				//nor rec
				file = new ArrayList<String[]>();
			}
		}
		return file;
	}

}
