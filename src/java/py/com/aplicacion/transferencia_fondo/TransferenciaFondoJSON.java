/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.transferencia_fondo;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.RegistroMap;


public class TransferenciaFondoJSON  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public TransferenciaFondoJSON ( ) throws IOException  {
    }
      
        

    public JsonArray  list_resolucion (Integer page) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {     
            
            
            TransferenciaFondoRS rs = new TransferenciaFondoRS();            
            ResultSet resulset = rs.list_resolucion(page);                
            
            
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
          
    
    

    public JsonArray  list_resolucion_search (Integer page,  String busqueda) {
        
        Map<String, String> map = null;        
        JsonArray jsonarray = new JsonArray();      
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
        
        
        try 
        {     
            TransferenciaFondoRS rs = new TransferenciaFondoRS();            
            ResultSet resulset = rs.list_resolucion_search(page, busqueda);
                   
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
