/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpccsystems.javaecl;

/**
 *
 * @author ChalaAX
 */
public class FromField implements EclCommand {
    private String name;
    private String inDS;
    private String outDS;
    private String fromType;
    private String map;

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }
    
    

    public String getInDS() {
        return inDS;
    }

    public void setInDS(String inDS) {
        this.inDS = inDS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutDS() {
        return outDS;
    }

    public void setOutDS(String outDS) {
        this.outDS = outDS;
    }

   
    
    @Override
    public String ecl() {
        //will need a solution here... sometimes you pass .results() as aprt of inDS
        //return "ml.FromField(" + inDS + "," + fromType + "," + outDS + "," + inDS +"_map);\r\n";
        String rstr = "ml.FromField(" + inDS + "," + fromType + "," + outDS;
        if(map != null && !map.equals("")){
            rstr += "," + map;
        }
        rstr += ");\r\n";
        return rstr;
      //return "ml.FromField(" + inDS + "," + fromType + "," + outDS + ");\r\n";
    }

    @Override
    public CheckResult check() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
