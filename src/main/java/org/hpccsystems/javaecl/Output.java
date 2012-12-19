/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Output implements EclCommand {
    private String recordset;
    private String name;
    
    private String isDef = ""; //true set output into attr using job entry name
    private String outputType = ""; //recordset,expression
    private String includeFormat = ""; //yes/no
    private String inputType = ""; //thor file options, CSV, XML, pipe, Named, File Owned by workunit
    private String outputFormat = "";
    private String expression = "";
    private String file = "";
    private String typeOptions = ""; // used for CSV, XML, Pipe
    private String fileOptions = ""; // used for CSV, XML
    private String named = ""; //used for named
    private String extend = ""; // availiable for Named
    private String returnAll = ""; // availiable for Named
    private String thor = ""; // used in 
    private String cluster = "";
    private String encrypt = "";
    private String compressed = "";
    private String overwrite = "";
    private String expire = "";
    private String repeat = "";// piped
    private String pipeType = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    
    public String getRecordset() {
        return recordset;
    }
    public void setRecordset(String recordset) {
        this.recordset = recordset;
    }
    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getCompressed() {
        return compressed;
    }

    public void setCompressed(String compressed) {
        this.compressed = compressed;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileOptions() {
        return fileOptions;
    }

    public void setFileOptions(String fileOptions) {
        this.fileOptions = fileOptions;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String format) {
        this.outputFormat = format;
    }

    public String getIncludeFormat() {
        return includeFormat;
    }

    public void setIncludeFormat(String includeFormat) {
        this.includeFormat = includeFormat;
    }

    public String getIsDef() {
        //System.out.println("getIsDef() = " + isDef);
        return isDef;
    }

    public void setIsDef(String isDef) {
        this.isDef = isDef;
    }

    public String getTypeOptions() {
        return typeOptions;
    }

    public void setTypeOptions(String options) {
        this.typeOptions = options;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(String overwrite) {
        this.overwrite = overwrite;
    }

    public String getPipeType() {
        return pipeType;
    }

    public void setPipeType(String pipeType) {
        this.pipeType = pipeType;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getReturnAll() {
        return returnAll;
    }

    public void setReturnAll(String returnAll) {
        this.returnAll = returnAll;
    }

    public String getThor() {
        return thor;
    }

    public void setThor(String thor) {
        this.thor = thor;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getNamed() {
        return named;
    }

    public void setNamed(String named) {
        this.named = named;
    }



    @Override
    public String ecl() {
        String ecl = "";
        
        if(this.isDef != null && this.isDef.equals("Yes")){
            ecl += this.name + " := ";
        }
        
        if(this.inputType != null && this.inputType.equals("Expression")){
            //[attr := ] OUTPUT( expression [, NAMED( name ) ] )
            ecl += "OUTPUT( " + this.expression;
            
            if(this.named != null && !this.named.equals("")){
                ecl += ", NAMED(" + named + ")"; 
            }
            ecl += ");";
        }else if (this.inputType.equals("Recordset")){
            //add recrodset
            ecl += "OUTPUT( " + this.recordset;
            if(this.thor.equals("No")){
                if(!this.outputFormat.equals("")){
                    ecl += "," + this.outputFormat;
                }
                if(this.outputType.equals("File")){
                    if(this.outputFormat.equals("")){
                        ecl += ",";
                    }
                    //[attr := ] OUTPUT(recordset [, [ format ] [,file [thorfileoptions ] ] )
                    
                    //[attr := ] OUTPUT(recordset [, [ format ] [,file [, CLUSTER( target ) ] [,ENCRYPT( key ) ]
                       //[,COMPRESSED] [,OVERWRITE] [,EXPIRE( [ days ] ) ] ] ] )
                    
                    
                    if(!this.file.equals("")){
                             ecl += ",'" + file +"'";

                            if(!this.cluster.equals("")){
                                ecl += ",CLUSTER('"+this.cluster+"')";
                            }
                            if(!this.encrypt.equals("")){
                                ecl += ",ENCRYPT('" + this.encrypt + "')";
                            }
                            if(this.compressed.equals("Yes")){
                                ecl += ",COMPRESSED";
                            }else{
                               // ecl += ",";
                            }
                            if(this.overwrite.equals("Yes")){
                                ecl += ",OVERWRITE";
                            }else{
                                ecl += ",";
                            }
                            if(!this.expire.equals("")){
                                ecl += ",EXPIRE(" + this.expire + ")";
                            }else{
                                ecl += "";
                            }
                   }
                    
                    
                }else if(this.outputType.equals("File - CSV")){
                    //[attr := ] OUTPUT(recordset, [ format ] ,file , CSV [ (csvoptions) ] [csvfileoptions ] )
                    
                    //[attr := ] OUTPUT(recordset, [ format ] ,file , CSV [ (csvoptions) ] [, CLUSTER( target )] [,ENCRYPT(key) ]
                        //[, OVERWRITE ] [, EXPIRE( [ days ] ) ] )
                    if(!this.file.equals("")){
                        ecl += ",'" +this.file +"',CSV";
                        if(!this.typeOptions.equals("")){
                            ecl += "(" + this.typeOptions + ")";
                        }
                        if(!this.cluster.equals("")){
                            ecl+= ",CLUSTER(" + this.cluster + ")";
                        }
                        if(!this.encrypt.equals("")){
                            ecl += ",ENCRYPT(" + this.encrypt + ")";
                        }
                        if(this.overwrite.equals("Yes")){
                            ecl += ",OVERWRITE";
                        }else{
                            ecl += "";
                        }
                        if(!this.expire.equals("")){
                            ecl += ",EXPIRE(" + this.expire + ")";
                        }else{
                            ecl += "";
                        }
                            
                    }
                        //[, OVERWRITE ] [, EXPIRE( [ days ] ) ] )
                }else if(this.outputType.equals("File - XML")){
                    //[attr := ] OUTPUT(recordset, [ format ] , file , XML [ (xmloptions) ] [xmlfileoptions ] )
                    
                    //[attr := ] OUTPUT(recordset, [ format ] ,file ,XML [ (xmloptions) ] [,ENCRYPT( key ) ] [, CLUSTER( target ) ]
                        //[, OVERWRITE ] [, EXPIRE( [ days ] ) ] )
                    
                    
                   if(!this.file.equals("")){
                        ecl += ",'" +this.file +"',XML";
                        if(!this.typeOptions.equals("")){
                            ecl += "(" + this.typeOptions + ")";
                        }
                        if(!this.cluster.equals("")){
                            ecl+= ",CLUSTER(" + this.cluster + ")";
                        }
                        if(!this.encrypt.equals("")){
                            ecl += ",ENCRYPT(" + this.encrypt + ")";
                        }
                        if(this.overwrite.equals("Yes")){
                            ecl += ",OVERWRITE";
                        }else{
                            ecl += ",";
                        }
                        if(!this.expire.equals("")){
                            ecl += ",EXPIRE(" + this.expire + ")";
                        }else{
                            ecl += "";
                        }
                   }       
                  
                    
                }else if(this.outputType.equals("Piped")){
                    //[attr := ] OUTPUT(recordset, [ format ] ,PIPE( pipeoptions )
                        
                    //[attr := ] OUTPUT(recordset, [ format ] ,PIPE( command [, CSV | XML]) [, REPEAT] )
                    
                    ecl += ",PIPE(" + this.typeOptions;
                    if(this.pipeType.equals("CSV")){
                        ecl += ",CSV";
                    }else if(this.pipeType.equals("XML")){
                        ecl += ",XML";
                    }else{
                        ecl += ",";
                    }
                    ecl += ")";
                    if(this.repeat.equals("Yes")){
                        ecl += ",REPEAT";
                    }else{
                        ecl += "";
                    }
                    
                }else if(this.outputType.equals("Named")){
                    //[attr := ] OUTPUT(recordset [, format ] , NAMED( name ) [,EXTEND] [,ALL])
                    
                    //[attr := ] OUTPUT(recordset [, format ] ,NAMED( name ) [,EXTEND] [,ALL])
                    
                   
                    ecl += ",NAMED(" + this.named + ")";
                    
                    if(this.extend.equals("Yes")){
                        ecl += ",EXTEND";
                    }else{
                        ecl += ",";
                    }
                    
                    if(this.returnAll.equals("Yes")){
                        ecl += ",ALL";
                    }else{
                        ecl += "";
                    }
                    
                }
                
            }else if(this.thor.equals("Yes")){
                //[attr := ] OUTPUT( recordset , THOR )
                ecl += ", THOR";
            }
            
            ecl += ");";
        }
        
        /*
        
//[attr := ] OUTPUT( expression [, NAMED( name ) ] )

        */
        
        return ecl += "\r\n";
        //return "output(" + recordset + ");";
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
