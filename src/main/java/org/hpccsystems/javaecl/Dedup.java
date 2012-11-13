/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class Dedup implements EclCommand {

    private String name;
    private String recordset;//Comma separated list of fieldNames. a "-" prefix to the field name will indicate descending order
    private String condition;
    private Boolean isAll = false;
    private Boolean isHash = false;
    private String keep;
    private String keeper;
    private Boolean runLocal = false;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    
   

    public void setIsAll(boolean isAll) {
        this.isAll = isAll;
    }
    
    public boolean getIsAll() {
        return isAll;
    }

    public String getIsAllString() {
        if(this.isAll){
            return "true";
        }else{
            return "false";
        }
    }
     
    public void setIsAllString(String isAll) {
        if(isAll.equals("true")){
            this.isAll = true;
        }else{
            this.isAll = false;
        }
    }

    public boolean getIsHash() {
        return isHash;
    }

    public void setIsHash(boolean isHash) {
        this.isHash = isHash;
    }
    
    public String getIsHashString() {
        if(isHash){
            return "true";
        }else{
            return "false";
        }
    }

    public void setIsHashString(String isHash) {
        
        if(isHash.equals("true")){
            this.isHash = true;
        }else{
            this.isHash = false;
        }
    }
    

    public String getKeep() {
        return keep;
    }

    public void setKeep(String keep) {
        this.keep = keep;
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

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

    public boolean getRunLocal() {
        return runLocal;
    }

    public void setRunLocal(boolean runLocal) {
        this.runLocal = runLocal;
    }
    
    public String getRunLocalString() {
        if(runLocal){
            return "true";
        }else{
            return "false";
        }
       
    }

    public void setRunLocalString(String runLocal) {
        if(runLocal.equals("true")){
            this.runLocal = true;
        }else{
            this.runLocal = false;
        }
    }


    

    

    @Override
    public String ecl() {
        //DEDUP(recordset [, condition [, ALL[, HASH]] [, KEEP n ] [, keeper ] ] [, LOCAL])
        String ecl = name + ":=DEDUP(" + recordset;
        //add local if its set if not its optional
        if (condition != "" && !condition.equals("")) {
           ecl += "," + condition;
           if(isAll != null && isAll){
               ecl += ", ALL";
               if(isHash != null && isHash){
                   ecl += ", HASH";
                   
               }
           }
        }
        
        if(keep != null && !keep.equals("")){
           ecl += ", KEEP " + keep;
           if(!keeper.equals("")){
               ecl += "," + keeper;
           }
        }
        
        if(runLocal != null && runLocal){
           ecl += ", LOCAL";
        }
        //close out the ecl call
        ecl += ");\r\n";
        
        return ecl;
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
