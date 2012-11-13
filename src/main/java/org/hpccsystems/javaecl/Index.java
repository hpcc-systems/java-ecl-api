/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Index implements EclCommand {

    private String baserecset = "";
    private String keys = "";
    private String payload = "";
    private String indexfile = "";
    private String sorted = "";
    private String preload = "";
    private String opt = "";
    private String compressed = "";
    private String distributed = "";
    private String index = "";
    private String newindexfile = "";
    private String overwrite = "No";
    
    private String name = "";

    public String getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(String overwrite) {
        this.overwrite = overwrite;
    }
    
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getBaserecset() {
        return baserecset;
    }

    public void setBaserecset(String baserecset) {
        this.baserecset = baserecset;
    }

    public String getCompressed() {
        return compressed;
    }

    public void setCompressed(String compressed) {
        this.compressed = compressed;
    }

    public String getDistributed() {
        return distributed;
    }

    public void setDistributed(String distributed) {
        this.distributed = distributed;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndexfile() {
        return indexfile;
    }

    public void setIndexfile(String indexfile) {
        this.indexfile = indexfile;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getNewindexfile() {
        return newindexfile;
    }

    public void setNewindexfile(String newindexfile) {
        this.newindexfile = newindexfile;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPreload() {
        return preload;
    }

    public void setPreload(String preload) {
        this.preload = preload;
    }

    public String getSorted() {
        return sorted;
    }

    public void setSorted(String sorted) {
        this.sorted = sorted;
    }

    

    @Override
    public String ecl() {
        //ITERATE(recordset, transform [, LOCAL ] )
        
         
        String ecl = "";
        
        //TODO: update this to build recordsets keys and payload
        
       
        
        
        ecl += this.name+" := INDEX(";
        
        if(this.newindexfile != null && !this.newindexfile.equals("")){
            //attr := INDEX(index,newindexfile);
            ecl += this.index + ",'" + this.newindexfile + "'";
        }else{
            if(baserecset != null && !baserecset.equals("")){
                ecl += baserecset + ",";
            }
            if(keys != null && !keys.equals("")){
                ecl += "{" + keys + "},";
            }else{
                //error
            }
            if(payload != null && !payload.equals("")){
                ecl += "{" + payload + "},";
            }else{
                //error
            }
            ecl += "'" + indexfile + "'";
            
            if(this.sorted.equals("Yes")){
                ecl += ",SORTED" + "";
            }else{
                ecl += "";
            }
            
            if(this.preload.equals("Yes")){
                ecl += ",PRELOAD" + "";
            }else{
                ecl += "";
            }
            
            if(this.opt.equals("Yes")){
                ecl += ",OPT" + "";
            }else{
                ecl += "";
            }
            
            if(this.compressed.equals("LZW")){
                ecl += ",COMPRESSED(LZW)" + "";
            }else if(this.compressed.equals("ROW")){
                ecl += ",COMPRESSED(ROW)" + "";
            }else if(this.compressed.equals("FIRST")){
                ecl += ",COMPRESSED(FIRST)" + "";
            }else{
                ecl += "";
            }
            
            if(this.distributed.equals("Yes")){
                ecl += ",DISTRIBUTED";
            }else{
                ecl += "";
            }
            
            
            
        }
//attr := INDEX([ baserecset, ] keys, indexfile [,SORTED] [,PRELOAD] [,OPT] [,COMPRESSED( LZW | ROW | FIRST) ] [,DISTRIBUTED]);
//attr := INDEX([ baserecset, ] keys, payload, indexfile [,SORTED] [,PRELOAD] [,OPT] [,COMPRESSED( LZW | ROW | FIRST) ] [,DISTRIBUTED]);
//attr := INDEX(index,newindexfile);
        //close out the ecl call
        ecl += ");\r\n\r\n";
        
        //ecl += this.name + "Bld := BUILDINDEX(" + this.name+"Index" + ");\r\n\r\n";
        ecl += "BUILDINDEX(" + this.name;
        if(this.overwrite.equals("Yes")){
            ecl += ",OVERWRITE";
        }
        ecl += ");\r\n\r\n";
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
