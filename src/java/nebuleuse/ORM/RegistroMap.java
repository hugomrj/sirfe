/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nebuleuse.ORM;
import nebuleuse.ORM.xml.Serializacion;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


public class RegistroMap {
   
    private HashMap<String,String> linea = new HashMap<String,String>();

    public  HashMap convertirHashMap (HttpServletRequest request, Object objeto) {        
        
        Serializacion serializacion = new Serializacion(objeto);                
        
        linea.clear();               
        Enumeration<String> parametros = request.getParameterNames();   
        String NombreColumna = "";
        
        while(parametros.hasMoreElements())
        {
             String strParametro = parametros.nextElement();       
             
            NombreColumna = "";
            for (int i = 1; i < serializacion.getElementos().size() ; i++) 
            {  
                if  (serializacion.getElementos().get(i).getObjeto().equals(strParametro))
                {
                    NombreColumna = serializacion.getElementos().get(i).getTabla();
                    break;
                }
            }              
            
            if (NombreColumna != ""){
                linea.put(NombreColumna, request.getParameter(strParametro));                 
            }
        }

        
        return linea;
    }
    
    
    public HashMap convertirHashMap (ResultSet resultset) throws SQLException{        
        
        linea.clear();   
        
        ResultSetMetaData metaData = resultset.getMetaData();
        int columnCount = metaData.getColumnCount();        

        
        for (int i = 1; i < columnCount + 1; i++ ) 
        {  
            String strColumna = metaData.getColumnName(i);
            linea.put(strColumna, resultset.getString(strColumna));

        }
        
        return linea;
    }    



    public HashMap convertirHashMap (Map<String, Object> map) throws SQLException{        
        
        linea.clear();   
        
        for (Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();            
            linea.put(key, value.toString());

        }        
        
        
        return linea;
    }    
    
    
    
    
    
    
    
    
    public HashMap getRegistro (ResultSet resultset) throws SQLException{        
        
            if(resultset.next()) 
            {    
                linea.clear();     
                HashMap registro = new HashMap();
                RegistroMap registoMap = new RegistroMap();
                return registoMap.convertirHashMap(resultset);            
            }
            else{
                return null;
            }
    }    
    
    
}




