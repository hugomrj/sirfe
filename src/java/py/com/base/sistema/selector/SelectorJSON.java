/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.base.sistema.selector;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.sql.ResultSet;
import java.util.Map;
import nebuleuse.ORM.RegistroMap;


public class SelectorJSON {
    
    
    public JsonArray lista ( Integer codigo  )    
            throws Exception {
                
        Map<String, String> map = null;
        
        RegistroMap registoMap = new RegistroMap();        
        ResultSet res = new SelectorRS().ListaRecursiva(codigo);                
        
        Gson gson = new Gson();       
        JsonArray jsonarray = new JsonArray();   
            
        while(res.next()) 
        {  
            map = registoMap.convertirHashMap(res);     
            JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
            jsonarray.add( element );
    
        }        

       return jsonarray ;          
            
    }
   
    
    public JsonArray all (  )    
            throws Exception {
                
        Map<String, String> map = null;
        
        RegistroMap registoMap = new RegistroMap();        
        ResultSet res = new SelectorRS().ListaAll();                
        
        Gson gson = new Gson();       
        JsonArray jsonarray = new JsonArray();   
            
        while(res.next()) 
        {  
            map = registoMap.convertirHashMap(res);     
            JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
            jsonarray.add( element );
    
        }        

       return jsonarray ;          
            
    }
   
    
    
    
    
}
















