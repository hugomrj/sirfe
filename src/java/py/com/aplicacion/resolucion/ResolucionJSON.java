/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.aplicacion.resolucion;


import java.io.IOException;
import nebuleuse.ORM.Persistencia;
import nebuleuse.ORM.sql.ReaderT;




public class ResolucionJSON  {

    public Integer total_registros = 0;    
    private Persistencia persistencia = new Persistencia();      
    
    
    public ResolucionJSON ( ) throws IOException  {
    
    }
            
    
    
        
    
    public String  resolucion_estados () throws IOException {
            
        
            String json = "";                                 
            ReaderT readerSQL = new ReaderT("ResolucionEstado");
            
            
            readerSQL.fileExt = "all.json";
            json = readerSQL.get( );              
            
            /*
            while(resulset.next()) 
            {  
                map = registoMap.convertirHashMap(resulset);     
                JsonElement element = gson.fromJson(gson.toJson(map)  , JsonElement.class);        
                jsonarray.add( element );
            }                    
            */
            
            
            return json ;         

    }      
      
    

    
          
        
}
