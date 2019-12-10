/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.resolucion;



import static com.google.common.io.Files.map;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.RegistroMap;


public class ResolucionDAO  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public ResolucionDAO ( ) throws IOException  {
    }
         
      
   
    
    public Resolucion estado (String resolucion) 
            throws IOException, Exception {
                
        Resolucion  ret = new Resolucion();                        
        String sql = new ResolucionSQL().estado(resolucion);        
        ret = (Resolucion) persistencia.sqlToObject(sql, ret);
        
        return ret ;          
        
    }      



       
    
    public Resolucion estado_transferencia (Integer consejo, String resolucion) 
            throws IOException, Exception {
                
        Resolucion  ret = new Resolucion();                        
        String sql = new ResolucionSQL().estado_transferencia(consejo, resolucion);        
        ret = (Resolucion) persistencia.sqlToObject(sql, ret);
        
        return ret ;          
        
    }      

    
    
    
    
    
    
    public Resolucion fxActualizarEstado (Integer  rendicion) 
            throws IOException, Exception {
                
        
        Resolucion  resolucion = new Resolucion();        
        ResolucionRS rs = new ResolucionRS();   
        
        ResultSet resultset = rs.fxActualizarEstado(rendicion);
        
        resultset.next();
        //System.out.println( resultset.getString("prueba2") );
        resultset.close();        
        
        return resolucion ;          
        
    }      

    
    
    
    
    
    
    public Resolucion fx_resolucion_numero (Integer  rendicion) 
            throws IOException, Exception {
                
        
        Resolucion  resolucion = new Resolucion();        
        ResolucionRS rs = new ResolucionRS();   
        
        ResultSet resultset = rs.fx_resolucion_numero(rendicion);
        
        //resultset.next();
        resultset.close();        
        
        return resolucion ;          
        
    }      

    
    
    
}     
