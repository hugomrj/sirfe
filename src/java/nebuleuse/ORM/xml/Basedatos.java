/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package nebuleuse.ORM.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author hugom_000
 */


public  class Basedatos {
        
        private File file = null;
        private String path =  this.getClass().getResource("/").getPath().replaceAll("classes", "configuracion");        
        private PropiedadesXML propiedadesXml = new PropiedadesXML();

        
        
    public void setPropiedades ( ) 
            throws IOException  {
    
        propiedadesXml.setPath(this.path);
        propiedadesXml.setFile(this.file);
        propiedadesXml.Iniciar();        
        propiedadesXml.enuKeys = propiedadesXml.properties.keys();
        
    }
        
       
    
    public String getValue ( String strKey ) throws IOException  {                
        return propiedadesXml.getData(strKey);
    }

    
    public void setFile(String filexml) {
        this.file = new File(this.path+filexml);
    }    
    
    
}
