
package py.com.aplicacion.departamento;


import java.io.IOException;
import nebuleuse.ORM.sql.ReaderT;
import nebuleuse.ORM.sql.SentenciaSQL;

public class DepartamentoSQL {
    
    
    public String search ( String busqueda )
            throws Exception {
    
        String sql = "";                                 
        sql = SentenciaSQL.select(new Departamento(), busqueda);        
        
        return sql ;             
    }        
    
    
    
    
    
    
    public String consulta03  ( Integer resolucion_estado,
            String fecha_desde, String fecha_hasta,
            Integer obj_desde, Integer obj_hasta ) 
            
            throws IOException {
        
        String sql = "";                                 
        ReaderT readerSQL = new ReaderT("Departamento");
        readerSQL.fileExt = "consulta03.sql";

        sql = readerSQL.get(  resolucion_estado, fecha_desde, fecha_hasta,
                obj_desde, obj_hasta
                );              
        
        return sql ;                     
        
        
    }
    
          
    
    
}




