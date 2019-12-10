/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.objeto_gasto;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import nebuleuse.ORM.Coleccion;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.RegistroMap;


public class ObjetoGastoJSON  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public ObjetoGastoJSON ( ) throws IOException  {
    }
      
        
    

    public JsonArray  consulta02 (Integer resolucion_estado,
            String fecha_desde, String fecha_hasta,
            Integer obj_desde, Integer obj_hasta
            
            ) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
                        
            ObjetoGastoRS rs = new ObjetoGastoRS();            
            ResultSet resulset = rs.consulta02 ( resolucion_estado, fecha_desde, fecha_hasta,
                                                                obj_desde,  obj_hasta);                
            
            
            
            while(resulset.next()) 
            {  
                map = registoMap.convertirHashMap(resulset);     
                JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
                jsonarray.add( element );
            }                    
            this.total_registros = rs.total_registros  ;   
            
        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return jsonarray ;         
        }
    }      
          
    
    
          
        
}
