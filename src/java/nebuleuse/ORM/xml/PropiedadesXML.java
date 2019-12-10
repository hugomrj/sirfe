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
 * @author hugo
 */


public class PropiedadesXML {
        
    private String path;        
    private File file;
    
        Properties properties = new Properties();
        FileInputStream fileInput;
        Enumeration enuKeys ;
        
        
    public void Iniciar() throws FileNotFoundException, IOException  {

            this.fileInput = new FileInputStream(getFile());
            this.properties = new Properties();            
            this.properties.loadFromXML(fileInput);
            this.fileInput.close();
        
    }

    
    public String getData( String strKey) 
            throws IOException  {
           
        String retornar = "";
        this.Iniciar();        
        this.enuKeys = this.properties.keys();
        
        while (this.enuKeys.hasMoreElements()) 
        {
            String xmlkey = (String) enuKeys.nextElement();
            String xmlvalue = properties.getProperty(xmlkey);            
            
            if (xmlkey.equals(strKey))
            {
                retornar = xmlvalue;                            
            }

        }   
        
        return retornar;
        
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }
        
    
}






