/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.consulta;

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


public class ConsultaJSON  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public ConsultaJSON ( ) throws IOException  {
    }
      
        
    


    public JsonArray  consulta001 (
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta
            ) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
            
            
            ConsultaRS rs = new ConsultaRS();            
            ResultSet resulset = rs.consulta001(fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta
                    );                
                       
            
            
            
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
          
    
              

    public JsonArray  consulta002 (
            Integer estadores,
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer obj_desde, Integer obj_hasta
            ) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
            ConsultaRS rs = new ConsultaRS();            
            ResultSet resulset = rs.consulta002 (
                    estadores,
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta,
                    obj_desde, obj_hasta
                    );                
                       
            
                    
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
          
    
    
              

    public JsonArray  consulta003 (            
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta
            ) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
            ConsultaRS rs = new ConsultaRS();            
            ResultSet resulset = rs.consulta003 (
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta
                    );                
                       
            
                    
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
          
    
    
              

    public JsonArray  consulta004 (
            Integer estadores,
            String fecha_desde, String fecha_hasta, 
            Integer dpto_desde, Integer dpto_hasta,
            Integer consejo_desde, Integer consejo_hasta,
            Integer obj_desde, Integer obj_hasta
            ) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {   
            
            ConsultaRS rs = new ConsultaRS();            
            ResultSet resulset = rs.consulta004 (
                    estadores,
                    fecha_desde, fecha_hasta,
                    dpto_desde, dpto_hasta,
                    consejo_desde, consejo_hasta,
                    obj_desde, obj_hasta
                    );                
                       
            
                    
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
