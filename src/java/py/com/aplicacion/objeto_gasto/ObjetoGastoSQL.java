
package py.com.aplicacion.objeto_gasto;


import java.io.IOException;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;

public class ObjetoGastoSQL {
    
    public String search ( String busqueda )
            throws Exception {
    
        String sql = "";             
        
        ReaderT readerSQL = new ReaderT("ObjetoGasto");
        readerSQL.fileExt = "search.sql";        
        sql = readerSQL.get(busqueda);
                
        
        
        return sql ;             
    }        
    
    
    
    
public String list ( ) throws Exception {
    
        String sql = "";             
            
        ReaderT readerSQL = new ReaderT("ObjetoGasto");
        readerSQL.fileExt = "list.sql";        
        sql = readerSQL.get();
        
        return sql ;
             
    }              
    


    
    
    public String consulta02  ( Integer resolucion_estado,
            String fecha_desde, String fecha_hasta,
            Integer obj_desde, Integer obj_hasta ) 
            
            throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("ObjetoGasto");
        readerSQL.fileExt = "consulta02.sql";

        sql = readerSQL.get(  resolucion_estado, fecha_desde, fecha_hasta,
                obj_desde, obj_hasta
                );              
        
        return sql ;                     
        
        
    }
    
         


    
}




