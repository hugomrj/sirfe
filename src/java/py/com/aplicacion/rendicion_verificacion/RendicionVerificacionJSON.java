/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.rendicion_verificacion;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.RegistroMap;

public class RendicionVerificacionJSON  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public RendicionVerificacionJSON ( ) throws IOException  {

    }
      
        
    


    public JsonArray  verificador ( Integer usuario ) {
        
        
        Map<String, String> map = null;      
        JsonArray jsonarray = new JsonArray();              
        
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
             
                
        try 
        {     
            
            RendicionVerificacionRS rs = new RendicionVerificacionRS();            
            ResultSet resulset = rs.verificador(usuario);
            
            resulset.next();
        
            if (resulset.getRow() != 0) 
            {  
                map = registoMap.convertirHashMap(resulset);     
                JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
                jsonarray.add( element );
                
            }

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
        
          
    public Integer  getVerificadoRendicion( Integer verificacion ) {
        
        
        Map<String, String> map = null;      
        Integer ret = 0;              
        
        RegistroMap registoMap = new RegistroMap();     
        Gson gson = new Gson();               
             
                
        try 
        {     
            
            RendicionVerificacionRS rs = new RendicionVerificacionRS();            
            ResultSet resulset = rs.getVerificadoRendicion(verificacion);
            
            resulset.next();
        
            if (resulset.getRow() != 0) 
            {  
                map = registoMap.convertirHashMap(resulset);     

                JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);     
                JsonObject gsonObj = element.getAsJsonObject();
                 ret = gsonObj.get("verificador").getAsInt();                
            }

        }         
        catch (Exception ex) {                        
            System.out.println(ex.getMessage());
            throw new Exception(ex);
        }
        finally
        {
            return ret ;         
        }
    }      

    
    
}
